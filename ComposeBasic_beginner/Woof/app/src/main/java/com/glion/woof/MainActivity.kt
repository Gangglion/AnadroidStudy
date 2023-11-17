package com.glion.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.woof.data.DataSource
import com.glion.woof.model.Dog
import com.glion.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    WoofApp(DataSource.loadDogData())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = { 
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_woof_logo),
                    contentDescription = null,
                    modifier = modifier
                        .size(44.dp, 44.dp)
                        .padding(8.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofApp(dogList: List<Dog>){
    Scaffold(
        topBar = {
            WoofTopAppBar()
        }
    ) {paddingValues ->
        LazyColumn(contentPadding = paddingValues){
            items(dogList){dog ->
                DogItem(
                    dog = dog,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
){
    var isExpanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if(isExpanded) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer,
        label = "",
    )

    Card(modifier = modifier){ // MEMO : Card로 매핑해준 이유 - Row로 하면 Row의 배경색과 앱의 배경색이 구분되지 않으므로, primary가 자동으로 적용될 Card로 매핑해주었다.
    // MEMO : Shape.kt 에 medium Shapes 를 재정의 해주었다. Card에서 따로 매핑하지 않아도 바로 적용되는 이유는 Card는 기본적으로 medium Shape를 사용하기 때문이다.
        Column(
            // MEMO : 확장여부에 따라 컨텐츠의 높낮이가 달라지므로, 애니메이션을 이용할 수 있다.
            modifier = Modifier
                .animateContentSize( // MEMO : animationSpec을 사용해서 애니메이션 맞춤설정 가능. 그냥 사용하면 반동없이 애니메이션 효과로 생겼다 사라지기만 한다.
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy, // MEMO : 스프링의 반동 정도
                        stiffness = Spring.StiffnessMedium// MEMO : 스프링의 강성 정도
                    )
                )
                .background(color)
                .clickable { isExpanded = !isExpanded }
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ){
                DogIcon(
                    dogImageRes = dog.dogImageRes,
                    modifier = modifier
                        .align(Alignment.CenterVertically))
                DogInfomation(dogName = dog.dogNameRes, dogAge = dog.dogAge)
                Spacer(modifier.weight(1f)) // MEMO : 유일하게 가중치가 적용되어있기 때문에, Spacer는 Row의 빈공간을 채운다.
                DogItemButton(
                    expanded = isExpanded,
                    onClick = { isExpanded = !isExpanded }
                )
            }
            if(isExpanded){
                DogHobby(
                    hobbyRes = dog.dogHobby,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 4.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
           imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun DogIcon(
    @DrawableRes dogImageRes: Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = dogImageRes),
        contentDescription = null,
        modifier = modifier
            .size(44.dp, 44.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DogInfomation(
    dogName: Int,
    dogAge: Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(8.dp)
    ){
        Text(
            text = stringResource(id = dogName),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(id = R.string.dog_age).format(dogAge),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DogHobby(
    @StringRes hobbyRes: Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(id = hobbyRes),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun WoofPreview(){
    WoofTheme(darkTheme = false){
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            WoofApp(DataSource.loadDogData())
        }
    }
}
@Preview
@Composable
fun WoofDarkPreview(){
    WoofTheme(darkTheme = true){
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            WoofApp(DataSource.loadDogData())
        }
    }
}