package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.*
import com.bumptech.glide.Glide

class plantaWikiAdaptador(): RecyclerView.Adapter<plantaWikiAdaptador.ViewHolder>(){

    /* val titles= arrayOf("Cactus","Lavanda", "Vainilla")  Arreglo inicial empleado en la creación de rv. */
    val urls = mutableListOf<String>() // Areglo de Urls para las imgs.
    var titles2= mutableListOf<String>() //Arreglo donde se guardan los titulos de las plantas.
    val images = intArrayOf(R.drawable.plant1,R.drawable.plant2,R.drawable.plant3)
    var listener: ListenerRecycler? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_plantawiki,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles2[i]
        // Insertamos la imagen con Glide.
        val imgPlanta = viewHolder.itemView.findViewById<ImageView>(R.id.ivPlantaWiki)
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
    }

    fun setData(arrPlantas: Array<PlantaWiki>){
        for(planta in arrPlantas){
            if(planta.nombreC != null || planta.nombreC != ""){
                titles2.add(planta.nombreC)
            }
            urls.add(planta.url)
        }
    }

    override fun getItemCount(): Int {
        return titles2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.ivPlantaWiki)
            itemTitle = itemView.findViewById(R.id.tvNombrePlantaWiki)
        }
    }
}