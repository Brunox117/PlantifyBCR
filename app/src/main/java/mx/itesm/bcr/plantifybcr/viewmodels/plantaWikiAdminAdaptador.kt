package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRE
import mx.itesm.bcr.plantifybcr.PlantaWiki
import mx.itesm.bcr.plantifybcr.R

class plantaWikiAdminAdaptador: RecyclerView.Adapter<plantaWikiAdminAdaptador.ViewHolder>(){

    //CALIDAD
    val titles= arrayOf("Cactus","Lavanda", "Vainilla")
    val images = intArrayOf(R.drawable.plant1, R.drawable.plant2, R.drawable.plant3)
    var titles2 = mutableListOf<String>()
    var listenerAdmin: ListenerRE? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): plantaWikiAdminAdaptador.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_adminwiki,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: plantaWikiAdminAdaptador.ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles2[i]
        viewHolder.itemImage.setImageResource(images[1])
        //CAlIDAD
        viewHolder.itemView.findViewById<ImageView>(R.id.ivEditar).setOnClickListener {
            listenerAdmin?.itemClickedEditar(i)
        }

        viewHolder.itemView.findViewById<ImageView>(R.id.ivBorrar).setOnClickListener {
            listenerAdmin?.itemClickedBorrar(i)
        }
    }
    fun setData(arrPlantas: Array<PlantaWiki>){
        for(planta in arrPlantas){
            titles2.add(planta.nombre)
        }
    }
    override fun getItemCount(): Int {
        return titles2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        //Calidad

        init {
            itemImage = itemView.findViewById(R.id.ivPlantaWikiA)
            itemTitle = itemView.findViewById(R.id.tvNombrePlantaWikiA)

        }
    }
}