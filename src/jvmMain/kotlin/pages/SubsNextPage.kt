package pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import api.APIService
import com.jomy.schulapp.components.SelectorDialog
import components.SubCard
import dataclass.SubData
import java.awt.Desktop
import java.net.URI
import java.util.prefs.Preferences

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubsNextPage(){

    var allSubs = remember{mutableStateListOf<List<String>>()}
    var selectedSubs = remember{mutableStateListOf<List<String>>()}
    var selectedKlasse = remember{mutableStateOf(Preferences.userRoot().get("selected_class",""))}
    var showDialog = mutableStateOf(false)
    var klassen = remember{ mutableStateListOf<String>()}
    LaunchedEffect(Unit){
        allSubs.clear()
        allSubs.addAll(APIService.getInstance().getSubsNext())
        var stringvorher = ""
        allSubs.forEach {
            if(stringvorher != it[0]){
                klassen.add(it[0])
            }
            if(it[0] == selectedKlasse.value){
                selectedSubs.add(it)
            }
            stringvorher = it[0]
        }

    }




    SelectorDialog( klassen = klassen, onPositiveClick = {
        Preferences.userRoot().put("selected_class",it)
        selectedKlasse.value = it
        selectedSubs.clear()
        allSubs.forEach {
            if(it[0] == selectedKlasse.value){
                selectedSubs.add(it)
            }
        }
        showDialog.value = false
    }, oldSelection = selectedKlasse.value, onDismiss = {
        showDialog.value = false
    }, visible = showDialog)



    Scaffold (bottomBar = {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedButton(onClick = {

                showDialog.value = !showDialog.value
            }, content = {
                if(selectedKlasse.value == ""){
                    Text("Klasse Auswählen")
                }else{
                    Text("Ausgewählte Klasse: ${selectedKlasse.value}")
                }

            }, shape = RoundedCornerShape(50.dp), modifier = Modifier.fillMaxWidth(.9f))
        }

    }, backgroundColor = Color.Transparent){
        LazyVerticalGrid(GridCells.Adaptive(230.dp)){
            items(selectedSubs.size){
                SubCard(SubData(selectedSubs[it][0],selectedSubs[it][1],selectedSubs[it][2],selectedSubs[it][3],selectedSubs[it][4],selectedSubs[it][5],selectedSubs[it][6]))
            }
        }
    }
}