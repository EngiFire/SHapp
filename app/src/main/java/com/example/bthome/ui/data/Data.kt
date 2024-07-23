package com.example.bthome.ui.data



data class L(var x : Boolean, var y : Boolean, var z : Boolean)
data class S(var x : Boolean, var y : Boolean, var z : Boolean)
data class F(var value: Boolean)
data class B(var value: Boolean)
data class T(var value: Int)
data class H(var value: Int)

data class Room(var l: L, val s: S, val f: F, val b: B, val t: T, val h: H)
data class Kitchen(val l: L, val s: S, val f: F, val b: B, val t: T, val h: H)
data class Bath(val l: L, val s: S, val f: F, val b: B, val t: T, val h: H)
data class Hallway(val l: L, val s: S, val f: F, val b: B, val t: T, val h: H)


