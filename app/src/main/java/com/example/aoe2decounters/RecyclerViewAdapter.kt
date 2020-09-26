package com.example.aoe2decounters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


class RecyclerViewAdapter(private val context: Context, private val unitsList: MutableList<Unit>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(), Filterable  {
    private var unitsListFull: MutableList<Unit> = unitsList.toMutableList()

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var unit_image: ImageView = itemView.findViewById(R.id.unit_image)
        var unit_name: TextView = itemView.findViewById(R.id.unit_name)
        var unit_civ: TextView = itemView.findViewById(R.id.unit_civ)
        var unit_description: TextView = itemView.findViewById(R.id.unit_description)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = unitsList[position]
        holder.unit_name.text = currentItem.unit_name
        holder.unit_civ.text = currentItem.unit_civ
        holder.unit_description.text = currentItem.unit_description
        holder.unit_image.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun getItemCount(): Int {
        return unitsList.size
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Unit> = mutableListOf()

                if (constraint == null || constraint.isEmpty()){
                    filteredList.addAll(unitsListFull)
                }
                else {
                    for (item in unitsListFull){
                        if (item.unit_name.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT).trim()
                            )
                        ) {
                            filteredList.add(item)
                        }
                    }
                }

                var results: FilterResults = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                unitsList.clear()
                unitsList.addAll(results!!.values as MutableList<Unit>)
                notifyDataSetChanged()
            }
        }
    }
}
