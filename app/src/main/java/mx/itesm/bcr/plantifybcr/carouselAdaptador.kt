package mx.itesm.bcr.plantifybcr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.viewmodels.Grupo

class carouselAdaptador(): RecyclerView.Adapter<carouselAdaptador.ViewHolder>() {

    val titles = arrayOf("Casa","Recámara","Sala de estar")
    val titles2 = mutableListOf<String>()
    val images = intArrayOf(R.drawable.circleedges,R.drawable.circleedges2,R.drawable.circleedges3)
    var listener: ListenerRecycler? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        init {
            itemImage = itemView.findViewById(R.id.ivGrupoTarjeta)
            itemTitle = itemView.findViewById(R.id.tvNombreGrupoTarjeta)
        }
    }

    fun setData(arrGrupos: Array<Grupo>){
        for(grupo in arrGrupos){
            titles2.add(grupo.nombre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.carrousel_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles2[i]
        viewHolder.itemImage.setImageResource(images.random())
        viewHolder.itemView.setOnClickListener {
            listener?.itemClickedGrupo(i)
        }
    }

    override fun getItemCount(): Int {
        return titles2.size
    }
}