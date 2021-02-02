package com.arkountos.aoe2counters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


class CountersRecyclerViewAdapter(private val context: Context, private val unitsList: MutableList<Unit>) : RecyclerView.Adapter<CountersRecyclerViewAdapter.MyViewHolder>(), Filterable  {
    private var unitsListFull: MutableList<Unit> = unitsList.toMutableList()

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var unitImage: ImageView = itemView.findViewById(R.id.app_icon)
        var unitName: TextView = itemView.findViewById(R.id.unit_name)
        var unitCiv: TextView = itemView.findViewById(R.id.unit_civ)
        var unitCivImage: ImageView = itemView.findViewById(R.id.unit_civ_image)
    }

    private fun emptyUnitsList(){
        this.printUnitsList()
        unitsListFull = mutableListOf()
        this.printUnitsList()
    }

    private fun setUnitsList(newUnitsListFull: MutableList<Unit>){
        unitsListFull = newUnitsListFull
    }

    private fun printUnitsList(){
        Log.d("RecyclerViewAdapter", unitsListFull.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.counters_recyclerview_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = unitsList[position]
        holder.unitName.text = currentItem.unitName
        holder.unitCiv.text = currentItem.unitCiv
//        holder.unit_description.text = currentItem.unit_description

        holder.unitImage.setImageResource(context.resources.getIdentifier(currentItem.unitImage, "drawable", context.packageName))

        holder.unitCivImage.setImageResource(context.resources.getIdentifier(currentItem.unitCiv.toLowerCase(Locale.ROOT), "drawable", context.packageName))
        Log.d("CHECK", "OKKKK")
//        holder.cardView.setOnLongClickListener {
//            if (holder.text_layout.layoutParams.height != RecyclerView.LayoutParams.WRAP_CONTENT) {
//                holder.text_layout.layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT
//                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                var scale: Float = context.resources.displayMetrics.density;
//                holder.text_layout.layoutParams.height = (150 * scale).toInt()
//            }
//            holder.text_layout.requestLayout()
//            true
//        }
//        holder.cardView.setOnClickListener {
//            var intent = Intent(context, UnitViewActivity::class.java)
//            intent.putExtra("EXTRA_UNIT_NAME", currentItem.unit_name)
//            intent.putExtra("EXTRA_UNIT_CIV", currentItem.unit_civ)
//            intent.putExtra("EXTRA_UNIT_DESCRIPTION", currentItem.unit_description)
//            intent.putExtra("EXTRA_UNIT_IMAGE", currentItem.unit_image)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return unitsList.size
    }

    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<Unit> = mutableListOf()

                if (constraint == null || constraint.isEmpty()){
                    filteredList.addAll(unitsListFull)
                }
                else {
                    for (item in unitsListFull){
                        if (item.unitName.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT).trim()) || (item.unitCiv.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT).trim()))) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
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
