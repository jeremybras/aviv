package fr.aviv.home.data

import fr.aviv.home.domain.Listing
import fr.aviv.home.domain.PropertyType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingsJsonTransformerTest {

    private val transformer = ListingsJsonTransformer()

    @Test
    fun `transformJsonToEntity - given a response with a house item - then should build result`() {
        // Given
        val givenJsonItem = ListingsJsonResponse.ListingResponse(
            bedrooms = 4,
            city = "Villers-sur-Mer",
            id = 1,
            area = 250,
            url = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
            price = 1500000,
            professional = "GSL EXPLORE",
            propertyType = "Maison - Villa",
            offerType = 1,
            rooms = 8,
        )
        val expectedItem = Listing(
            bedrooms = 4,
            city = "Villers-sur-Mer",
            id = 1,
            area = 250,
            url = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
            price = 1500000,
            professional = "GSL EXPLORE",
            propertyType = PropertyType.HOUSE,
            offerType = 1,
            rooms = 8,
        )

        // When
        val result = transformer.transformJsonToEntity(givenJsonItem)

        // Then
        assertEquals(expectedItem, result)
    }

    @Test
    fun `transformJsonToEntity - given a response with an appartment item - then should build result`() {
        // Given
        val givenJsonItem = ListingsJsonResponse.ListingResponse(
            bedrooms = 4,
            city = "Villers-sur-Mer",
            id = 1,
            area = 250,
            url = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
            price = 1500000,
            professional = "GSL EXPLORE",
            propertyType = "Appartement",
            offerType = 1,
            rooms = 8,
        )
        val expectedItem = Listing(
            bedrooms = 4,
            city = "Villers-sur-Mer",
            id = 1,
            area = 250,
            url = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
            price = 1500000,
            professional = "GSL EXPLORE",
            propertyType = PropertyType.APPARTMENT,
            offerType = 1,
            rooms = 8,
        )

        // When
        val result = transformer.transformJsonToEntity(givenJsonItem)

        // Then
        assertEquals(expectedItem, result)
    }
}
