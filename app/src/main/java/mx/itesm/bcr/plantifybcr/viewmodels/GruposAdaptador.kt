package mx.itesm.bcr.plantifybcr.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bcr.plantifybcr.ListenerRE
import mx.itesm.bcr.plantifybcr.Planta
import mx.itesm.bcr.plantifybcr.R


class GruposAdaptador(): RecyclerView.Adapter<GruposAdaptador.ViewHolder>()
{
    val titles = mutableListOf<String>()
    var listener: ListenerRE? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        init {
            itemTitle = itemView.findViewById(R.id.tvGrupos)
        }
    }

    fun setData(arrPlantas: Array<Grupo>){
        for(planta in arrPlantas){
            titles.add(planta.nombre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_grupos,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]

        viewHolder.itemView.findViewById<ImageView>(R.id.ivBorrarGrupos).setOnClickListener {
            listener?.itemClickedBorrar(i)
        }

        viewHolder.itemView.findViewById<ImageView>(R.id.ivEditarGrupos).setOnClickListener {
            listener?.itemClickedEditar(i)
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}