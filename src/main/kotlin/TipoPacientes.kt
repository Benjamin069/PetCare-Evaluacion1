package org.example

import java.time.LocalDateTime

class Canino (
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDuenio: TipoDuenio
) : Paciente(codigoAtencion, nombre, especie, fechaIngreso, tipoDuenio) {

    override fun calcularCostoBase(minutos: Int): Double {
        val tarifaHora = 12000.0
        val horas = minutos / 60.0
        val descuentoConvenio = if (tipoDuenio == TipoDuenio.CONVENIO) 0.80 else 1.0
        return horas * tarifaHora * descuentoConvenio
    }
}

class Felino(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDuenio: TipoDuenio
) : Paciente(codigoAtencion, nombre, especie, fechaIngreso) {

    override fun calcularCostoBase(minutos: Int): Double {
        if (minutos < 20) return 0.0
        val tarifaHora = 9000.0
        return (minutos / 60.0) * tarifaHora
    }
}

class Exotico(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDuenio: TipoDuenio
    val esSilvetre: Boolean
) : Paciente(codigoAtencion, nombre, especie, fechaIngreso, tipoDuenio) {

    override fun calcularCostoBase(minutos: Int): Double {
        val tarifaHora = 20000.0
        val recargaSilvestre = if (esSilvetre) 1.30 else 1.0
        return (minutos / 60.0) * tarifaHora * recargaSilvestre
    }
}