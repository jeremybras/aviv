package fr.aviv.home.presentation

import androidx.annotation.DrawableRes

sealed interface ListingsUiState {
    data object Loading : ListingsUiState

    data class Ready(
        val items: List<ListingDisplayModel>,
    ) : ListingsUiState {
        data class ListingDisplayModel(
            val id: Int,
            val tags: List<Tag>,
            val city: String,
            val shouldShowImage: Boolean,
            val imageUrl: String,
            val professional: String,
        )

        data class Tag(
            @DrawableRes val image: Int,
            val value: String? = null,
        )
    }

    data class Error(
        val message: String,
    ) : ListingsUiState
}
