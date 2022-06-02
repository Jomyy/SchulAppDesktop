package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dataclass.SubData

@Composable
fun SubCard(data: SubData){
    Card(modifier = Modifier.padding(5.dp).fillMaxWidth().fillMaxHeight().clip(RoundedCornerShape(24.dp)).border(
        BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant), RoundedCornerShape(24.dp)
    )){
        Column(modifier = Modifier.padding(15.dp)) {
            if(data.state == ""){
                Text("Vertreten", style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
            }else{
                Text(data.state, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
            }

            Divider(modifier = Modifier.padding(vertical = 10.dp), Color.Transparent)
            Text(data.fach)
            Divider(modifier = Modifier.padding(vertical = 10.dp), Color.Transparent)
            Text(data.stunde)
            Divider(modifier = Modifier.padding(vertical = 10.dp), Color.Transparent)
            Text(data.lehrer)
            Divider(modifier = Modifier.padding(vertical = 10.dp), Color.Transparent)
            Text(data.raum)

        }

    }
}