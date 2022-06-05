package mx.itesm.bcr.plantifybcr

import android.app.Activity.RESULT_OK
import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView;
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.ui.notifications.NotificationsFragment
import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import mx.itesm.bcr.plantifybcr.databinding.ActivityLoginAppBinding
import mx.itesm.bcr.plantifybcr.databinding.AgregarPlantaFragmentBinding

import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaVM
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.HashMap
import kotlin.reflect.typeOf

class AgregarPlantaFrag : Fragment(), OnFragmentActionsListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""
    private lateinit var manager: FragmentManager
    private lateinit var binding: AgregarPlantaFragmentBinding
    private var listener: OnFragmentActionsListener? = null
    private val opcionesIluminacion = arrayOf("Natural","Artificial","Ninguna")
    private var opcionesGrupo = arrayListOf<String>()
    private var iluminacion = ""
    private var grupo = ""
    private var nombrePlanta = ""
    private var hora = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        opcionesGrupo.add("Ninguno")
        binding = AgregarPlantaFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root
        binding.btnAgregarPlanta.setOnClickListener {
            if(binding.tvNombre.toString() == "" || iluminacion == "" || hora == ""){
                //MENSAJE DE ERROR NO AGREGAMOS EL USUARIO
                AlertDialog.Builder(requireContext()).apply {
                  setTitle("Faltan datos")
                  setMessage("Debes elegir un nombre, una iluminación y una hora de riego")
                  setPositiveButton("Ok",null)
                }.show()
            }else{
            agregarPlanta()
            }
        }
        binding.btnElegirHora.setOnClickListener {
            mostrarTimePicker()
        }
        //PRUEBA SUBIR IMAGENES
        binding.addImg.setOnClickListener {
            pedirPermisos()
        }
        binding.btnIluminacion.setOnClickListener {
            val builderSingle = AlertDialog.Builder(requireContext())
            builderSingle.setTitle("Iluminación")
            builderSingle.setPositiveButton(getString(android.R.string.ok)){
                dialog,_ ->
                dialog.dismiss()
            }
            builderSingle.setSingleChoiceItems(opcionesIluminacion,3){
                dialog,which ->
                iluminacion = opcionesIluminacion[which]
            }
            builderSingle.show()
        }
        binding.btnAgregarGrupoAGF.setOnClickListener {
            val builderSingle  = AlertDialog.Builder(requireContext())
            builderSingle.setTitle("Grupos")
            builderSingle.setPositiveButton(getString(android.R.string.ok)){
                    dialog,_ ->
                dialog.dismiss()
            }
            builderSingle.setSingleChoiceItems(opcionesGrupo.toTypedArray(),opcionesGrupo.size){
                    dialog,which ->
                grupo = opcionesGrupo[which]
            }
            builderSingle.show()
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
          Toast.makeText(requireContext(),"Debes dar permiso a la aplicacion",Toast.LENGTH_SHORT).show()
    }
    }
    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val data = it.data?.data
            binding.ivImg.setImageURI(data)

            val database = Firebase.database
            val myRef = database.getReference("/Usuarios/$_tokken/imagenesPlantas/")
            val FileUri = data
            val Folder: StorageReference = FirebaseStorage.getInstance().getReference().child("/Usuarios/$_tokken/imagenesPlantas/")
            val file_name: StorageReference = Folder.child("imagen$nombrePlanta" + FileUri!!.lastPathSegment)
            file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                file_name.downloadUrl.addOnSuccessListener { uri->

                    val hashMap = HashMap<String,String>()
                    hashMap["url$nombrePlanta"] = java.lang.String.valueOf(uri)
                    myRef.setValue(hashMap)
                    Log.d("Mensaje","se subio correctamente")
                    nombrePlanta = ""
                    hora = ""
                    iluminacion = ""
                    grupo = ""
                    binding.tvNombre.text.clear()
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("La planta se agrego correctamente!")
                        setPositiveButton("Ok",null)
                    }.show()
                }
            }
        }
    }
    private fun elegirFotodeGaleria() {
        val intent = Intent(Intent(Intent.ACTION_GET_CONTENT))
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }


    private fun mostrarTimePicker() {
        val timePicker = TimePIcker{time-> onTimeSelected(time)}
        manager = childFragmentManager
        timePicker.show(manager,"time")
    }
    private fun onTimeSelected(time:String){
        hora = time
    }

    private fun agregarPlanta() {
        val baseDatos = Firebase.database
        nombrePlanta = binding.tvNombre.text.toString()
        val horaRiego = hora
        val planta = Planta(nombrePlanta,horaRiego,iluminacion,grupo)
        if(grupo == ""){
            grupo == "ninguno"
        }
        val referencia = baseDatos.getReference("/Usuarios/$_tokken/Plantas/$nombrePlanta")
        referencia.setValue(planta)
        pedirPermisos()
        /*
        nombrePlanta = ""
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
            //Obtenemos el tokken que paso la activity login a main activity
            viewModel.tokken.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                _tokken = it.toString()
                configurarGrupos()
            })
        }, 100)
    }

    private fun configurarGrupos() {
        val baseDatos = Firebase.database
        val referenciaGrupos = baseDatos.getReference("/Usuarios/$_tokken/Grupos")
        referenciaGrupos.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(grupo in snapshot.children){
                    var grupoOpc = grupo.child("/nombre").value
                    opcionesGrupo.add(grupoOpc.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionsListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onClickFragmentButton() {

    }
}


