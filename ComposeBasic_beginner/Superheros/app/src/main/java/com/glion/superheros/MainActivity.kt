package com.glion.superheros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.superheros.data.DataSource
import com.glion.superheros.model.Hero
import com.glion.superheros.ui.theme.SuperherosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperherosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HeroApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroApp(){
    Scaffold(
        topBar = {
            HeroTopAppBar()
        }
    ){paddingValues ->
        HeroList(DataSource.loadHeroDatas(), paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopAppBar(modifier: Modifier = Modifier){
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
fun HeroList(heroList: List<Hero>, paddingValues: PaddingValues){
    LazyColumn(contentPadding = paddingValues){
        items(heroList){ hero ->
            HeroCard(hero)
        }
    }
}

@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
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
){
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
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(start = 12.dp, top = 12.dp)
    ){
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
