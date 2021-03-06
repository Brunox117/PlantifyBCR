package mx.itesm.bcr.plantifybcr

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.databinding.InfoUsuarioFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.InfoUsuarioVM
import com.bumptech.glide.Glide


class InfoUsuarioFrag : Fragment() {

    private val viewModelH: HomeViewModel by activityViewModels()
    private var _binding: InfoUsuarioFragmentBinding? = null
    private val binding get() = _binding!!
    private var _tokken = ""
    private var tipoUsuarioC = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoUsuarioFragmentBinding.inflate(inflater)
        //CODIGO Planes de usuario ===============================================================================

        binding.btnEditorIU.setOnClickListener {
            if(tipoUsuarioC != "editorWiki"){
                volverEditor()
            }else{
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Ya eres editor de la wiki!")
                    setPositiveButton("Ok",null)
                }.show()
            }
        }

        //CODIGO listeners para planes ===============================================================================
        binding.btnLogOutIU.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                activity?.finish()
                val intLogin = Intent(requireContext(), LoginApp::class.java)
                startActivity(intLogin)
            }
        }
        val root: View = binding.root
        return root
    }



    //CODIGO CALIDAD ===================================================================================

    private fun volverEditor() {
        val tipoPlan = "editorWiki"
        val baseDatos = Firebase.database
        val referencia = baseDatos.getReference("/Usuarios/$_tokken/infoUsuario/tipoUsuario")
        referencia.setValue(tipoPlan)
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Tu solicitud como editor ha sido aceptada!")
            setPositiveButton("Ok",null)
        }.show()
    }

    //CODIGO CALIDAD ===================================================================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            viewModelH.tokken.observe(viewLifecycleOwner, Observer {
                _tokken = it.toString()
            })
            println("Descargando datos de la nube")
            descargarDatosNube()
        },100)
    }

    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        val userPic = Firebase.auth.currentUser
        //Recuperamos la url de la foto del usuario
        val photo = userPic?.photoUrl
        val referenciaUsuario = baseDatos.getReference("/Usuarios/$_tokken/infoUsuario")
        referenciaUsuario.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var nombre = snapshot.child("/nombre").value
                var correo = snapshot.child("/correo").value
                tipoUsuarioC = snapshot.child("/tipoUsuario").value.toString()
                binding.adUsuario.text = nombre.toString()
                binding.adEmail.text = correo.toString()
                //Colocamos la foto del usuario
                if (userPic != null){
                    Glide.with(requireContext()).load(photo).into(binding.ivUsuarioInfo)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }



}