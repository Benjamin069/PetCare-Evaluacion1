package org.example

sealed class EstadoBox {

    object Libre : EstadoBox()
    data class EnAntencion(val paciente: Paciente) : EstadoBox()
    data class EnProceso(val motivo: String) : EstadoBox()
    data class FueraDeServicio(val motivo: String) : EstadoBox()

}