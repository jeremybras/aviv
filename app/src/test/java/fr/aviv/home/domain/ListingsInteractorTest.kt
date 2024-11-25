package fr.aviv.home.domain

import fr.aviv.home.data.ListingsRepository
import fr.aviv.home.data.ListingsResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingsInteractorTest {

    @Mock
    private lateinit var repository: ListingsRepository

    @InjectMocks
    private lateinit var interactor: ListingsInteractor

    private val defaultGivenResponseItem = ListingsResponse.Success.Listing(
        bedrooms = 3,
        city = "Sartrouville",
        id = 3,
        area = 350,
        url = "imageUrl",
        price = 250000,
        professional = "Century",
        propertyType = ListingsResponse.Success.PropertyType.HOUSE,
        offerType = 3,
        rooms = 4,
    )

    private val defaultExpectedListingItem = ListingsResult.Success.Listing(
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

    @Test
    fun `loadListings - given a Success result - then should return a Success listing object`() {
        // Given
        val givenItem = defaultGivenResponseItem
        val expectedItem = defaultExpectedListingItem
        given(repository.loadListings()).willReturn(
            ListingsResponse.Success(
                items = listOf(
                    givenItem,
                )
            )
        )

        // When
        val result = interactor.loadListings()

        // Then
        assertEquals(
            ListingsResult.Success(
                items = listOf(expectedItem)
            ),
            result,
        )
    }

    @Test
    fun `loadListings - given a Success result with an appartment - then should return a Success listing object`() {
        // Given
        val givenItem = defaultGivenResponseItem.copy(
            propertyType = ListingsResponse.Success.PropertyType.APPARTMENT,
        )
        val expectedItem = defaultExpectedListingItem.copy(
            propertyType = ListingsResult.Success.PropertyType.APPARTMENT,
        )
        given(repository.loadListings()).willReturn(
            ListingsResponse.Success(
                items = listOf(
                    givenItem,
                )
            )
        )

        // When
        val result = interactor.loadListings()

        // Then
        assertEquals(
            ListingsResult.Success(
                items = listOf(expectedItem)
            ),
            result,
        )
    }

    @Test
    fun `loadListings - given a Failure response - then should return an Error result`() {
        // Given
        given(repository.loadListings()).willReturn(ListingsResponse.Failure)

        // When
        val result = interactor.loadListings()

        // Then
        assertEquals(ListingsResult.Error, result)
    }
}
