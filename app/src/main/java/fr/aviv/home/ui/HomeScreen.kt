package fr.aviv.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.aviv.home.presentation.ListingsUiState
import fr.aviv.home.presentation.ListingsViewModel

@Composable
fun HomeScreen(onListing: (Int) -> Unit) {

    val viewModel: ListingsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadListings()
    }
    when (val listingsUiState = viewModel.listingsUiState.collectAsStateWithLifecycle().value) {
        ListingsUiState.Loading -> {
            HomeContentLoading()
        }

        is ListingsUiState.Error -> {
            HomeContentError(
                errorMessage = listingsUiState.message,
                onRetry = viewModel::loadListings,
            )
        }

        is ListingsUiState.Ready -> {
            HomeContentReady(
                listings = listingsUiState.items,
                onListing = onListing,
            )
        }
    }
}
