package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.bcr.plantifybcr.databinding.WikiAdminFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragmentDirections
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdminAdaptador

class WikiAdmin : Fragment(),ListenerRAdmin {
    //Calidad
    private var _binding: WikiAdminFragmentBinding? = null
    private lateinit var adaptador: plantaWikiAdminAdaptador
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = WikiAdmin()
    }

    private lateinit var viewModel: WikiAdminVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WikiAdminFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root
        binding.imAgregarP.setOnClickListener {
            val accion = WikiAdminDirections.actionWikiAdminToAgregarPlantaWiki()
            findNavController().navigate(accion)
        }
        val recyclerView = _binding?.rvPlantaWikiA
        adaptador = plantaWikiAdminAdaptador()

        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adaptador
        adaptador.listenerAdmin = this

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun itemClickedEditar(position: Int) {
        val plantaWikiA = adaptador.titles[position]
        println("Click en editar de $plantaWikiA")
    }

    override fun itemClickedBorrar(position: Int) {
        val plantaWikiA = adaptador.titles[position]
        println("Click en borrar de $plantaWikiA")
    }

}