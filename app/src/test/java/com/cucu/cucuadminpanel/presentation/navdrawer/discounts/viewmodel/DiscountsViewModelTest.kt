package com.cucu.cucuadminpanel.presentation.navdrawer.discounts.viewmodel

import com.cucu.cucuadminpanel.MainDispatcherRule
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DiscountsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var repository: Repository

    lateinit var viewModel: DiscountsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DiscountsViewModel(repository)
    }

    @Test
    fun `getAllDiscounts should call repo`() = runTest {
        val expected = listOf(Product("a", "b", 10.0), Product("c", "d", 11.0))

        coEvery { repository.getAllDiscounts() } returns expected

        assert(viewModel.discounts == emptyList<Product>())

        viewModel.getAllDiscounts()

        coVerify { repository.getAllDiscounts() }
        assert(viewModel.discounts == expected)
    }

}