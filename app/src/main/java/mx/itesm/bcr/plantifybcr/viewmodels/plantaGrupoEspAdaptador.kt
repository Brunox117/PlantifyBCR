package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRE
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.Planta
import mx.itesm.bcr.plantifybcr.R
import com.bumptech.glide.Glide

class plantaGrupoEspAdaptador(): RecyclerView.Adapter<plantaGrupoEspAdaptador.ViewHolder>()
{
    //Adaptador opcional, en caso de requerir funciones específicas en el grupo de plantas, usar este.
    val titles = mutableListOf<String>()
    val urls = mutableListOf<String>()
    val images = intArrayOf(R.drawable.plant1, R.drawable.plant2, R.drawable.plant3)
    var listener: ListenerRecycler? = null
    var listenerRE: ListenerRE? = null

    fun setData(arrPlantas: Array<Planta>){
        for(planta in arrPlantas){
            titles.add(planta.nombre)
            urls.add(planta.url)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): plantaGrupoEspAdaptador.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_grupoesp,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: plantaGrupoEspAdaptador.ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        val imgPlanta = viewHolder.itemView.findViewById<ImageView>(R.id.ivPlantaG)
        var url = urls[i]
        if(url != ""){
            Glide.with(viewHolder.itemView.context).load(url).into(imgPlanta)
        }else{
            url = "https://firebasestorage.googleapis.com/v0/b/plantifybcr-71577.appspot.com/o/Usuarios%2F33I63MXlbpgM6JWcgcKVkLmnjam2%2FimagenesPlantas%2FDEFAULT%2FimagenDEFAULTmsf%3A66?alt=media&token=c2397cbd-f6eb-492f-bfe5-7369c45057ac"
            Glide.with(viewHolder.itemView.context).load(url).into(imgPlanta)
        }
        viewHolder.itemView.setOnClickListener {
            listener?.itemClickedPlanta(i)
        }
        viewHolder.itemView.findViewById<ImageView>(R.id.ivBorrarGrupoEsp).setOnClickListener {
            listenerRE?.itemClickedBorrar(i)
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.ivPlantaG)
            itemTitle = itemView.findViewById(R.id.tvNombrePlantaG)
        }
    }
}