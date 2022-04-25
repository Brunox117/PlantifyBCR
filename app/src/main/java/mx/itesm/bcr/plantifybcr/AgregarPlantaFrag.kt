package mx.itesm.bcr.plantifybcr

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaVM

class AgregarPlantaFrag : Fragment() {
    companion object {
        fun newInstance() = AgregarPlantaFrag()
    }

    private lateinit var viewModel: AgregarPlantaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.agregar_planta_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AgregarPlantaVM::class.java)
        // TODO: Use the ViewModel
    }


}
/*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel: AgregarPlantaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root
        AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
            activity?.finish()
            val intLogin = Intent(requireContext(), LoginApp::class.java)
            startActivity(intLogin)
        }
    return root
    }
*/