package mx.itesm.bcr.plantifybcr.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GrupoEspPlantasVM : ViewModel() {
    val tokken = MutableLiveData<String>()
    fun setTokken(valor:String){
        tokken.value = valor
    }
}