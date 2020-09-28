package com.example.aoe2decounters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UnitViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: CountersRecyclerViewAdapter
    private lateinit var recyclerViewManager: RecyclerView.LayoutManager
    private var unitsList: MutableList<Unit> = ArrayList()

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_view)

        var unit_name = intent.extras?.get("EXTRA_UNIT_NAME")
        var unit_civ = intent.extras?.get("EXTRA_UNIT_CIV")
        var unit_description = intent.extras?.get("EXTRA_UNIT_DESCRIPTION")
        var unit_image = intent.extras?.get("EXTRA_UNIT_IMAGE")

        var unit_counters = resources.getStringArray(resources.getIdentifier((unit_name.toString() + "Counters").replace("\\s".toRegex(),""), "array", this.packageName))

        var title = findViewById<TextView>(R.id.unit_name)
        var description = findViewById<TextView>(R.id.unit_description)
        var civ = findViewById<TextView>(R.id.unit_civ)
        var image = findViewById<ImageView>(R.id.app_icon)

        Log.d("Check", unit_name.toString())

        title.text = unit_name.toString()
        description.text = unit_description.toString()
        civ.text = unit_civ.toString()
        image.setImageResource(this.resources.getIdentifier(unit_image.toString(), "drawable", this.packageName))

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var counter_name: String
        var counter_des: String
        var counter_civ: String
        var counter_type: String
        var counter_image: String
        for (counterUnit in unit_counters){
            Log.d("Ident", counterUnit)
            counter_name = getString(resources.getIdentifier(counterUnit.toString(), "string", this.packageName))
            counter_des = getString(resources.getIdentifier(counterUnit.toString() + "D", "string", this.packageName))
            counter_civ = getString(resources.getIdentifier(counterUnit.toString() + "C", "string", this.packageName))
            counter_type = getString(resources.getIdentifier(counterUnit.toString() + "T", "string", this.packageName))
            counter_image = getString(resources.getIdentifier(counterUnit.toString() + "I", "string", this.packageName))

            unitsList.add(Unit(counter_name, counter_civ, counter_type, counter_des, counter_image))
//            Log.d("UnitsArray", ": $unit_name, " + R.string.Arambai)
        }


        recyclerViewManager = LinearLayoutManager(this)
        recyclerViewAdapter = CountersRecyclerViewAdapter(this, unitsList = unitsList)

        recyclerView = findViewById<RecyclerView>(R.id.counters_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }

    }
}