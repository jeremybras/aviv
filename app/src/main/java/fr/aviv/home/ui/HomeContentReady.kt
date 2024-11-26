package fr.aviv.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import fr.aviv.home.presentation.ListingsUiState
import fr.aviv.ui.DarkOrLightPreview

@Composable
fun HomeContentReady(
    listings: List<ListingsUiState.Ready.ListingDisplayModel>,
    onListing: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        listings.forEach { listing ->
            Listing(
                listing = listing,
                onListing = onListing,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
private fun Listing(
    listing: ListingsUiState.Ready.ListingDisplayModel,
    onListing: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        onClick = {
            onListing(listing.id)
        },
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                    ),
                text = listing.professional,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 8.dp),
            ) {
                if (listing.shouldShowImage) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)),
                            model = listing.imageUrl,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                FlowRow(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp,
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    listing.tags.forEach {
                        Tag(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun Tag(tag: ListingsUiState.Ready.Tag) {
    Row(
        modifier = Modifier
            .height(24.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Red)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(tag.image),
            contentDescription = null,
        )
        tag.value?.let { text ->
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
            )
        }
    }
}

@DarkOrLightPreview
@Composable
private fun HomeContentReadyPreview() {
    HomeContentReady(
        listings = listOf(
            ListingsUiState.Ready.ListingDisplayModel(
                id = 1,
                tags = emptyList(),
                city = "Paris",
                shouldShowImage = false,
                imageUrl = "",
                professional = "Villers-sur-Mer",
            ),
        ),
        onListing = {},
    )
}
