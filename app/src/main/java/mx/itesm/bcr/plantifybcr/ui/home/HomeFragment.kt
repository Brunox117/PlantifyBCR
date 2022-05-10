package mx.itesm.bcr.plantifybcr.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.MainActivity
import mx.itesm.bcr.plantifybcr.Planta
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.viewmodels.plantaMenuAdaptador


class HomeFragment : Fragment(), ListenerRecycler {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: plantaMenuAdaptador
    private val viewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater)
        val root: View = binding.root
        val recyclerView = _binding?.rvPlantaHome
        adapter = plantaMenuAdaptador()
        recyclerView?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView?.adapter = adapter
        adapter.listener = this
        binding.button2.text = "Mis grupos"
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable {
            //Obtenemos el tokken que paso la activity login a main activity
            viewModel.tokken.observe(viewLifecycleOwner, Observer {
                _tokken = it.toString()
            })
            println("El tokken es: $_tokken")
            descargarDatosNube()
        }, 250)

    }


    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        //Obtenemos la informacion del usuario
        val referenciaUsuario = baseDatos.getReference("/Usuarios/$_tokken")
        referenciaUsuario.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Recuperamos el nombre
                var nombre = snapshot.child("/infoUsuario/nombre").value
                binding.tvUsuario.text = nombre.toString()
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
                    if (plantaArr != null) {
                        arrPlantas.add(plantaArr)

                    }
                }
                    adapter.setData(arrPlantas.toTypedArray())
                println("Arreglo de plantas = ${arrPlantas[0]} ===========")
            }
            }
            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }
        })
    }


    override fun itemClicked(position: Int){
        val planta = adapter.titles[position]
        println("Click en $planta")
        val accion = HomeFragmentDirections.actionHomeFragToPlantaEspFrag(planta)
        findNavController().navigate(accion)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}