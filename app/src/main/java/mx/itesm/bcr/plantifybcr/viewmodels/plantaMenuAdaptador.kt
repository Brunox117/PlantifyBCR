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
import mx.itesm.bcr.plantifybcr.ui.home.HomeFragment
import mx.itesm.bcr.plantifybcr.ui.home.HomeViewModel

class plantaMenuAdaptador(): RecyclerView.Adapter<plantaMenuAdaptador.ViewHolder>() {

    val titles = mutableListOf<String>()
    var percentages = arrayOf("12%")
    var images = intArrayOf(R.drawable.plant1)
    var iluminacion = mutableListOf<String>()
    var listener: ListenerRecycler? = null
    private var arrHome = HomeFragment()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_planta,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        for(planta in arrHome.arrPlantas){
            titles.add(planta.nombre)
            iluminacion.add(planta.hora)
        }
        viewHolder.itemTitle.text = titles.toTypedArray()[i]
        viewHolder.itemPercentage.text = percentages[i]
        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemView.setOnClickListener {
            listener?.itemClickedPlanta(i)
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