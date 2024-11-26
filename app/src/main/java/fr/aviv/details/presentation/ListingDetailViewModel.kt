package fr.aviv.details.presentation

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.aviv.R
import fr.aviv.details.domain.ListingDetailInteractor
import fr.aviv.details.domain.ListingDetailResult
import fr.aviv.home.presentation.ListingBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
    private val interactor: ListingDetailInteractor,
    private val resources: Resources,
    private val listingBuilder: ListingBuilder,
) : ViewModel() {

    private val _detail = MutableStateFlow<ListingDetailUiState>(ListingDetailUiState.Loading)
    val detailUiState: StateFlow<ListingDetailUiState> = _detail

    fun loadListing(listingId: Int) {
        viewModelScope.launch(context = dispatcher) {
            _detail.value = ListingDetailUiState.Loading
            _detail.value = when (val result = interactor.loadListing(listingId)) {
                is ListingDetailResult.Success -> ListingDetailUiState.Ready(
                    item = listingBuilder.build(result.item)
                )

                ListingDetailResult.Error -> ListingDetailUiState.Error(
                    message = resources.getString(R.string.repository_failure),
                )
            }
        }
    }
}
