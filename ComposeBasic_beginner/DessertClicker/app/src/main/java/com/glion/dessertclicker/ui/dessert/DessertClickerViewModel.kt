package com.glion.dessertclicker.ui.dessert

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.glion.dessertclicker.R
import com.glion.dessertclicker.data.Datasource.dessertList
import com.glion.dessertclicker.data.DessertClickerUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertClickerUIState())
    val uiState: StateFlow<DessertClickerUIState> = _uiState.asStateFlow()

    /**
     * 공유 버튼 눌렀을때 실행되는 함수
     */
    fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(R.string.share_text, dessertsSold, revenue)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            ContextCompat.startActivity(intentContext, shareIntent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * 보여질 디저트를 결정하는 함수
     */
    private fun determineDessertToShow(
        dessertsSold: Int
    ): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertIndex
    }

    /**
     * 디저트 이미지 클릭했을때 실행되는 함수
     */
    fun clickDessertImage(){
        _uiState.update { currentState-> // 상태 업데이트 해줌
            val updateIndex = determineDessertToShow(currentState.dessertSold)
            currentState.copy(
                currentIndex = updateIndex,
                revenue = currentState.revenue + currentState.currentDessert.price,
                dessertSold = currentState.dessertSold.inc(),
                currentDessert = dessertList[updateIndex]
            )
        }
    }
}