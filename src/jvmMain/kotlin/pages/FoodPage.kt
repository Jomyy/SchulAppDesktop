package pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import api.APIService
import components.FoodCard
import dataclass.FoodData
import jdk.jshell.spi.ExecutionEnv
import java.awt.Desktop
import java.awt.desktop.PreferencesHandler
import java.net.URI

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodPage(){
        var foodList = remember{ mutableStateListOf<List<String>>()}

        LaunchedEffect(Unit) {
            foodList.addAll(APIService.getInstance().getFood())
        }
        LazyVerticalGrid(GridCells.Adaptive(220.dp)){
            items(foodList.size){
                FoodCard(FoodData(foodList[it][0],foodList[it][1],foodList[it][2],foodList[it][3]))
            }
        }
        System.getProperties()



}
