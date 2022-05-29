package mx.itesm.bcr.plantifybcr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.ActivityLoginAppBinding

class LoginApp : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAppBinding
    private lateinit var tokken: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityLoginAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            println("Comenzando autenticacion")
            autenticar()
        }
        //CODIGO CALIDAD
        binding.btnLoginEditor.setOnClickListener{
            autenticarEditor()
        }
        //CODIGO CALIDAD
        verificarLogin()
    }
    //CODIGO CALIDAD ===============================================================================
    private fun autenticarEditor() {
        print("ENtramos a autenticar")
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher2.launch(signInIntent)
    }

    private val signInLauncher2 = registerForActivityResult(

        FirebaseAuthUIActivityResultContract()
    ) {
            res ->
        this.onSignInResultEditor(res)
    }
    private fun onSignInResultEditor(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK){
            val usuario = FirebaseAuth.getInstance().currentUser
            //Creamos el usuario
            val baseDatos = Firebase.database
            tokken = usuario?.uid.toString()
            val nombre = usuario?.displayName.toString()
            val correo = usuario?.email.toString()
            val tipoUsuario = "editorWiki"
            val user = Usuario(tokken, nombre, correo,tipoUsuario)
            val Casa = "Casa"
            val referencia = baseDatos.getReference("/Usuarios/$tokken/infoUsuario")
            referencia.setValue(user)
            val referencia3 = baseDatos.getReference("/Usuarios/$tokken/Grupos/Casa")
            referencia3.setValue(Casa)
            entrarApp()
        }else{
            println("Error en tus datos")
        }
    }

    //CODIGO CALIDAD ===============================================================================
    private fun verificarLogin() {
        val usuario = FirebaseAuth.getInstance().currentUser
        tokken = usuario?.uid.toString()
        if(usuario != null){
            println("Bienvenido")
            entrarApp()
        }
    }

    private val signInLauncher = registerForActivityResult(

        FirebaseAuthUIActivityResultContract()
    ) {
            res ->
        this.onSignInResult(res)
    }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK){
            val usuario = FirebaseAuth.getInstance().currentUser
            //Creamos el usuario
            val baseDatos = Firebase.database
            tokken = usuario?.uid.toString()
            val nombre = usuario?.displayName.toString()
            val correo = usuario?.email.toString()
            val tipoUsuario = "sinplan"
            val user = Usuario(tokken, nombre, correo,tipoUsuario)
            val Casa = "Casa"
            val referencia = baseDatos.getReference("/Usuarios/$tokken/infoUsuario")
            referencia.setValue(user)
            val referencia3 = baseDatos.getReference("/Usuarios/$tokken/Grupos/Casa")
            referencia3.setValue(Casa)
            entrarApp()
        }else{
            println("Error en tus datos")
        }
    }

    private fun entrarApp() {
        val intApp = Intent(this,MainActivity::class.java)
        intApp.putExtra("tokken",tokken)
        startActivity(intApp)
        //Borrar pantalla login
        finish()
    }

    private fun autenticar() {
        print("ENtramos a autenticar")
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }
}