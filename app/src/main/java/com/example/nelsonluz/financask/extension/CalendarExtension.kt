package com.example.nelsonluz.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}