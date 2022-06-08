package mx.itesm.bcr.plantifybcr.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mx.itesm.bcr.plantifybcr.databinding.FragmentNotificationsBinding
import mx.itesm.bcr.plantifybcr.*
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragmentDirections


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val viewModel: NotificationsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)*/

        _binding = FragmentNotificationsBinding.inflate(inflater)
        val root: View = binding.root
        binding.btnGrupos.setOnClickListener {
            val accion = NotificationsFragmentDirections.actionAjustesFragToGruposPlantasFrag()
            findNavController().navigate(accion)
        }
        binding.btnAcerca.setOnClickListener {
            val accion = NotificationsFragmentDirections.actionAjustesFragToAcercaDeFrag()
            findNavController().navigate(accion)
        }
        binding.btnSensores.setOnClickListener {
            val accion = NotificationsFragmentDirections.actionAjustesFragToSensoresFrag()
            findNavController().navigate(accion)
        }
        binding.btnTutoriales.setOnClickListener {
            val accion = NotificationsFragmentDirections.actionAjustesFragToTutorialesFrag()
            findNavController().navigate(accion)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
