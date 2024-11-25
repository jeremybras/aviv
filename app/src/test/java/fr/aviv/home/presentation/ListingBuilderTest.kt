package fr.aviv.home.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.home.domain.ListingsResult
import fr.aviv.utils.CurrencyFormatter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingBuilderTest {
    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var currencyFormatter: CurrencyFormatter

    @InjectMocks
    private lateinit var builder: ListingBuilder

    @BeforeEach
    fun setup() {
        given(resources.getString(R.string.area, "350")).willReturn("350m")
        given(currencyFormatter.format(250000)).willReturn("250 000")
    }

    private val defaultGivenItem = ListingsResult.Success.Listing(
        bedrooms = 3,
        city = "Sartrouville",
        id = 3,
        area = 350,
        url = "imageUrl",
        price = 250000,
        professional = "Century",
        propertyType = ListingsResult.Success.PropertyType.HOUSE,
        offerType = 3,
        rooms = 4,
    )
    private val defaultExpectedDisplayModel = ListingsUiState.Ready.ListingDisplayModel(
        id = 3,
        city = "Sartrouville",
        tags = listOf(
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_euro,
                value = "250 000",
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_bedroom,
                value = "3",
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_blueprint,
                value = "4",
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_field,
                value = "350m",
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_home,
            ),
        ),
        shouldShowImage = true,
        imageUrl = "imageUrl",
        professional = "Century",
    )

    @Test
    fun `build - given a nominal case - should return display model`() {
        // Given
        val givenItem = defaultGivenItem
        val expectedDisplayModel = defaultExpectedDisplayModel

        // When
        val result = builder.build(givenItem)

        // Then
        assertEquals(expectedDisplayModel, result)
    }

    @Test
    fun `build - given an item with an appartment - should return display model with appartment drawable`() {
        // Given
        val givenItem = defaultGivenItem.copy(
            propertyType = ListingsResult.Success.PropertyType.APPARTMENT,
        )
        val expectedDisplayModel = defaultExpectedDisplayModel.copy(
            tags = defaultExpectedDisplayModel.tags
                .subList(0, 4)
                .plus(
                    ListingsUiState.Ready.Tag(
                        image = R.drawable.ic_appartment,
                    )
                )
        )

        // When
        val result = builder.build(givenItem)

        // Then
        assertEquals(expectedDisplayModel, result)
    }

    @Test
    fun `build - given an item with null image url - should return display model`() {
        // Given
        val givenItem = defaultGivenItem.copy(
            url = null,
        )
        val expectedDisplayModel = defaultExpectedDisplayModel.copy(
            shouldShowImage = false,
            imageUrl = "",
        )

        // When
        val result = builder.build(givenItem)

        // Then
        assertEquals(expectedDisplayModel, result)
    }

    @Test
    fun `build - given an item with blank image url - should return display model`() {
        // Given
        val givenItem = defaultGivenItem.copy(
            url = "",
        )
        val expectedDisplayModel = defaultExpectedDisplayModel.copy(
            shouldShowImage = false,
            imageUrl = "",
        )

        // When
        val result = builder.build(givenItem)

        // Then
        assertEquals(expectedDisplayModel, result)
    }
}
