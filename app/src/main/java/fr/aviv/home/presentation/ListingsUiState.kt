package fr.aviv.home.presentation

import androidx.annotation.DrawableRes

sealed interface ListingsUiState {
    data object Loading : ListingsUiState

    data class Ready(
        val items: List<ListingDisplayModel>,
    ) : ListingsUiState

    data class Error(
        val message: String,
    ) : ListingsUiState
}

data class ListingDisplayModel(
    val id: Int,
    val tags: List<TagDisplayModel>,
    val city: String,
    val shouldShowImage: Boolean,
    val imageUrl: String,
    val professional: String,
)

data class TagDisplayModel(
    @DrawableRes val image: Int,
    val title: String,
    val value: String? = null,
)
