package com.arkountos.aoe2counters

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


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

        val unitName = intent.extras?.get("EXTRA_UNIT_NAME")
        val unitCiv = intent.extras?.get("EXTRA_UNIT_CIV")
        val unitDescription = intent.extras?.get("EXTRA_UNIT_DESCRIPTION")
        val unitImage = intent.extras?.get("EXTRA_UNIT_IMAGE")

        val unitCounters: Array<String> = resources.getStringArray(
            resources.getIdentifier(
                (unitName.toString() + "Counters").replace(
                    "\\s".toRegex(),
                    ""
                ), "array", this.packageName
            )
        )

        val title = findViewById<TextView>(R.id.unit_name)
        val description = findViewById<TextView>(R.id.unit_description)
        val civ = findViewById<TextView>(R.id.unit_civ)
        val image = findViewById<ImageView>(R.id.app_icon)
        val civImage = findViewById<ImageView>(R.id.unit_civ_image)
        val uniqueUnitCheckbox = findViewById<CheckBox>(R.id.show_unique_checkbox)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Log.d("Check", unitName.toString())

        title.text = unitName.toString()
        description.text = unitDescription.toString()
        civ.text = unitCiv.toString()
        image.setImageResource(
            this.resources.getIdentifier(
                unitImage.toString(),
                "drawable",
                this.packageName
            )
        )
        civImage.setImageResource(
            this.resources.getIdentifier(
                unitCiv.toString().toLowerCase(
                    Locale.ROOT
                ), "drawable", this.packageName
            )
        )

        description.movementMethod = ScrollingMovementMethod()

        var counterName: String
        var counterDes: String
        var counterCiv: String
        var counterType: String
        var counterImage: String


        // THIS IMPLEMENTATION SUCKS. But I cannot for the love of me get the adapter notified of the data changed, so I have to create a new recyclerViewAdapter every time the data contained changes, then apply it to the recyclerView.
        // Why did notifyDataSetChanged() not work?
        uniqueUnitCheckbox.setOnCheckedChangeListener { _, _ ->
            // If Checkbox is ticked...
            if (uniqueUnitCheckbox.isChecked) {
                val unitCountersNoUnique: MutableSet<String> = mutableSetOf()
                for (counterUnit in unitCounters) {
                    val tempCounterCiv = getString(
                        resources.getIdentifier(
                            counterUnit + "C",
                            "string",
                            this.packageName
                        )
                    )
                    if (tempCounterCiv == "Generic") {
                        unitCountersNoUnique.add(counterUnit)
                    }
                }
                unitsList = mutableListOf()
                for (counterUnit in unitCountersNoUnique){
                    Log.d("Ident", counterUnit)
                    counterName = getString(
                        resources.getIdentifier(
                            counterUnit,
                            "string",
                            this.packageName
                        )
                    )
                    counterDes = getString(
                        resources.getIdentifier(
                            counterUnit + "D",
                            "string",
                            this.packageName
                        )
                    )
                    counterCiv = getString(
                        resources.getIdentifier(
                            counterUnit + "C",
                            "string",
                            this.packageName
                        )
                    )
                    counterType = getString(
                        resources.getIdentifier(
                            counterUnit + "T",
                            "string",
                            this.packageName
                        )
                    )
                    counterImage = getString(
                        resources.getIdentifier(
                            counterUnit + "I",
                            "string",
                            this.packageName
                        )
                    )

                    unitsList.add(
                        Unit(
                            counterName,
                            counterCiv,
                            counterType,
                            counterDes,
                            counterImage
                        )
                    )
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
                for (counterUnit in unitCounters){
                    Log.d("Ident", counterUnit)
                    counterName = getString(
                        resources.getIdentifier(
                            counterUnit,
                            "string",
                            this.packageName
                        )
                    )
                    counterDes = getString(
                        resources.getIdentifier(
                            counterUnit + "D",
                            "string",
                            this.packageName
                        )
                    )
                    counterCiv = getString(
                        resources.getIdentifier(
                            counterUnit + "C",
                            "string",
                            this.packageName
                        )
                    )
                    counterType = getString(
                        resources.getIdentifier(
                            counterUnit + "T",
                            "string",
                            this.packageName
                        )
                    )
                    counterImage = getString(
                        resources.getIdentifier(
                            counterUnit + "I",
                            "string",
                            this.packageName
                        )
                    )

                    unitsList.add(
                        Unit(
                            counterName,
                            counterCiv,
                            counterType,
                            counterDes,
                            counterImage
                        )
                    )
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

        for (counterUnit in unitCounters){
            Log.d("Ident", counterUnit)
            counterName = getString(
                resources.getIdentifier(
                    counterUnit,
                    "string",
                    this.packageName
                )
            )
            counterDes = getString(
                resources.getIdentifier(
                    counterUnit + "D",
                    "string",
                    this.packageName
                )
            )
            counterCiv = getString(
                resources.getIdentifier(
                    counterUnit + "C",
                    "string",
                    this.packageName
                )
            )
            counterType = getString(
                resources.getIdentifier(
                    counterUnit + "T",
                    "string",
                    this.packageName
                )
            )
            counterImage = getString(
                resources.getIdentifier(
                    counterUnit + "I",
                    "string",
                    this.packageName
                )
            )

            unitsList.add(Unit(counterName, counterCiv, counterType, counterDes, counterImage))
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