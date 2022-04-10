package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class GrupoEspPlantasFrag : Fragment() {

    companion object {
        fun newInstance() = GrupoEspPlantasFrag()
    }

    private lateinit var viewModel: GrupoEspPlantasVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grupo_esp_plantas_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GrupoEspPlantasVM::class.java)
        // TODO: Use the ViewModel
    }

}