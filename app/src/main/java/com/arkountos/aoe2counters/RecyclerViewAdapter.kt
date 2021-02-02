package com.arkountos.aoe2counters

import android.content.Context
import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


class RecyclerViewAdapter(private val context: Context, private val unitsList: MutableList<Unit>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(), Filterable  {
    private var unitsListFull: MutableList<Unit> = unitsList.toMutableList()

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var unitImage: ImageView = itemView.findViewById(R.id.app_icon)
        var unitName: TextView = itemView.findViewById(R.id.unit_name)
        var unitCiv: TextView = itemView.findViewById(R.id.unit_civ)
        var unitDescription: TextView = itemView.findViewById(R.id.unit_description)
        var unitCivImage: ImageView = itemView.findViewById(R.id.unit_civ_image)
        var cardView: CardView = itemView.findViewById(R.id.card)
        var textLayout: LinearLayout = itemView.findViewById(R.id.text_layout)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = unitsList[position]
        holder.unitName.text = currentItem.unitName
        holder.unitCiv.text = currentItem.unitCiv
        holder.unitDescription.text = currentItem.unitDescription
        holder.unitDescription.movementMethod = ScrollingMovementMethod()
        holder.unitImage.setImageResource(context.resources.getIdentifier(currentItem.unitImage, "drawable", context.packageName))

        holder.unitCivImage.setImageResource(context.resources.getIdentifier(currentItem.unitCiv.toLowerCase(
            Locale.ROOT
        ), "drawable", context.packageName))
//        holder.cardView.setOnLongClickListener {
//            if (holder.textLayout.layoutParams.height != RecyclerView.LayoutParams.WRAP_CONTENT) {
//                holder.textLayout.layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT
//                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                val scale: Float = context.resources.displayMetrics.density
//                holder.textLayout.layoutParams.height = (150 * scale).toInt()
//            }
//            holder.textLayout.requestLayout()
//            true
//        }
        holder.cardView.setOnClickListener {
            val intent = Intent(context, UnitViewActivity::class.java)
            intent.putExtra("EXTRA_UNIT_NAME", currentItem.unitName)
            intent.putExtra("EXTRA_UNIT_CIV", currentItem.unitCiv)
            intent.putExtra("EXTRA_UNIT_DESCRIPTION", currentItem.unitDescription)
            intent.putExtra("EXTRA_UNIT_IMAGE", currentItem.unitImage)
            context.startActivity(intent)
        }
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
