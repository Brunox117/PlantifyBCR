package mx.itesm.bcr.plantifybcr

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.AgregarGrupoFragmentBinding
import mx.itesm.bcr.plantifybcr.databinding.AgregarPlantaFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.AgregarGrupoVM
import mx.itesm.bcr.plantifybcr.viewmodels.Grupo

class AgregarGrupoFrag : Fragment() {

    companion object {
        fun newInstance() = AgregarGrupoFrag()
    }
    private val viewModelH: HomeViewModel by activityViewModels()
    private var _tokken = ""
    private lateinit var viewModel: AgregarGrupoVM
    private lateinit var binding: AgregarGrupoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AgregarGrupoFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root
        binding.btnAgregaGrupo.setOnClickListener {
            println("Verificando ===================================")
            println(binding.etNombreGrupo.text.toString())
            if(binding.etNombreGrupo.text.toString() != ""){

                agregarGrupo()
            }else{
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Debes elegir un nombre!")
                    setPositiveButton("Ok",null)
                }.show()
            }

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
            //Obtenemos el tokken que paso la activity login a main activity
            viewModelH.tokken.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                _tokken = it.toString()
            })
        }, 100)
    }

    private fun agregarGrupo() {
        val baseDatos = Firebase.database
        val nombreGrupo = binding.etNombreGrupo.text.toString()
        val grupo = Grupo(nombreGrupo)
        val referencia = baseDatos.getReference("/Usuarios/$_tokken/Grupos/$nombreGrupo")
        referencia.setValue(grupo)
        binding.etNombreGrupo.text.clear()
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Grupo agregado!")
            setPositiveButton("Ok",null)
        }.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AgregarGrupoVM::class.java)
        // TODO: Use the ViewModel
    }

}