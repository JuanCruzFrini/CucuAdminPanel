package com.cucu.cucuadminpanel.presentation.products.detail.viewmodel

import com.cucu.cucuadminpanel.MainDispatcherRule
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainDispatcherRule()

    @RelaxedMockK
    lateinit var repository: Repository

    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ProductDetailViewModel(repository)
    }

    @Test
    fun `set product`() = runTest {
        val product = mockk<Product> {
            Product("id", "name",1200.0,0.0, 5, "img.com", "descr",0L, 1, 1, 0)
        }

        assert(viewModel.product.value == Product())

        viewModel.setProduct(product)

        assert(viewModel.product.value == product)
    }

    @Test
    fun `get old price`() = runTest {
        val product = Product(newPrice = 20.0)

        viewModel.setProduct(product)

        val result = viewModel.getOldPrice()

        assert(result == viewModel.product.value.newPrice)
    }

    @Test
    fun `updateProduct success`() = runTest {
        val product = mockk<Product> {
            Product("id", "name",1200.0,0.0, 5, "img.com", "descr",0L, 1, 1, 0)
        }
        val expected = true

        coEvery { repository.updateProduct(product) } returns expected

        viewModel.updateProduct(product)

        coVerify { repository.updateProduct(product) }
        assert(viewModel.succeedEdit == expected)
    }

    @Test
    fun `updateProduct error`() = runTest {
        val product = mockk<Product> {
            Product("id", "name",1200.0,0.0, 5, "img.com", "descr",0L, 1, 1, 0)
        }
        val error = Exception("error")

        coEvery { repository.updateProduct(product) } throws error

        viewModel.updateProduct(product)

        coVerify { repository.updateProduct(product) }
        assert(!viewModel.succeedEdit)
    }

    @Test
    fun `getCategories should call repo`() = runTest {
        val expected = listOf<String>()

        coEvery { repository.getCategories() } returns expected

        val result = viewModel.getCategories()

        coVerify { repository.getCategories() }
        assert(result == expected)
    }

    @Test
    fun `deleteProduct success`() = runTest {
        val productId = "Test id"
        val expected = true

        coEvery { repository.deleteProduct(productId) } returns expected

        viewModel.deleteProduct(productId)

        coVerify { repository.deleteProduct(productId) }
        assert(viewModel.succeedDelete == expected)
    }

    @Test
    fun `deleteProduct error`() = runTest {
        val productId = "Test id"
        val error = Exception("error")

        coEvery { repository.deleteProduct(productId) } throws error

        viewModel.deleteProduct(productId)

        coVerify { repository.deleteProduct(productId) }
        assert(!viewModel.succeedDelete)
    }
}