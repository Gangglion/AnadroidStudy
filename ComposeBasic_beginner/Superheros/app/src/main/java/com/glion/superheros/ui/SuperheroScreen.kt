package com.glion.superheros.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.superheros.R
import com.glion.superheros.data.HerosRepository
import com.glion.superheros.model.Hero
import com.glion.superheros.model.SolHero
import com.glion.superheros.ui.theme.SuperherosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroApp() {
    Scaffold(
        topBar = {
            HeroTopAppBar()
        }
    ) { paddingValues ->
//        HeroList(DataSource.loadHeroDatas(), paddingValues)

        // ##### 솔루션 Hero List #####
        SolutionHeroList(heroSolList = HerosRepository.heros, contentPadding = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        }
    )
}

@Composable
fun HeroList(heroList: List<Hero>, paddingValues: PaddingValues) {
    LazyColumn(contentPadding = paddingValues) {
        items(heroList) { hero ->
            HeroCard(hero)
        }
    }
}

@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HeroInfo(hero.heroTitle, hero.heroContent)
            Spacer(modifier.weight(1f))
            HeroProfile(hero.heroProfile)
        }
    }
}

@Composable
fun HeroProfile(
    @DrawableRes heroProfile: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = heroProfile),
        contentDescription = null,
        modifier = modifier
            .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
            .size(48.dp, 48.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HeroInfo(
    @StringRes heroTitle: Int,
    @StringRes heroContent: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(start = 12.dp, top = 12.dp)
    ) {
        Text(
            text = stringResource(id = heroTitle),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = stringResource(id = heroContent),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ############## 솔루션 참고 목록 아이템 컴포저블 ############## //
@Composable
fun HeroItemSolution(
    solHero: SolHero,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp), // MEMO : Row 의 사이즈 지정
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // MEMO : 하단의 Spacer 와 Box 가 차지하는 공간을 제외한 나머지 공간 차지
            ) {
                Text(
                    text = stringResource(id = solHero.nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(id = solHero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.small)
            ) {
                Image(
                    painterResource(id = solHero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SolutionHeroList(
    modifier: Modifier = Modifier,
    heroSolList: List<SolHero>,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val visibleState = remember {
        MutableTransitionState(false).apply { // MEMO : MutableTransitionState 타입 변수. 현재 애니메이션의 진행상태 정보
            // MEMO : initialState - 초기 시작 상태, targetState - 목표 상태. 컴포저블이 현 상태에서 다른 상태로 이동할때 어떻게 작동해야 하는지 쉽게 정의할 수 있음
            targetState = true
        }
    }
    AnimatedVisibility( // MEMO : 내부의 콘텐츠에 대해 나타남과 사라짐을 애니메이션으로 처리할 수 있게 한다. 항목이 나타나는것에 대한 애니메이션 처리를 위해 추가되었다.
        visibleState = visibleState,
        enter = fadeIn( // 나타날때 효과 정의
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        ),
        exit = fadeOut(), // 사라질때 효과 정의
        modifier = modifier
    ) { // 람다 내부에 나타날 Content 들어감. 이 Content 들에 효과를 줌
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(heroSolList) { index, solHero ->
                HeroItemSolution(
                    solHero = solHero,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateEnterExit( // MEMO : 나타남과 사라짐에 대한 애니메이션 - LazyColumn 내부 요소들에 대해서도 애니메이션 처리를 추가해줌
                            enter = slideInVertically( // MEMO : 애니메이션 시작할때(나타날때)
                                // slideInVertically, slideInHorizontally 를 사용할 수 있다. 애니메이션의 시작위치를 각각 initialOffsetY, initialOffsetX 로 정할 수 있다.
                                // slideInVertically - initialOffsetY, slideInHorizontally - initialOffsetX
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) } // 초기 위치를 반환하는 람다식. 이를 통해 요소 전체 높이의 백분율로 지정이 가능하다.
                                // MEMO : {  } 내부의 값으로 애니메이션 시작 위치를 정할 수 있다.
                            )
                        )
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewHeroAppLight(){
    SuperherosTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HeroApp()
        }
    }
}
@Preview
@Composable
fun PreviewHeroAppDark(){
    SuperherosTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HeroApp()
        }
    }
}