package mx.itesm.bcr.plantifybcr.ui.home


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.bcr.plantifybcr.*
import mx.itesm.bcr.plantifybcr.databinding.FragmentHomeBinding
import mx.itesm.bcr.plantifybcr.databinding.InfoUsuarioFragmentBinding
import mx.itesm.bcr.plantifybcr.ui.dashboard.DashboardFragmentDirections
import mx.itesm.bcr.plantifybcr.viewmodels.Grupo
import mx.itesm.bcr.plantifybcr.viewmodels.plantaGrupoEspAdaptador
import mx.itesm.bcr.plantifybcr.viewmodels.plantaMenuAdaptador
import com.bumptech.glide.Glide




class HomeFragment : Fragment(), ListenerRecycler {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterPH: plantaMenuAdaptador
    private lateinit var adapterGH: carouselAdaptador
    private lateinit var Usuario: InfoUsuarioFrag
    private lateinit var  adapterGEH: plantaGrupoEspAdaptador
    private val viewModel: HomeViewModel by activityViewModels()
    private var _tokken = ""


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

        //onClick de imageUsuario
        binding.btnUserImage.setOnClickListener {

            val accion = HomeFragmentDirections.actionHomeFragToInfoUsuarioFrag()
            findNavController().navigate(accion)
        }

        //RecyclerView de las plantas
        val recyclerViewPH = _binding?.rvPlantaHome
        adapterPH = plantaMenuAdaptador()
        recyclerViewPH?.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerViewPH?.adapter = adapterPH
        adapterPH.listener = this

        //RecyclerView de los grupos - recyclerViewGrupoHome
        val recyclerViewGH = _binding?.rvGrupoHome
        adapterGH = carouselAdaptador()

        //Inicializamos el adaptador del grupo específico.
        adapterGEH = plantaGrupoEspAdaptador()

        recyclerViewGH?.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerViewGH?.adapter = adapterGH
        adapterGH.listener = this
        //DESABILITAMOS EL RECYCLER VIEW PARA VERIFICAR SI HAY PLANTAS
        binding.rvPlantaHome.visibility = View.GONE
        binding.btnAgregarPlantaHome.visibility = View.GONE
        binding.tvAgregarPlanta.visibility = View.GONE
        binding.btnAgregarPlantaHome.setOnClickListener {
            val accion = HomeFragmentDirections.actionHomeFragToAgregarPlantaFrag()
            findNavController().navigate(accion)
        }
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
        }, 100)
    }

    private fun descargarDatosNube() {
        val baseDatos = Firebase.database
        //Obtenemos la informacion del usuario
        val referenciaUsuario = baseDatos.getReference("/Usuarios/$_tokken")
        val userPic = Firebase.auth.currentUser
        //Recuperamos la url de la foto del usuario
        val photo = userPic?.photoUrl
        referenciaUsuario.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Recuperamos el nombre
                var nombre = snapshot.child("/infoUsuario/nombre").value
                binding.tvUsuario.text = nombre.toString()
                //Colocamos la foto del usuario
                if (userPic != null){
                    Glide.with(requireContext()).load(photo).into(binding.btnUserImage)
                }
                //bindUsuario = InfoUsuarioFrag()
                //bindUsuario.setInfo(nombre.toString())
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
                    binding.rvPlantaHome.visibility = View.VISIBLE
                for(planta in snapshot.children){
                    var d:Map<String,String> = planta.value as Map<String, String>
                    val plantaArr = planta.getValue(Planta::class.java)
                    if (plantaArr != null) {
                        arrPlantas.add(plantaArr)
                    }
                }
                adapterPH.setData(arrPlantas.toTypedArray())
                adapterPH.notifyDataSetChanged()
                }else{
                binding.btnAgregarPlantaHome.visibility = View.VISIBLE
                binding.tvAgregarPlanta.visibility = View.VISIBLE
            }
            }
            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }
        })

        val referenciaGrupos = baseDatos.getReference("/Usuarios/$_tokken/Grupos")
        referenciaGrupos.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var arrGrupos = mutableListOf<Grupo>()
                    for(grupo in snapshot.children){
                        var d:Map<String,String> = grupo.value as Map<String, String>
                        val grupoArr = grupo.getValue(Grupo::class.java)
                        if(grupoArr != null){
                            arrGrupos.add(grupoArr)
                        }
                    }
                    adapterGH.setData(arrGrupos.toTypedArray())
                    adapterGH.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Error: $error")
            }

        })
    }

    override fun itemClickedPlanta(position: Int){
        val planta = adapterPH.titles2[position]
        println("Click en $planta")
        val accion = HomeFragmentDirections.actionHomeFragToPlantaEspFrag(planta,_tokken)
        findNavController().navigate(accion)
    }

    override fun itemClickedGrupo(position: Int) {
        val grupo = adapterGH.titles[position]
        val accion = HomeFragmentDirections.actionHomeFragToGrupoEspPlantasFrag(grupo)
        findNavController().navigate(accion)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

