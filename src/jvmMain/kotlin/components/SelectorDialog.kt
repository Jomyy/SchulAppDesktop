package com.jomy.schulapp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState


@Composable
fun SelectorDialog(
    onDismiss: () -> Unit,
    visible: MutableState<Boolean>,
    onPositiveClick: (newKlasse: String) -> Unit,
    klassen: List<String>,
    oldSelection: String
) {

    val state = rememberDialogState(size = DpSize(400.dp,500.dp))
    Dialog(
        onDismiss, visible = visible.value, resizable = false, title = "Klasse Auswählen",
        focusable = false, state = state
    ) {

        Scaffold(modifier = Modifier
            .fillMaxHeight(),
            contentColor = MaterialTheme.colors.background,
            content = {
                Surface(
                    color = MaterialTheme.colors.background,

                    modifier = Modifier.padding(it)
                ) {

                    Column {

                        // Color Selection

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            if (klassen.isEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(15.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text("Solch Eine Leere")
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,

                                    ) {
                                    items(klassen) { klassetext ->
                                        OutlinedButton(onClick = {

                                            onPositiveClick(klassetext)
                                        }, modifier = Modifier.fillMaxWidth(.8f), shape = RoundedCornerShape(50.dp)) {
                                            Text(klassetext)
                                        }
                                    }

                                }

                            }


                        }


                    }
                }
            },
            bottomBar = {
                Divider(modifier = Modifier.border(ButtonDefaults.outlinedBorder))
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = { onPositiveClick(oldSelection) }, shape = RoundedCornerShape(50.dp)) {
                        Text(
                            "Abrruch",
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                            )
                    }
                }

            })

    }
}
