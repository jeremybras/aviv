package fr.aviv.details.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.details.domain.ListingDetailInteractor
import fr.aviv.details.domain.ListingDetailResult
import fr.aviv.home.domain.Listing
import fr.aviv.home.presentation.ListingBuilder
import fr.aviv.home.presentation.ListingDisplayModel
import fr.aviv.testutils.BaseCoroutinesTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class ListingDetailViewModelTest : BaseCoroutinesTest() {

    @Mock
    private lateinit var interactor: ListingDetailInteractor

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var listingBuilder: ListingBuilder

    private lateinit var viewModel: ListingDetailViewModel

    @BeforeEach
    fun setup() {
        viewModel = ListingDetailViewModel(
            dispatcher = testDispatcher,
            interactor = interactor,
            resources = resources,
            listingBuilder = listingBuilder,
        )
    }

    @Test
    fun `loadListings - given interactor returns error - then should return error ui state`() {
        // Given
        given(interactor.loadListing(1)).willReturn(ListingDetailResult.Error)
        given(resources.getString(R.string.repository_failure)).willReturn("errorMessage")

        // When / Then
        viewModel.loadListing(1)
        assertEquals(ListingDetailUiState.Loading, viewModel.detailUiState.value)
        scheduler.advanceUntilIdle()
        assertEquals(
            ListingDetailUiState.Error(message = "errorMessage"),
            viewModel.detailUiState.value
        )
    }

    @Test
    fun `loadListings - given interactor returns success - then should call builder and return result`() {
        // Given
        val item = mock(Listing::class.java)
        val displayModel = mock(ListingDisplayModel::class.java)
        given(interactor.loadListing(1)).willReturn(
            ListingDetailResult.Success(
                item = item,
            )
        )
        given(listingBuilder.build(item)).willReturn(displayModel)

        // When / Then
        viewModel.loadListing(1)
        assertEquals(ListingDetailUiState.Loading, viewModel.detailUiState.value)
        scheduler.advanceUntilIdle()
        assertEquals(
            ListingDetailUiState.Ready(
                item = displayModel,
            ),
            viewModel.detailUiState.value
        )
        then(listingBuilder).should().build(item)
        then(listingBuilder).shouldHaveNoMoreInteractions()
    }
}
