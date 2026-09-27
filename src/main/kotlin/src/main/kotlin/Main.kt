package org.example

import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

fun main() = runBlocking {
    val sistema = SistemaPetCare()

    println("=== INICIO DE PRUEBAS ===\n")

    try {
        println("Probando con un código malo '123ABC':")
        Canino("123ABC", "Fido", "Pastor", LocalDateTime.now(), TipoDuenio.PARTICULAR)
    } catch (e: Exception) {
        println("Error detectado: ${e.message}\n")
    }

    val max = Canino("CA12CD", "Max", "Golden Retriever", LocalDateTime.now(), TipoDuenio.CONVENIO)
    val luna = Canino("CA99ZA", "Luna", "Labrador", LocalDateTime.now(), TipoDuenio.PARTICULAR)
    val misi = Felino("FE22TO", "Misi", "Siamés", LocalDateTime.now(), TipoDuenio.PARTICULAR)
    val loro = Exotico("EX44RG", "Loro", "Amazónico", LocalDateTime.now(), TipoDuenio.MUNICIPAL, esSilvestre = true)

    sistema.registrarEntrada(max)
    sistema.registrarEntrada(luna)
    sistema.registrarEntrada(misi)
    sistema.registrarEntrada(loro)

    sistema.registrarSalida("CA12CD", 75)
    sistema.registrarSalida("CA99ZA", 180)
    sistema.registrarSalida("FE22TO", 18)
    sistema.registrarSalida("EX44RG", 120)

    sistema.mostrarReporteCierre()
}