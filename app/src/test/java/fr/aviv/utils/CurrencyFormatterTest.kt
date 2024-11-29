package fr.aviv.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CurrencyFormatterTest {

    private val formatter = CurrencyFormatter()

    @Test
    fun `format - given a short price - then return formatted String`() {
        // Given
        val givenPrice = 34

        // When
        val result = formatter.format(givenPrice)

        // Then
        assertEquals("34", result)
    }

    @Test
    fun `format - given a long price - then return formatted String`() {
        // Given
        val givenPrice = 3_400

        // When
        val result = formatter.format(givenPrice)

        // Then
        assertEquals("3 400", result)
    }

    @Test
    fun `format - given a really long price - then return formatted String`() {
        // Given
        val givenPrice = 3_400_000

        // When
        val result = formatter.format(givenPrice)

        // Then
        assertEquals("3 400 000", result)
    }
}
