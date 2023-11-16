package com.glion.gridbuild.data

import com.glion.gridbuild.R
import com.glion.gridbuild.model.CardData
import com.glion.gridbuild.model.GridRowData

class GridDataSource() {
    fun getData(): List<GridRowData>{
        return listOf(
            GridRowData(CardData(R.drawable.architecture, R.string.architecture, 58), CardData(R.drawable.business, R.string.business, 121)),
            GridRowData(CardData(R.drawable.crafts, R.string.crafts, 78),  CardData(R.drawable.culinary, R.string.culinary, 118)),
            GridRowData(CardData(R.drawable.design, R.string.design, 423), CardData(R.drawable.drawing, R.string.drawing, 92)),
            GridRowData(CardData(R.drawable.fashion, R.string.fashion, 165), CardData(R.drawable.film, R.string.film, 164)),
            GridRowData(CardData(R.drawable.gaming, R.string.gaming, 326), CardData(R.drawable.lifestyle, R.string.lifestyle, 305)),
            GridRowData(CardData(R.drawable.music, R.string.music, 212), CardData(R.drawable.painting, R.string.painting, 172)),
            GridRowData(CardData(R.drawable.photography, R.string.photography, 321), CardData(R.drawable.tech, R.string.tech, 118)),
        )
    }
}