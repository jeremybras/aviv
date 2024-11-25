package fr.aviv.home.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.home.domain.ListingsInteractor
import fr.aviv.home.domain.ListingsResult
import fr.aviv.testutils.BaseCoroutinesTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.times
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingsViewModelTest : BaseCoroutinesTest() {

    @Mock
    private lateinit var interactor: ListingsInteractor

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var listingBuilder: ListingBuilder

    private lateinit var viewModel: ListingsViewModel

    @BeforeEach
    fun setup() {
        viewModel = ListingsViewModel(
            dispatcher = testDispatcher,
            interactor = interactor,
            resources = resources,
            listingBuilder = listingBuilder,
        )
    }

    @Test
    fun `loadListings - given interactor returns error - then should return error ui state`() {
        // Given
        given(interactor.loadListings()).willReturn(ListingsResult.Error)
        given(resources.getString(R.string.repository_failure)).willReturn("errorMessage")

        // When / Then
        viewModel.loadListings()
        assertEquals(ListingsUiState.Loading, viewModel.listingsUiState.value)
        scheduler.advanceUntilIdle()
        assertEquals(
            ListingsUiState.Error(message = "errorMessage"),
            viewModel.listingsUiState.value
        )
    }

    @Test
    fun `loadListings - given interactor returns success - then should call builder and return result`() {
        // Given
        val item = mock(ListingsResult.Success.Listing::class.java)
        val displayModel = mock(ListingsUiState.Ready.ListingDisplayModel::class.java)
        given(interactor.loadListings()).willReturn(
            ListingsResult.Success(
                items = listOf(
                    item,
                    item,
                    item,
                ),
            )
        )
        given(listingBuilder.build(item)).willReturn(displayModel)

        // When / Then
        viewModel.loadListings()
        assertEquals(ListingsUiState.Loading, viewModel.listingsUiState.value)
        scheduler.advanceUntilIdle()
        assertEquals(
            ListingsUiState.Ready(
                items = listOf(
                    displayModel,
                    displayModel,
                    displayModel,
                )
            ),
            viewModel.listingsUiState.value
        )
        then(listingBuilder).should(times(3)).build(item)
        then(listingBuilder).shouldHaveNoMoreInteractions()
    }
}
