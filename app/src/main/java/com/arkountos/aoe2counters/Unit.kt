package com.arkountos.aoe2counters

class Unit (private val unit_name_input: String, private val unit_civ_input: String, private val unit_type_input: String, private val unit_description_input: String, private val unit_image_input: String){
    val unit_name = unit_name_input
    val unit_civ: String = unit_civ_input
    val unit_type: String = unit_type_input
    val unit_description: String = unit_description_input
    val unit_image: String = unit_image_input.toLowerCase()

//    val counters = counters_input


}

class UnitNameComparator : Comparator<Unit?> {
    override fun compare(o1: Unit?, o2: Unit?): Int {
        return o1!!.unit_name!!.compareTo(o2!!.unit_name)
    }
}