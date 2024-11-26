package fr.aviv.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.aviv.details.presentation.ListingDetailUiState
import fr.aviv.details.presentation.ListingDetailViewModel

@Composable
fun DetailScreen(
    listingId: Int,
) {

    val viewModel: ListingDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadListing(listingId)
    }

    when (val detailUiState = viewModel.detailUiState.collectAsStateWithLifecycle().value) {
        ListingDetailUiState.Loading -> {
            DetailContentLoading()
        }
        is ListingDetailUiState.Error -> {
            DetailContentError(
                errorMessage = detailUiState.message,
                onRetry = {
                    viewModel.loadListing(listingId)
                },
            )
        }
        is ListingDetailUiState.Ready -> {
            DetailContentReady(
                listing = detailUiState.item,
            )
        }
    }
}
