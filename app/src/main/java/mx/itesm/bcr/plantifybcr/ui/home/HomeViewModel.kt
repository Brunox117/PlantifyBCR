package mx.itesm.bcr.plantifybcr.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.itesm.bcr.plantifybcr.Planta

class HomeViewModel : ViewModel() {

    val tokken = MutableLiveData<String>()
    fun setTokken(valor:String){
        tokken.value = valor
    }

}