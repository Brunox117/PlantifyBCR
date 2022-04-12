package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.bcr.plantifybcr.viewmodels.GruposPlantasVM

class GruposPlantasFrag : Fragment() {

    companion object {
        fun newInstance() = GruposPlantasFrag()
    }

    private lateinit var viewModel: GruposPlantasVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grupos_plantas_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GruposPlantasVM::class.java)
        // TODO: Use the ViewModel
    }

}