package org.example

import kotlinx.coroutines.delay

class SistemaPetCare {
    val boxes = List(10) { Box(it + 1) }
    val historial = mutableListOf<Ticket>()
    private var numTicket = 1

    suspend fun registrarEntrada(paciente: Paciente) {
        val boxLibre = boxes.firstOrNull { it.estado is EstadoBox.Libre }

        if (boxLibre == null) {
            println("No hay boxes disponibles para ${paciente.nombre}.")
            return
        }

        boxLibre.estado = EstadoBox.EnProceso("Ingresando...")
        println("Registrando entrada de ${paciente.nombre}...")
        delay(3000)

        boxLibre.estado = EstadoBox.EnAtencion(paciente)
        println("-> ${paciente.nombre} entró al Box ${boxLibre.numero}.\n")
    }

    suspend fun registrarSalida(codigo: String, minutos: Int) {
        val boxOcupado = boxes.firstOrNull { box ->
            val estado = box.estado
            estado is EstadoBox.EnAtencion && estado.paciente.codigoAtencion == codigo
        }

        if (boxOcupado == null) {
            println("No se encontró al paciente con código $codigo.")
            return
        }

        val paciente = (boxOcupado.estado as EstadoBox.EnAtencion).paciente
        boxOcupado.estado = EstadoBox.EnProceso("Cobrando...")
        println("Procesando salida de ${paciente.nombre}...")
        delay(6500)

        try {
            val total = paciente.calcularMontoTotal(minutos)
            val ticket = Ticket(numTicket++, paciente, minutos, total)
            historial.add(ticket)
            boxOcupado.estado = EstadoBox.Libre

            println("-> Salida lista: Ticket #${ticket.numeroTicket} | Total a pagar: \$${total.toInt()} (Box ${boxOcupado.numero} libre).\n")
        } catch (e: Exception) {
            println("Error al cobrar: ${e.message}")
            boxOcupado.estado = EstadoBox.EnAtencion(paciente)
        }
    }

    fun boxesDisponibles(): Int = boxes.count { it.estado is EstadoBox.Libre }

    fun pacientesConvenio() = historial.map { it.paciente }.filter { it.tipoDuenio == TipoDuenio.CONVENIO }

    fun ingresoPromedio(): Double = if (historial.isEmpty()) 0.0 else historial.sumOf { it.montoPagado } / historial.size

    fun codigosFinalizados() = historial.map { it.paciente.codigoAtencion }

    fun pacienteMayorTiempo() = historial.maxByOrNull { it.minutosUso }?.paciente

    fun mostrarReporteCierre() {
        println("\n--- RESUMEN DEL DÍA ---")
        historial.forEach { t ->
            println("Ticket #${t.numeroTicket} - ${t.paciente.nombre} (${t.paciente.codigoAtencion}) - Minutos: ${t.minutosUso} - Paga: \$${t.montoPagado.toInt()}")
        }
        println("----------------------")
        println("Total cobrado: \$${historial.sumOf { it.montoPagado }.toInt()}")
        println("Atenciones realizadas: ${historial.size}")
        println("Promedio por atención: \$${ingresoPromedio().toInt()}")
        println("Boxes desocupados: ${boxesDisponibles()}")
        println("----------------------\n")
    }
}