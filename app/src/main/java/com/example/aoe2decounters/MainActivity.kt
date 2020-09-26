package com.example.aoe2decounters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerViewManager: RecyclerView.LayoutManager
    private var unitsList: MutableList<Unit> = ArrayList()

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)

        drawer = findViewById(R.id.drawer)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        var units_array = resources.getStringArray(R.array.Units)

        var unit_name: String
        var unit_description: String
        var unit_civilization: String
        var unit_type: String
        var unit_image: String
        for (myUnit in units_array){
            Log.d("Ident", myUnit)
            unit_name = getString(resources.getIdentifier(myUnit, "string", this.packageName))
            unit_description = getString(resources.getIdentifier(myUnit.toString() + "D", "string", this.packageName))
            unit_civilization = getString(resources.getIdentifier(myUnit.toString() + "C", "string", this.packageName))
            unit_type = getString(resources.getIdentifier(myUnit.toString() + "T", "string", this.packageName))
            unit_image = getString(resources.getIdentifier(myUnit.toString() + "I", "string", this.packageName))

            unitsList.add(Unit(unit_name, unit_civilization, unit_type, unit_description, unit_image))
            Log.d("UnitsArray", ": $unit_name, " + R.string.Arambai)
        }

//        unitsList.add(Unit("Cataphract", R.string.CataphractDes.toString(), arrayOf()))
//        unitsList.add(Unit("Spearman", counters_input = arrayOf("Archer Line", "Militia Line"), unit_info_input = "General Unit"))
//        unitsList.add(Unit("Paladin", "General Unit", arrayOf("Spearman")))

        recyclerViewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(this, unitsList)

        recyclerView = findViewById<RecyclerView>(R.id.main_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        var searchItem: MenuItem? = menu?.findItem(R.id.main_menu)
        var searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerViewAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_menu_home -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.drawer_menu_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            R.id.drawer_menu_feedback -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "plain/text"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("fivosiliadis@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "AoE2DE Counters Feedback: ")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                startActivity(Intent.createChooser(intent, ""))
            }
            R.id.drawer_menu_rate -> {
                val uri: Uri = Uri.parse("market://details?id=$packageName")
                val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(myAppLinkToMarket)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show()
                }
            }
            else -> {

            }
        }
        return true
    }
}