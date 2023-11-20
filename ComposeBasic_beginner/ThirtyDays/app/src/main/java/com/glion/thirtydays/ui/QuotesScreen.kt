package com.glion.thirtydays.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.thirtydays.R
import com.glion.thirtydays.model.LifeQuotes
import com.glion.thirtydays.model.QuotesDataList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesApp(){
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ){paddingValues ->
        QuotesList(
            lifeQuotesList = QuotesDataList,
            contentPadding = paddingValues,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_status),
                style = MaterialTheme.typography.displaySmall
            )
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuotesList(
    modifier: Modifier = Modifier,
    lifeQuotesList: List<LifeQuotes>,
    contentPadding: PaddingValues = PaddingValues(0.0.dp)
) {
    val cardVisibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = cardVisibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
        ){
            itemsIndexed(lifeQuotesList){ index, lifeQuotes ->
                QuotesItem(
                    index = index,
                    quotes = lifeQuotes,
                    modifier = Modifier
                        .padding(16.dp)
                        .animateEnterExit( // MEMO : 리스트의 각 항목이 나올때의 애니메이션 지정
                            enter = slideInVertically(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessVeryLow
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun QuotesItem(
    modifier: Modifier = Modifier,
    index: Int,
    quotes: LifeQuotes
){
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ){
        Column(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize( // MEMO : Column 의 size 가 변할때의 애니메이션 지정
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )
        ){
            Text(
                text = stringResource(id = R.string.day).format(index+1),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = quotes.author),
                style = MaterialTheme.typography.titleMedium
            )
            Box{
                val boxModifier = Modifier.padding(8.dp)
                Image(
                    painter = painterResource(id = quotes.imageRes),
                    contentDescription = null,
                    modifier = boxModifier
                        .height(300.dp) // MEMO : 이미지의 height 지정. width 는 기본적으로 wrap_content로 적용된다.
                        .clickable { isExpanded = !isExpanded },
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    modifier = boxModifier
                        .align(Alignment.BottomCenter)
                )
            }
            if(isExpanded){
                Text(
                    text = stringResource(id = quotes.content),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun QuotesItemPreview(){
    val quotes = LifeQuotes(R.string.author_1, R.string.contents_1, R.drawable.image_1)
    QuotesItem(index = 0, quotes = quotes)
}