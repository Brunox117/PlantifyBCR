package mx.itesm.bcr.plantifybcr

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
    private var url = ""

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
                    && binding.etTipoClima.text.toString() == "" && binding.etUsos.text.toString() == "" && url==""){
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
        binding.btnAgregarImgWiki.setOnClickListener {
            if(args.nombrePlanta != null){
                nombreC = args.nombrePlanta.toString()
                pedirPermisos()
            }else {
                nombreC = binding.etNombreCWiki.text.toString()
                pedirPermisos()
            }
        }
        return root
    }

    private fun pedirPermisos() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED -> {
                    elegirFotodeGaleria()
                }else -> requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else {
            elegirFotodeGaleria()
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        if(it == true){
            elegirFotodeGaleria()
        }else{
            Toast.makeText(requireContext(),"Debes dar permiso a la aplicacion", Toast.LENGTH_SHORT).show()
        }
    }
    private fun elegirFotodeGaleria() {
        val intent = Intent(Intent(Intent.ACTION_GET_CONTENT))
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }
    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val data = it.data?.data
            //binding.ivImg.setImageURI(data)
            val database = Firebase.database
            val myRef = database.getReference("/imagenesWiki/$nombreC")
            val FileUri = data
            val Folder: StorageReference = FirebaseStorage.getInstance().getReference().child("/imagenesWiki/$nombreC")
            val file_name: StorageReference = Folder.child("imagen$nombreC" + FileUri!!.lastPathSegment)
            file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                file_name.downloadUrl.addOnSuccessListener { uri->
                    val hashMap = HashMap<String,String>()
                    hashMap["url"] = java.lang.String.valueOf(uri)
                    url = hashMap["url"].toString()
                    myRef.setValue(hashMap)
                    /*nombrePlanta = ""
                    hora = ""
                    iluminacion = ""
                    grupo = ""
                    binding.tvNombre.text.clear()
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("La planta se agrego correctamente!")
                        setPositiveButton("Ok",null)
                    }.show()
                     */
                }
            }
        }
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
        if(url != ""){
            val referencia = baseDatos.getReference("Wiki/${args.nombrePlanta}/url")
            referencia.setValue(url)

        }
        nombre = ""
        nombreC = ""
        clima = ""
        usos = ""
        url = ""
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
        val plantaWiki = PlantaWiki(nombre,nombreC,clima, usos,url)
        val referencia = baseDatos.getReference("/Wiki/$nombreC")
        referencia.setValue(plantaWiki)
        nombre = ""
        nombreC = ""
        clima = ""
        usos = ""
        url = ""
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