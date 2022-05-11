package mx.itesm.bcr.plantifybcr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class carouselAdaptador(): RecyclerView.Adapter<carouselAdaptador.ViewHolder>() {

    val titles = arrayOf("Jardín","Recámara","Sala de estar")
    val images = intArrayOf(R.drawable.garden,R.drawable.bedroom,R.drawable.livingroom)
    var listener: ListenerRecycler? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        init {
            itemImage = itemView.findViewById(R.id.ivGrupoTarjeta)
            itemTitle = itemView.findViewById(R.id.tvNombreGrupoTarjeta)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.carrousel_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemView.setOnClickListener {
            listener?.itemClicked(i)
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}