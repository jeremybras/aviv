package fr.aviv.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.aviv.R
import fr.aviv.details.presentation.ListingDetailUiState
import fr.aviv.details.presentation.ListingDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    listingId: Int,
    onBack: () -> Unit,
) {

    val viewModel: ListingDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadListing(listingId)
    }
    var topAppBarTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.content_description_back_button),
                            )
                        }
                    )
                },
                title = {
                    Text(text = topAppBarTitle)
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
            ) {
                when (val uiState = viewModel.detailUiState.collectAsStateWithLifecycle().value) {
                    ListingDetailUiState.Loading -> {
                        DetailContentLoading()
                    }
                    is ListingDetailUiState.Error -> {
                        DetailContentError(
                            errorMessage = uiState.message,
                            onRetry = {
                                viewModel.loadListing(listingId)
                            },
                        )
                    }
                    is ListingDetailUiState.Ready -> {
                        topAppBarTitle = uiState.item.city
                        DetailContentReady(
                            listing = uiState.item,
                        )
                    }
                }
            }
        },
    )
}
