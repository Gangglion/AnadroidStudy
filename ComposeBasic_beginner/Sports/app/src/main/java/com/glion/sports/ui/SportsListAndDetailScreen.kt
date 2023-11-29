package com.glion.sports.ui

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.glion.sports.R
import com.glion.sports.model.Sport

@Composable
fun SportsListAndDetail(
    sports: List<Sport>,
    currentSport: Sport,
    onClick: (Sport)-> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
    ){
        SportsList(
            sports = sports,
            onClick = onClick,
            contentPadding = innerPadding,
            modifier = Modifier
                .weight(2f)
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                )
        )
        val activity = LocalContext.current as Activity
        SportsDetail(
            selectedSport = currentSport,
            contentPadding = innerPadding,
            onBackPressed = { activity.finish() },
            modifier = Modifier
                .weight(3f)
        )
    }
}