package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.databinding.InfoUsuarioFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.InfoUsuarioVM

class InfoUsuarioFrag : Fragment() {

    private lateinit var viewModel: InfoUsuarioVM
    private val viewModelH: HomeViewModel by activityViewModels()
    private var _binding: InfoUsuarioFragmentBinding? = null
    private val binding get() = _binding!!
    private var _tokken = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoUsuarioFragmentBinding.inflate(inflater)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            viewModelH.tokken.observe(viewLifecycleOwner, Observer {
                _tokken = it.toString()
            })
            descargarDatosNube()
        },100)
    }

    fun setInfo(usuario: String) {
        binding.adUsuario.text = usuario
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoUsuarioVM::class.java)
        // TODO: Use the ViewModel
    }

}