package mx.itesm.bcr.plantifybcr

interface ListenerRE // Listener para Remover y Editar.
{
    //CALIDAD
    fun itemClickedEditar(position: Int)
    fun itemClickedBorrar(position: Int)
}