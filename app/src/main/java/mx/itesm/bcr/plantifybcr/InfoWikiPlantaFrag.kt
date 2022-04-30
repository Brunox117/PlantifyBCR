package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mx.itesm.bcr.plantifybcr.databinding.InfoWikiPlantaFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.InfoWikiPlantaVM

class InfoWikiPlantaFrag : Fragment() {

    private val args: InfoWikiPlantaFragArgs by navArgs()
    private lateinit var binding: InfoWikiPlantaFragmentBinding
    private lateinit var viewModel: InfoWikiPlantaVM

    companion object {
        fun newInstance() = InfoWikiPlantaFrag()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoWikiPlantaFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNombrePlantaWInfo.text = args.plantaWiki
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoWikiPlantaVM::class.java)
        // TODO: Use the ViewModel
    }

}