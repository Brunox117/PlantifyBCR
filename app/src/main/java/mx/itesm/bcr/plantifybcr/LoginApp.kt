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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityLoginAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            println("Comenzando autenticacion")
            autenticar()
        }
        verificarLogin()
    }
    private fun verificarLogin() {
        val usuario = FirebaseAuth.getInstance().currentUser
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
            val nombre = usuario?.displayName.toString()
            val tokken = usuario?.uid.toString()
            val correo = usuario?.email.toString()
            val user = Usuario(tokken, nombre, correo)
            val referencia = baseDatos.getReference("/Usuarios/$tokken")
            referencia.setValue(user)
            entrarApp()
        }else{
            println("Error en tus datos")
        }
    }

    private fun entrarApp() {
        val intApp = Intent(this,MainActivity::class.java)
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