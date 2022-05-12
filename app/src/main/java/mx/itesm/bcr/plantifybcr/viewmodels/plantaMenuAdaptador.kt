package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.Planta
import mx.itesm.bcr.plantifybcr.R
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel

class plantaMenuAdaptador(): RecyclerView.Adapter<plantaMenuAdaptador.ViewHolder>() {
    var titles = arrayOf("Cactus","Hierbabuena","Menta")
    var percentages = arrayOf("12%","56%","88%")
    var images = intArrayOf(R.drawable.plant1,R.drawable.plant2,R.drawable.plant3)
    var titles2 = mutableListOf<String>()
    var iluminacion = mutableListOf<String>()
    var listener: ListenerRecycler? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_planta,viewGroup,false)
        return ViewHolder(v)
    }
    fun setData(arrPlantas: Array<Planta>){
        for(planta in arrPlantas){
            titles2.add(planta.nombre)
            iluminacion.add(planta.iluminacion)
        }
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles2[i]
        viewHolder.itemPercentage.text = iluminacion[i]
        viewHolder.itemImage.setImageResource(images[1])
        viewHolder.itemView.setOnClickListener {
            listener?.itemClickedPlanta(i)
        }
    }

    override fun getItemCount(): Int {
        return titles2.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemPercentage: TextView
        init {
            itemImage = itemView.findViewById(R.id.ivPlanta)
            itemTitle = itemView.findViewById(R.id.tvNombreP)
            itemPercentage = itemView.findViewById(R.id.tvAguaP)
        }
    }
}