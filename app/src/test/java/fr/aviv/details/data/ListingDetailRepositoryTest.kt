package fr.aviv.details.data

import fr.aviv.details.domain.ListingDetailResult
import fr.aviv.home.data.AvivService
import fr.aviv.home.data.ListingsJsonResponse
import fr.aviv.home.data.ListingsJsonTransformer
import fr.aviv.home.domain.Listing
import fr.aviv.testutils.enqueue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExtendWith(MockitoExtension::class)
class ListingDetailRepositoryTest {

    private lateinit var server: MockWebServer

    private lateinit var service: AvivService

    @Mock
    private lateinit var transformer: ListingsJsonTransformer

    private lateinit var repository: ListingDetailRepository

    @BeforeEach
    fun setup() {
        server = MockWebServer()
        service = Retrofit
            .Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AvivService::class.java)
        repository = ListingDetailRepository(
            service = service,
            transformer = transformer,
        )
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `loadListing - given a successful request - then should return transformers result`() {
        // Given
        server.enqueue("listing_detail.json")
        val jsonItem = ListingsJsonResponse.ListingResponse(
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
        val expectedResult = mock(Listing::class.java)
        given(transformer.transformJsonToEntity(jsonItem)).willReturn(expectedResult)

        // When
        val result = repository.loadListing(1)

        // Then
        assertEquals(
            ListingDetailResult.Success(item = expectedResult),
            result,
        )
    }

    @Test
    fun `loadListing - given a successful request with an empty body - then should return Failure`() {
        // Given
        server.enqueue("listings_empty.json")

        // When
        val result = repository.loadListing(1)

        // Then
        assertEquals(ListingDetailResult.Error, result)
    }

    @Test
    fun `loadListing - given an unsuccessful request - then should return Failure`() {
        // Given
        server.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("Client Error")
        )

        // When
        val result = repository.loadListing(1)

        // Then
        assertEquals(ListingDetailResult.Error, result)
    }
}
