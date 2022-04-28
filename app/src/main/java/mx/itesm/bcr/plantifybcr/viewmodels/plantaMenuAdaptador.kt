package mx.itesm.bcr.plantifybcr.viewmodels

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRecycler
import mx.itesm.bcr.plantifybcr.R

class plantaMenuAdaptador: RecyclerView.Adapter<plantaMenuAdaptador.ViewHolder>() {

    val titles = arrayOf("Planta 1","Planta 2","Planta 3")
    val percentages = arrayOf("12%","56%","88%")
    val images = intArrayOf(R.drawable.plant1,R.drawable.plant2,R.drawable.plant3)
    var listener: ListenerRecycler? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_planta,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemPercentage.text = percentages[i]
        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemView.setOnClickListener {
            listener?.itemClicked(i)

        }
    }

    override fun getItemCount(): Int {
        return titles.size
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