package mx.itesm.bcr.plantifybcr.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.LoginApp
import mx.itesm.bcr.plantifybcr.databinding.FragmentDashboardBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragmentDirections
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador

class DashboardFragment : Fragment(),ListenerRecycler {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adaptador: plantaWikiAdaptador
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = _binding?.rvPlantaWiki
        adaptador = plantaWikiAdaptador()

        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adaptador
        adaptador.listener = this
        return root
    }

    override fun itemClickedPlanta(position: Int) {
        val plantaWiki = adaptador.titles[position]
        println("click en $plantaWiki")
        val accion = DashboardFragmentDirections.actionWikiFragToInfoWikiPlantaFrag(plantaWiki)
        findNavController().navigate(accion)
    }

    override fun itemClickedGrupo(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
