package fr.aviv.home.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.home.domain.Listing
import fr.aviv.home.domain.PropertyType
import fr.aviv.testutils.lenientGiven
import fr.aviv.testutils.willReturn
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
        given(resources.getString(R.string.tag_area_title)).willReturn("Area")
        given(resources.getString(R.string.tag_price_title)).willReturn("Price")
        given(resources.getString(R.string.tag_rooms_title)).willReturn("Rooms")
        given(resources.getString(R.string.tag_bedrooms_title)).willReturn("Bedrooms")
        lenientGiven(resources.getString(R.string.tag_type_title_house)).willReturn("House")
        lenientGiven(resources.getString(R.string.tag_type_title_appartment)).willReturn("Appartment")
    }

    private val defaultGivenItem = Listing(
        bedrooms = 3,
        city = "Sartrouville",
        id = 3,
        area = 350,
        url = "imageUrl",
        price = 250000,
        professional = "Century",
        propertyType = PropertyType.HOUSE,
        offerType = 3,
        rooms = 4,
    )
    private val defaultExpectedDisplayModel = ListingDisplayModel(
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
            propertyType = PropertyType.APPARTMENT,
        )
        val expectedDisplayModel = defaultExpectedDisplayModel.copy(
            tags = defaultExpectedDisplayModel.tags
                .subList(0, 4)
                .plus(
                    TagDisplayModel(
                        image = R.drawable.ic_appartment,
                        title = "Appartment",
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
