package mx.itesm.bcr.plantifybcr.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.MainActivity
import mx.itesm.bcr.plantifybcr.databinding.ActivityLoginAppBinding
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.viewmodels.plantaMenuAdaptador

class HomeFragment : Fragment(), ListenerRecycler {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: plantaMenuAdaptador
    private val viewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = _binding?.rvPlantaHome
        adapter = plantaMenuAdaptador()

        viewModel.tokken.observe(viewLifecycleOwner, Observer {
            _tokken = it.toString()
        })

        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter
        adapter.listener = this

        return root

    }



    override fun itemClicked(position: Int){
        val planta = adapter.titles[position]
        println("Click en $planta")
        val accion = HomeFragmentDirections.actionHomeFragToPlantaEspFrag(planta)
        findNavController().navigate(accion)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}