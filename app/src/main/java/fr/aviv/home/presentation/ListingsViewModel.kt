package fr.aviv.home.presentation

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.aviv.R
import fr.aviv.home.domain.ListingsInteractor
import fr.aviv.home.domain.ListingsResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ListingsViewModel @Inject constructor(
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
    private val interactor: ListingsInteractor,
    private val resources: Resources,
    private val listingBuilder: ListingBuilder,
) : ViewModel() {

    private val _listings = MutableStateFlow<ListingsUiState>(ListingsUiState.Loading)
    val listingsUiState: StateFlow<ListingsUiState> = _listings

    fun loadListings() {
        viewModelScope.launch(context = dispatcher) {
            _listings.value = ListingsUiState.Loading
            _listings.value = when (val result = interactor.loadListings()) {
                is ListingsResult.Success -> ListingsUiState.Ready(
                    items = result.items.map { item ->
                        listingBuilder.build(item)
                    },
                )

                ListingsResult.Error -> ListingsUiState.Error(
                    message = resources.getString(R.string.repository_failure),
                )
            }
        }
    }
}
