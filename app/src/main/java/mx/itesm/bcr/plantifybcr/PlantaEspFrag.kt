package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.PlantaEspFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.PlantaEspVM

class PlantaEspFrag : Fragment() {

    private val args: PlantaEspFragArgs by navArgs()
    private lateinit var binding: PlantaEspFragmentBinding
    private lateinit var viewModel: PlantaEspVM
    private var nombrePlanta = ""
    private var tokken = ""

    companion object {
        fun newInstance() = PlantaEspFrag()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlantaEspFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nombrePlanta = args.nombrePlanta
        tokken = args.tokken
        //Aqui llamariamos a la base de datos
        descargarDatosNube()

    }

    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        val referenciaPlanta = baseDatos.getReference("/Usuarios/$tokken/Plantas/$nombrePlanta")
        referenciaPlanta.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var nombreP = snapshot.child("/nombre").value
                binding.tvNombrePlanta.text = nombreP.toString()
                var tipoIluminacion = snapshot.child("/iluminacion").value
                binding.tvIluminacionPlantaEsp.text = tipoIluminacion.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlantaEspVM::class.java)
        // TODO: Use the ViewModel
    }

}