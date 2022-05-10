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
import android.content.Context
import android.os.Handler
import androidx.fragment.app.activityViewModels
import mx.itesm.bcr.plantifybcr.databinding.ActivityLoginAppBinding
import mx.itesm.bcr.plantifybcr.databinding.AgregarPlantaFragmentBinding

import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaVM
import java.util.*

class AgregarPlantaFrag : Fragment(), OnFragmentActionsListener {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""
    private lateinit var binding: AgregarPlantaFragmentBinding
    private var listener: OnFragmentActionsListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AgregarPlantaFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root
        binding.btnAgregarPlanta.setOnClickListener {
            agregarPlanta()
        }
        return root
    }

    private fun agregarPlanta() {
        val baseDatos = Firebase.database
        val nombre = binding.tvNombre.text.toString()
        val horaRiego = binding.tvHora.text.toString()
        var iluminacion = "Artificial"
        val planta = Planta(nombre,horaRiego,iluminacion)
        val referencia = baseDatos.getReference("/Usuarios/$_tokken/Plantas/$nombre")
        referencia.setValue(planta)
        binding.tvNombre.text.clear()
        binding.tvHora.text.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
            //Obtenemos el tokken que paso la activity login a main activity
            viewModel.tokken.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                _tokken = it.toString()
            })
        }, 250)
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


