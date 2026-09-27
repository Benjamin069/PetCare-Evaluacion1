package org.example

data class Ticket (
    val numeroTicket: Int,
    val paciente: Paciente,
    val minutosUso: Int,
    val montoPagado: Double
)