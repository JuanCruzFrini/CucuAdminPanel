package com.cucu.cucuadminpanel.presentation.products.add

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cucu.cucuadminpanel.MainDispatcherRule
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import com.cucu.cucuadminpanel.presentation.products.add.viewmodel.AddProductViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddProductViewModelTest {

    @get:Rule
    var instantExcecutor = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var repository: Repository

    private lateinit var viewModel: AddProductViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddProductViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `add product success`() = runTest {
        val product = mockk<Product>()
        val uri = mockk<Uri>()
        val expected = true

        coEvery { repository.addProduct(product, uri) } returns expected

        viewModel.addProduct(product, uri)

        advanceUntilIdle()
        coVerify { repository.addProduct(product, uri) }

        assert(!viewModel.isAdding)
        assert(viewModel.succeedAdd == expected)
    }

    @Test
    fun `add product fails`() = runBlocking {
        val product = mockk<Product>()
        val uri = mockk<Uri>()
        val error = Exception("error")

        coEvery { repository.addProduct(product, uri) } throws error

        viewModel.addProduct(product, uri)

        coVerify { repository.addProduct(product, uri) }
        assert(viewModel.succeedAdd == null)
    }

    @Test
    fun `getCountries should call repo`() = runBlocking {
        val expected = mockk<List<String>>()

        coEvery { repository.getCategories() } returns expected

        val result = repository.getCategories()

        coVerify { repository.getCategories() }
        assert(result == expected)
    }
}