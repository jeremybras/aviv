package fr.aviv.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor() {
    fun format(
        date: LocalDateTime,
    ): String = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}
