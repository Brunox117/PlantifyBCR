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
        super.onViewCreated(view, savedInstanceState)
        descargarDatos()
    }

    private fun descargarDatos() {
        val baseDatos = Firebase.database
        val referenciaPlanta = baseDatos.getReference("/Wiki/${args.plantaWiki}")
        referenciaPlanta.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Buscando datos ${args.plantaWiki}")
                var nombre = snapshot.child("/Nombre").value
                var Info = snapshot.child("/Info").value
                binding.tvNombrePlantaWInfo.text = nombre.toString()
                binding.tvInfoWikiPlanta.text = Info.toString()
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