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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.GrupoEspPlantasFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel
import mx.itesm.bcr.plantifybcr.viewmodels.AgregarPlantaVM
import mx.itesm.bcr.plantifybcr.viewmodels.GrupoEspPlantasVM
import mx.itesm.bcr.plantifybcr.viewmodels.plantaGrupoEspAdaptador
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdaptador

class GrupoEspPlantasFrag : Fragment(), ListenerRecycler, ListenerRE {

    private val args: GrupoEspPlantasFragArgs by navArgs()
    private var _binding: GrupoEspPlantasFragmentBinding? = null
    private lateinit var adapter: plantaGrupoEspAdaptador
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
        adapter = plantaGrupoEspAdaptador()
        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter
        adapter.listener = this
        adapter.listenerRE = this
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

        val referenciaPlantas = baseDatos.getReference("/Usuarios/$_tokken/Plantas")
        referenciaPlantas.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var arrPlantas = mutableListOf<Planta>()
                    for(planta in snapshot.children){
                        var d:Map<String,String> = planta.value as Map<String, String>
                        val plantaArr = planta.getValue(Planta::class.java)
                        if (plantaArr != null && plantaArr.grupo == args.nombreGrupo) {
                            arrPlantas.add(plantaArr)
                        }
                    }
                    adapter.setData(arrPlantas.toTypedArray())
                    adapter.notifyDataSetChanged()
                }
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

    override fun itemClickedPlanta(position: Int) {
        val planta = adapter.titles[position]
        println("Click en $planta")
        val accion = GrupoEspPlantasFragDirections.actionGrupoEspPlantasFragToPlantaEspFrag(planta,_tokken)
        findNavController().navigate(accion)
    }

    override fun itemClickedGrupo(position: Int) {
        TODO("Not yet implemented")
    }

    override fun itemClickedEditar(position: Int) {
        TODO("Not yet implemented")
    }

    override fun itemClickedBorrar(position: Int) {
        val planta = adapter.titles[position]
        println("Configurar Borrar en: $planta")
    }

}