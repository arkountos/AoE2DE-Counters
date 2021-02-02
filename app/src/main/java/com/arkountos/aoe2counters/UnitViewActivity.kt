package com.arkountos.aoe2counters

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var unit_name = intent.extras?.get("EXTRA_UNIT_NAME")
        var unit_civ = intent.extras?.get("EXTRA_UNIT_CIV")
        var unit_description = intent.extras?.get("EXTRA_UNIT_DESCRIPTION")
        var unit_image = intent.extras?.get("EXTRA_UNIT_IMAGE")

        var unit_counters: Array<String> = resources.getStringArray(resources.getIdentifier((unit_name.toString() + "Counters").replace("\\s".toRegex(),""), "array", this.packageName))

        var title = findViewById<TextView>(R.id.unit_name)
        var description = findViewById<TextView>(R.id.unit_description)
        var civ = findViewById<TextView>(R.id.unit_civ)
        var image = findViewById<ImageView>(R.id.app_icon)
        var civ_image = findViewById<ImageView>(R.id.unit_civ_image)
        var unique_unit_checkbox = findViewById<CheckBox>(R.id.show_unique_checkbox)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Log.d("Check", unit_name.toString())

        title.text = unit_name.toString()
        description.text = unit_description.toString()
        civ.text = unit_civ.toString()
        image.setImageResource(this.resources.getIdentifier(unit_image.toString(), "drawable", this.packageName))
        civ_image.setImageResource(this.resources.getIdentifier(unit_civ.toString().toLowerCase(), "drawable", this.packageName))

        var counter_name: String
        var counter_des: String
        var counter_civ: String
        var counter_type: String
        var counter_image: String


        // THIS IMPLEMENTATION SUCKS. But I cannot for the love of me get the adapter notified of the data changed, so I have to create a new recyclerViewAdapter everytime the data contained changes, then apply it to the recyclerView.
        // Why did notifyDataSetChanged() not work?
        unique_unit_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            // If Checkbox is ticked...
            if (unique_unit_checkbox.isChecked) {
                var unit_counters_no_unique: MutableSet<String> = mutableSetOf()
                for (counterUnit in unit_counters) {
                    var temp_counter_civ = getString(
                        resources.getIdentifier(
                            counterUnit.toString() + "C",
                            "string",
                            this.packageName
                        )
                    )
                    if (temp_counter_civ == "Generic") {
                        unit_counters_no_unique.add(counterUnit)
                    }
                }
                unitsList = mutableListOf()
                for (counterUnit in unit_counters_no_unique){
                    Log.d("Ident", counterUnit)
                    counter_name = getString(resources.getIdentifier(counterUnit.toString(), "string", this.packageName))
                    counter_des = getString(resources.getIdentifier(counterUnit.toString() + "D", "string", this.packageName))
                    counter_civ = getString(resources.getIdentifier(counterUnit.toString() + "C", "string", this.packageName))
                    counter_type = getString(resources.getIdentifier(counterUnit.toString() + "T", "string", this.packageName))
                    counter_image = getString(resources.getIdentifier(counterUnit.toString() + "I", "string", this.packageName))

                    unitsList.add(Unit(counter_name, counter_civ, counter_type, counter_des, counter_image))
                }

                // For some reason I cannot notify the existing adapter of the data changed, so I will create a new adapter and patch it on the recyclerView every time the checkbox is pressed.
                // This is not efficient, but I cannot get it to work otherwise.
                recyclerViewManager = LinearLayoutManager(this)
                recyclerViewAdapter = CountersRecyclerViewAdapter(this, unitsList = unitsList)

                recyclerView = findViewById<RecyclerView>(R.id.counters_recyclerview).apply {
                    setHasFixedSize(true)
                    layoutManager = recyclerViewManager
                    adapter = recyclerViewAdapter
                }


            }
            // If checkbox is unticked
            else{
                unitsList = mutableListOf()
                for (counterUnit in unit_counters){
                    Log.d("Ident", counterUnit)
                    counter_name = getString(resources.getIdentifier(counterUnit.toString(), "string", this.packageName))
                    counter_des = getString(resources.getIdentifier(counterUnit.toString() + "D", "string", this.packageName))
                    counter_civ = getString(resources.getIdentifier(counterUnit.toString() + "C", "string", this.packageName))
                    counter_type = getString(resources.getIdentifier(counterUnit.toString() + "T", "string", this.packageName))
                    counter_image = getString(resources.getIdentifier(counterUnit.toString() + "I", "string", this.packageName))

                    unitsList.add(Unit(counter_name, counter_civ, counter_type, counter_des, counter_image))
                }

                // For some reason I cannot notify the existing adapter of the data changed, so I will create a new adapter and patch it on the recyclerView every time the checkbox is pressed.
                // This is not efficient, but I cannot get it to work otherwise.
                recyclerViewManager = LinearLayoutManager(this)
                recyclerViewAdapter = CountersRecyclerViewAdapter(this, unitsList = unitsList)

                recyclerView = findViewById<RecyclerView>(R.id.counters_recyclerview).apply {
                    setHasFixedSize(true)
                    layoutManager = recyclerViewManager
                    adapter = recyclerViewAdapter
                }

            }
        }

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

        Log.d("UnitViewActivity", "Initialize recyclerview")
        recyclerViewManager = LinearLayoutManager(this)
        recyclerViewAdapter = CountersRecyclerViewAdapter(this, unitsList = unitsList)

        recyclerView = findViewById<RecyclerView>(R.id.counters_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}