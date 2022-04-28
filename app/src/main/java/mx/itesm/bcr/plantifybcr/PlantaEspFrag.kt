package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mx.itesm.bcr.plantifybcr.databinding.PlantaEspFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.PlantaEspVM

class PlantaEspFrag : Fragment() {


    private lateinit var binding: PlantaEspFragmentBinding
    companion object {
        fun newInstance() = PlantaEspFrag()
    }

    private lateinit var viewModel: PlantaEspVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.planta_esp_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantaEspVM::class.java)
        // TODO: Use the ViewModel
    }

}