package mx.itesm.bcr.plantifybcr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.databinding.WikiAdminFragmentBinding
import mx.itesm.bcr.plantifybcr.viewmodels.plantaWikiAdminAdaptador

class WikiAdmin : Fragment(),ListenerRE {
    //Calidad
    private var _binding: WikiAdminFragmentBinding? = null
    private lateinit var adaptador: plantaWikiAdminAdaptador
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = WikiAdmin()
    }

    private lateinit var viewModel: WikiAdminVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WikiAdminFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root
        binding.imAgregarP.setOnClickListener {
            val accion = WikiAdminDirections.actionWikiAdminToAgregarPlantaWiki()
            findNavController().navigate(accion)
        }
        val recyclerView = _binding?.rvPlantaWikiA
        adaptador = plantaWikiAdminAdaptador()

        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adaptador
        adaptador.listenerAdmin = this
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        descargarDatosNube()
    }

    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        val referenciaWiki = baseDatos.getReference("/Wiki/")
        referenciaWiki.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var arrPlantas = mutableListOf<PlantaWiki>()
                    for(planta in snapshot.children){
                        var d:Map<String,String> = planta.value as Map<String, String>
                        var plantaArr = planta.getValue(PlantaWiki::class.java)
                        if(plantaArr !=null){
                            arrPlantas.add(plantaArr)
                        }
                    }
                    //AQUI MANDAMOS EL ARRAY AL ADAPTADOR
                    adaptador.setData(arrPlantas.toTypedArray())
                    adaptador.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error $error")
            }

        })
    }

    override fun itemClickedEditar(position: Int) {
        val plantaWikiA = adaptador.titles[position]
        println("Click en editar de $plantaWikiA")
    }

    override fun itemClickedBorrar(position: Int) {
        val plantaWikiA = adaptador.titles[position]
        println("Click en borrar de $plantaWikiA")
    }

}