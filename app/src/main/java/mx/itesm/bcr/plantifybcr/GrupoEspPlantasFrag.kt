package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.bcr.plantifybcr.databinding.GrupoEspPlantasFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.GrupoEspPlantasVM
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador

class GrupoEspPlantasFrag : Fragment() {

    private val args: GrupoEspPlantasFragArgs by navArgs()
    private var _binding: GrupoEspPlantasFragmentBinding? = null
    private lateinit var adapter: plantaWikiAdaptador
    private val binding get() = _binding!!


    private lateinit var viewModel: GrupoEspPlantasVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GrupoEspPlantasFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root

        //RecyclerView de las plantas de un grupo en específico.
        val recyclerView = _binding?.rvPlantasGrupoEsp
        adapter = plantaWikiAdaptador()
        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView7.text = "Grupo de plantas: ${args.nombreGrupo}"
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GrupoEspPlantasVM::class.java)
        // TODO: Use the ViewModel
    }

}