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

    private val args: PlantaEspFragArgs by navArgs()
    private lateinit var binding: PlantaEspFragmentBinding
    private lateinit var viewModel: PlantaEspVM

    companion object {
        fun newInstance() = PlantaEspFrag()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlantaEspFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNombrePlanta.text = args.nombrePlanta
        //Aqui llamariamos a la base de datos

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantaEspVM::class.java)
        // TODO: Use the ViewModel
    }

}