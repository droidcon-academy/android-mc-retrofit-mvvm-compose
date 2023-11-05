package com.droidcon.movieapp.common

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.roundOffDecimal(): Double {
    val decimalFormat = DecimalFormat("#.#")

    decimalFormat.roundingMode = RoundingMode.CEILING

    return decimalFormat.format(this).toDouble()
}
