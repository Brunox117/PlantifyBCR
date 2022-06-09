package mx.itesm.bcr.plantifybcr

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.AgregarPlantaWikiFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaWikiVM

class agregarPlantaWiki : Fragment() {
    private val args: agregarPlantaWikiArgs by navArgs()
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
        binding.tvNombrePEditando.visibility = View.GONE
        binding.lyCientifico.visibility = View.GONE
        if(args.nombrePlanta != null){
            binding.tvNombrePEditando.text = args.nombrePlanta
            binding.tvNombrePEditando.visibility = View.VISIBLE
            binding.btnAgregarWikiAdmin.setOnClickListener {
                if(binding.etNombreAddWiki.text.toString() == ""
                    && binding.etTipoClima.text.toString() == "" && binding.etUsos.text.toString() == ""){
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("No se edito nada")
                        setMessage("Debes cambiar al menos un campo")
                        setPositiveButton("Ok",null)
                    }.show()
                }else{
                    agregarWiki2()
                }
            }
        }
        if(args.nombrePlanta == null){
            binding.lyCientifico.visibility = View.VISIBLE
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
        }
        return root
    }

    private fun agregarWiki2() {
        val baseDatos = Firebase.database
        nombre = binding.etNombreAddWiki.text.toString()
        clima = binding.etTipoClima.text.toString()
        usos = binding.etUsos.text.toString()
        if(nombre != ""){
            val referencia = baseDatos.getReference("Wiki/${args.nombrePlanta}/nombre")
            referencia.setValue(nombre)
        }
        if(clima != ""){
            val referencia = baseDatos.getReference("Wiki/${args.nombrePlanta}/clima")
            referencia.setValue(clima)
        }
        if(usos != ""){
            val referencia = baseDatos.getReference("Wiki/${args.nombrePlanta}/usos")
            referencia.setValue(usos)
        }
        nombre = ""
        nombreC = ""
        clima = ""
        usos = ""
        binding.etNombreAddWiki.text.clear()
        binding.etNombreCWiki.text.clear()
        binding.etTipoClima.text.clear()
        binding.etUsos.text.clear()
        AlertDialog.Builder(requireContext()).apply {
            setTitle("La planta se edito correctamente!")
            setPositiveButton("Ok",null)
        }.show()
    }

    private fun agregarWiki() {
        val baseDatos = Firebase.database
        nombre = binding.etNombreAddWiki.text.toString()
        nombreC = binding.etNombreCWiki.text.toString()
        clima = binding.etTipoClima.text.toString()
        usos = binding.etUsos.text.toString()
        val plantaWiki = PlantaWiki(nombre,nombreC,clima, usos)
        val referencia = baseDatos.getReference("/Wiki/$nombreC")
        referencia.setValue(plantaWiki)
        nombre = ""
        nombreC = ""
        clima = ""
        usos = ""
        binding.etNombreAddWiki.text.clear()
        binding.etNombreCWiki.text.clear()
        binding.etTipoClima.text.clear()
        binding.etUsos.text.clear()
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