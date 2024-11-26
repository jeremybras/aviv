package fr.aviv.details.presentation

import fr.aviv.home.presentation.ListingDisplayModel

sealed interface ListingDetailUiState {
    data object Loading : ListingDetailUiState
    data class Error(
        val message: String,
    ) : ListingDetailUiState

    data class Ready(
        val item: ListingDisplayModel,
    ) : ListingDetailUiState
}