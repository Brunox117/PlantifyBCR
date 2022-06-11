package mx.itesm.bcr.plantifybcr

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.PlantaEspFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragmentDirections
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
        binding.btnEliminarPlanta.setOnClickListener {
            /*AlertDialog.Builder(requireContext()).apply {
                setTitle("Se eliminará la planta $nombrePlanta de manera permanente estás seguro?")
                setPositiveButton("Ok",null)
                setNegativeButton("No",null)
            }.show()*/
            eliminarPlanta()
        }
        return binding.root
    }

    private fun eliminarPlanta() {
        val baseDatos = Firebase.database
        val referenciaPlantaE = baseDatos.getReference("/Usuarios/$tokken/Plantas/$nombrePlanta")
        referenciaPlantaE.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.ref.removeValue()
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("La planta se eliminó correctamente!")
                    setPositiveButton("Ok",null)
                }.show()
                val accion = PlantaEspFragDirections.actionPlantaEspFragToHomeFrag()
                findNavController().navigate(accion)
            }
            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }
        })
        val referenciaImgEliminar = baseDatos.getReference("/Usuarios/$tokken/imagenesPlantas/$nombrePlanta")
        referenciaImgEliminar.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
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
                var horaRiego = snapshot.child("/hora").value
                binding.tvHoraRiego.text = horaRiego.toString()
                val img = snapshot.child("/url").value
                println("Url: $img")
                if(img != null){
                    Glide.with(requireContext()).load(img).into(binding.imgPlanta)
                }else{
                    val url = "https://firebasestorage.googleapis.com/v0/b/plantifybcr-71577.appspot.com/o/Usuarios%2F33I63MXlbpgM6JWcgcKVkLmnjam2%2FimagenesPlantas%2FDEFAULT%2FimagenDEFAULTmsf%3A66?alt=media&token=c2397cbd-f6eb-492f-bfe5-7369c45057ac"
                    Glide.with(requireContext()).load(url).into(binding.imgPlanta)
                }
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