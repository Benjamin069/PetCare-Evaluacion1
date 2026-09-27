package org.example

import java.time.LocalDateTime

fun esCodigoValido(codigo: String): Boolean {
    if (codigo.length != 6) return false

    val primerasDosLetras = codigo[0].isLetter() && codigo[1].isLetter()
    val dosNumeros        = codigo[2].isDigit() && codigo[3].isDigit()
    val ultimasDosLetras  = codigo[4].isLetter() && codigo[5].isLetter()

    return primerasDosLetras && dosNumeros && ultimasDosLetras
}

open class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: LocalDateTime,
    val tipoDuenio: TipoDuenio
) {
    init {
        require(esCodigoValido(codigoAtencion)) { "El código '$codigoAtencion' es inválido." }
    }

    open fun calcularCostoBase(minutos: Int): Double = 0.0

    fun calcularMontoTotal(minutos: Int): Double {
        val base = calcularCostoBase(minutos)
        var total = base * 1.19

        if (tipoDuenio == TipoDuenio.MUNICIPAL) {
            total *= 0.50
        }

        if (total <= 0 && !(this is Felino && minutos < 20)) {
            throw IllegalArgumentException("La tarifa no puede ser \$0 o negativa.")
        }
        return total
    }
}