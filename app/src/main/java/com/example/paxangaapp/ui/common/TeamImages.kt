package com.example.paxangaapp.ui.common

import com.example.paxangaapp.R

class TeamImages() {
    // Lista de nombres de imágenes
    companion object {
        private val listaDeImagenes: List<Int> = listOf(
            R.drawable.amorebieta,
            R.drawable.deportivo,
            R.drawable.fiore,
            R.drawable.gimnastics,
            R.drawable.hercules,
            R.drawable.inter,
            R.drawable.malaga,
            R.drawable.manchesterc,
            R.drawable.manchesteru,
            R.drawable.milan,
            R.drawable.roma,
            R.drawable.sporting,
            R.drawable.sunderland,
            R.drawable.swansea,
            R.drawable.valencia,
            R.drawable.valladolid,
            R.drawable.villarreal,
            R.drawable.zaragoza,
            R.drawable.juve,
            R.drawable.oviedo,

            )

        // Función para obtener la lista de nombres de imágenes
        fun obtenerImagenes(): List<Int> {
            return listaDeImagenes
        }
    }
}

