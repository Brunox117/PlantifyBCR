package mx.itesm.bcr.plantifybcr

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.AgregarPlantaWikiFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaWikiVM

class agregarPlantaWiki : Fragment() {
    //Calidad
    private val viewModel: AgregarPlantaWikiVM by activityViewModels()
    private var _tokken = ""
    private lateinit var manager: FragmentManager
    private lateinit var binding: AgregarPlantaWikiFragmentBinding
    private var nombre = ""
    private var nombreC = ""
    private var clima = ""
    private var usos = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AgregarPlantaWikiFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root
        binding.btnAgregarWikiAdmin.setOnClickListener {
            if(binding.etNombreAddWiki.text.toString() == "" || binding.etNombreCWiki.text.toString() == ""
                || binding.etTipoClima.text.toString() == "" || binding.etUsos.text.toString() == "") {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Faltan datos")
                    setMessage("Debes llenar cada casilla con la información correspondiente.")
                    setPositiveButton("Ok",null)
                }.show()
            }else{
                agregarWiki()
            }
        }
        return root
    }

    private fun agregarWiki() {
        val baseDatos = Firebase.database
        nombre = binding.etNombreAddWiki.text.toString()
        nombreC = binding.etNombreCWiki.text.toString()
        clima = binding.etTipoClima.text.toString()
        usos = binding.etUsos.text.toString()
        val plantaWiki = PlantaWiki(nombre,nombreC,clima, usos)
        val referencia = baseDatos.getReference("/Wiki/$nombre")
        referencia.setValue(plantaWiki)
        nombre = ""
        nombreC = ""
        clima = ""
        usos = ""
        AlertDialog.Builder(requireContext()).apply {
            setTitle("La planta se agregó correctamente!")
            setPositiveButton("Ok",null)
        }.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}