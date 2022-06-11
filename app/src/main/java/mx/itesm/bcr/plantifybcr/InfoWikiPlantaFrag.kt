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
import mx.itesm.bcr.plantifybcr.databinding.InfoWikiPlantaFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.InfoWikiPlantaVM
import com.bumptech.glide.Glide

class InfoWikiPlantaFrag : Fragment() {

    private val args: InfoWikiPlantaFragArgs by navArgs()
    private lateinit var binding: InfoWikiPlantaFragmentBinding
    private lateinit var viewModel: InfoWikiPlantaVM

    companion object {
        fun newInstance() = InfoWikiPlantaFrag()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoWikiPlantaFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        descargarDatos()
    }

    private fun descargarDatos() {
        val baseDatos = Firebase.database
        val referenciaPlanta = baseDatos.getReference("/Wiki/${args.plantaWiki}")
        referenciaPlanta.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Buscando datos ${args.plantaWiki}")
                var nombres = snapshot.child("/nombre").value
                var nombreC = snapshot.child("/nombreC").value
                var clima = snapshot.child("/clima").value
                var usos = snapshot.child("/usos").value
                var imagen = snapshot.child("/url").value
                binding.tvNombresWikiEsp.text = nombres.toString()
                binding.tvNombreCWikiEsp.text = nombreC.toString()
                binding.tvClimaWikiEsp.text = clima.toString()
                binding.tvUsosWikiEsp.text = usos.toString()
                if(imagen != ""){
                    Glide.with(requireContext()).load(imagen).into(binding.ivPlantaWikiEsp)
                }else{
                    val url = "https://firebasestorage.googleapis.com/v0/b/plantifybcr-71577.appspot.com/o/Usuarios%2F33I63MXlbpgM6JWcgcKVkLmnjam2%2FimagenesPlantas%2FDEFAULT%2FimagenDEFAULTmsf%3A66?alt=media&token=c2397cbd-f6eb-492f-bfe5-7369c45057ac"
                    Glide.with(requireContext()).load(url).into(binding.ivPlantaWikiEsp)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                print("Error $error")
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoWikiPlantaVM::class.java)
        // TODO: Use the ViewModel
    }

}