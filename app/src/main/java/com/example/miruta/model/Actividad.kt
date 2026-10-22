package com.example.miruta.model

data class Actividad(val id:Int, val titulo:String, val descripcion:String, val categoria:String, val prioridad:Int, val completada:Boolean=false)
