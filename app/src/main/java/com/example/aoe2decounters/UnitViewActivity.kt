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
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
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

        var title = findViewById<TextView>(R.id.unit_name)
        var description = findViewById<TextView>(R.id.unit_description)
        var civ = findViewById<TextView>(R.id.unit_civ)
        var image = findViewById<ImageView>(R.id.unit_image)

        Log.d("Check", unit_name.toString())

        title.text = unit_name.toString()
        description.text = unit_description.toString()
        civ.text = unit_civ.toString()
        image.setImageResource(this.resources.getIdentifier(unit_image.toString(), "drawable", this.packageName))

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerViewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this, unitsList = unitsList)

        recyclerView = findViewById<RecyclerView>(R.id.counters_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }

    }
}