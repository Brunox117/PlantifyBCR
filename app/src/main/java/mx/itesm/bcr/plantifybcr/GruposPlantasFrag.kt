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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.GruposPlantasFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.ui.notifications.NotificationsFragmentDirections
import mx.itesm.bcr.plantifybcr.viewmodels.Grupo
import mx.itesm.bcr.plantifybcr.viewmodels.GruposAdaptador
import mx.itesm.bcr.plantifybcr.viewmodels.GruposPlantasVM
import mx.itesm.bcr.plantifybcr.viewmodels.plantaGrupoEspAdaptador

class GruposPlantasFrag : Fragment() {

    private var _binding: GruposPlantasFragmentBinding? = null
    private lateinit var adapter: GruposAdaptador
    private val binding get() = _binding!!
    private val hviewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""

    private lateinit var viewModel: GruposPlantasVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GruposPlantasFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root

        //RecyclerView de los grupos.
        val recyclerView = _binding?.rvGruposUsuario
        adapter = GruposAdaptador()
        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter

        binding.ivAgregarGrupo.setOnClickListener {
            val action = GruposPlantasFragDirections.actionGruposPlantasFragToAgregarGrupoFrag()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        referenciaGrupos.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var arrGrupos = mutableListOf<Grupo>()
                    for(grupo in snapshot.children){
                        var d:Map<String,String> = grupo.value as Map<String, String>
                        val grupoArr = grupo.getValue(Grupo::class.java)
                        if (grupoArr != null) {
                            arrGrupos.add(grupoArr)
                        }
                    }
                    adapter.setData(arrGrupos.toTypedArray())
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }

}