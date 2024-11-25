package fr.aviv.utils

import java.text.DecimalFormat
import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {
    fun format(
        price: Int,
    ): String = DecimalFormat("#,###").format(price).replace(",", " ")
}
