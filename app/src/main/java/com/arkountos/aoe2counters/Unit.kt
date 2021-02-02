package com.arkountos.aoe2counters

import java.util.*
import kotlin.Comparator

class Unit (private val unit_name_input: String, private val unit_civ_input: String, private val unit_type_input: String, private val unit_description_input: String, private val unit_image_input: String){
    val unitName = unit_name_input
    val unitCiv: String = unit_civ_input
    val unitType: String = unit_type_input
    val unitDescription: String = unit_description_input
    val unitImage: String = unit_image_input.toLowerCase(Locale.ROOT)

}

class UnitNameComparator : Comparator<Unit?> {
    override fun compare(o1: Unit?, o2: Unit?): Int {
        return o1!!.unitName.compareTo(o2!!.unitName)
    }
}