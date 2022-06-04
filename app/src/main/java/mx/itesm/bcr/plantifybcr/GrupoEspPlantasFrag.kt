package mx.itesm.bcr.plantifybcr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.GrupoEspPlantasFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.GrupoEspPlantasVM
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador

class GrupoEspPlantasFrag : Fragment() {

    private val args: GrupoEspPlantasFragArgs by navArgs()
    private var _binding: GrupoEspPlantasFragmentBinding? = null
    private lateinit var adapter: plantaWikiAdaptador
    private val binding get() = _binding!!
    private val hviewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""

    private lateinit var viewModel: GrupoEspPlantasVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GrupoEspPlantasFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root

        //RecyclerView de las plantas de un grupo en específico.
        val recyclerView = _binding?.rvPlantasGrupoEsp
        adapter = plantaWikiAdaptador()
        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView7.text = "Grupo de plantas: ${args.nombreGrupo}"
        Handler().postDelayed(Runnable {
                hviewModel.tokken.observe(viewLifecycleOwner, Observer {
                    _tokken = it.toString()
                })
                descargarDatosNube()
            },100)

    }

    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        val referenciaGrupos = baseDatos.getReference("/Usuarios/$_tokken/Grupos")
        referenciaGrupos.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                println(snapshot.child("/${args.nombreGrupo}").value)
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GrupoEspPlantasVM::class.java)
        // TODO: Use the ViewModel
    }

}