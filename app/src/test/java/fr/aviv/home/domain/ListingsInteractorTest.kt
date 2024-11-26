package fr.aviv.home.domain

import fr.aviv.home.data.ListingsRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ListingsInteractorTest {

    @Mock
    private lateinit var repository: ListingsRepository

    @InjectMocks
    private lateinit var interactor: ListingsInteractor

    @Test
    fun `loadListings - should return an repository result`() {
        // Given
        val response = mock(ListingsResult::class.java)
        given(repository.loadListings()).willReturn(response)

        // When
        val result = interactor.loadListings()

        // Then
        assertEquals(response, result)
    }
}
