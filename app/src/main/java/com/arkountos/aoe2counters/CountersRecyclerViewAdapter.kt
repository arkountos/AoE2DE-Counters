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
        var unit_image: ImageView = itemView.findViewById(R.id.app_icon)
        var unit_name: TextView = itemView.findViewById(R.id.unit_name)
        var unit_civ: TextView = itemView.findViewById(R.id.unit_civ)
//        var unit_description: TextView = itemView.findViewById(R.id.unit_description)
        var unit_civ_image: ImageView = itemView.findViewById(R.id.unit_civ_image)
//        var cardView: CardView = itemView.findViewById(R.id.card)
//        var text_layout: LinearLayout = itemView.findViewById(R.id.text_layout)
//
    }

    fun emptyUnitsList(){
        this.printUnitsList()
        unitsListFull = mutableListOf()
        this.printUnitsList()
    }

    fun setUnitsList(newUnitsListFull: MutableList<Unit>){
        unitsListFull = newUnitsListFull
    }

    fun printUnitsList(){
        Log.d("RecyclerViewAdapter", unitsListFull.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.counters_recyclerview_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = unitsList[position]
        holder.unit_name.text = currentItem.unit_name
        holder.unit_civ.text = currentItem.unit_civ
//        holder.unit_description.text = currentItem.unit_description

        holder.unit_image.setImageResource(context.resources.getIdentifier(currentItem.unit_image, "drawable", context.packageName))

        holder.unit_civ_image.setImageResource(context.resources.getIdentifier(currentItem.unit_civ.toLowerCase(Locale.ROOT), "drawable", context.packageName))
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
                var filteredList: MutableList<Unit> = mutableListOf()

                if (constraint == null || constraint.isEmpty()){
                    filteredList.addAll(unitsListFull)
                }
                else {
                    for (item in unitsListFull){
                        if (item.unit_name.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT).trim()) || (item.unit_civ.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT).trim()))) {
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
