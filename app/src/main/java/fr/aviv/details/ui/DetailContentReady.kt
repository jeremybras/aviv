package fr.aviv.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import fr.aviv.R
import fr.aviv.home.presentation.ListingDisplayModel
import fr.aviv.home.presentation.TagDisplayModel
import fr.aviv.ui.DarkOrLightPreview

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailContentReady(
    listing: ListingDisplayModel,
) {
    if (listing.shouldShowImage) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                model = listing.imageUrl,
                contentDescription = null,
            )
        }
    }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        textAlign = TextAlign.Center,
        text = listing.professional,
    )
    Spacer(modifier = Modifier.width(16.dp))
    listing.tags.forEachIndexed { index, tagDisplayModel ->
        Tag(tagDisplayModel)
        if (index < listing.tags.size - 1) {
            HorizontalDivider(
                color = DividerDefaults.color.copy(alpha = .6f)
            )
        }
    }
}

@Composable
private fun Tag(tag: TagDisplayModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(tag.image),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            text = tag.title,
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
private fun DetailContentReadyPreview() {
    DetailContentReady(
        ListingDisplayModel(
            id = 3,
            city = "Sartrouville",
            tags = listOf(
                TagDisplayModel(
                    image = R.drawable.ic_euro,
                    title = "Price",
                    value = "250 000",
                ),
                TagDisplayModel(
                    image = R.drawable.ic_bedroom,
                    title = "Bedrooms",
                    value = "3",
                ),
                TagDisplayModel(
                    image = R.drawable.ic_blueprint,
                    title = "Rooms",
                    value = "4",
                ),
                TagDisplayModel(
                    image = R.drawable.ic_field,
                    title = "Area",
                    value = "350m",
                ),
                TagDisplayModel(
                    image = R.drawable.ic_home,
                    title = "House",
                ),
            ),
            shouldShowImage = true,
            imageUrl = "imageUrl",
            professional = "Century",
        )
    )
}
