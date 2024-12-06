package com.example.nott_a_problem.pages.services.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RoomNumberDialog(
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Enter Room Number", style = MaterialTheme.typography.h6)
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Text("Room Number") }
                )
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { onConfirm(textState.text) }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}