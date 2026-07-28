package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Workplace(
    val name: String,
    val address: String,
    val phone: String
)

class WorkplaceAdapter(private val workplaceList: List<Workplace>) :
    RecyclerView.Adapter<WorkplaceAdapter.WorkplaceViewHolder>() {

    class WorkplaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWorkplaceName: TextView = itemView.findViewById(R.id.tvWorkplaceName)
        val tvWorkplaceAddress: TextView = itemView.findViewById(R.id.tvWorkplaceAddress)
        val tvWorkplacePhone: TextView = itemView.findViewById(R.id.tvWorkplacePhone)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkplaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workplace, parent, false)
        return WorkplaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkplaceViewHolder, position: Int) {
        val workplace = workplaceList[position]
        holder.tvWorkplaceName.text = workplace.name
        holder.tvWorkplaceAddress.text = workplace.address
        holder.tvWorkplacePhone.text = workplace.phone


        holder.btnEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, activity_workplace_add::class.java)
            intent.putExtra("WORKPLACE_NAME", workplace.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = workplaceList.size
}