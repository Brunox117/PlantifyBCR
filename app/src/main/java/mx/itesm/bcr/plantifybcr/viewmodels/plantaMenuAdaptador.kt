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
    val titles = arrayOf("Cactus","Hierbabuena","Menta")
    val percentages = arrayOf("12%","56%","88%")
    val images = intArrayOf(R.drawable.plant1,R.drawable.plant2,R.drawable.plant3)
    var listener: ListenerRecycler? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_planta,viewGroup,false)
        return ViewHolder(v)
    }
    fun setData(arrPlantas: Array<Planta>){
        println("Arreglo de plantas del adaptador ${arrPlantas[0]}")
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
            itemImage = itemView.findViewById(R.id.rv_ivPlanta)
            itemTitle = itemView.findViewById(R.id.tvNombreP)
            itemPercentage = itemView.findViewById(R.id.tvAguaP)
        }
    }
}