package fr.aviv.details.domain

import fr.aviv.details.data.ListingDetailRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingDetailInteractorTest {

    @Mock
    private lateinit var repository: ListingDetailRepository

    @InjectMocks
    private lateinit var interactor: ListingDetailInteractor

    @Test
    fun `loadListings - should return repository result`() {
        // Given
        val response = mock(ListingDetailResult::class.java)
        given(repository.loadListing(1)).willReturn(response)

        // When
        val result = interactor.loadListing(1)

        // Then
        assertEquals(response, result)
    }
}
