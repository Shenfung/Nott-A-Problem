package com.example.nott_a_problem.pages.services.location

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

@Composable
fun IndoorCheckScreen(navController: NavController, area: String) {
    val indoorAreas = listOf(
        "Block A; Trent Building",
        "Block B and B1; Faculty of Science and Engineering",
        "Block C and C1; Faculty of Science and Engineering",
        "Block D; Faculty of Engineering",
        "Block E; Faculty of Arts and Social Sciences",
        "Block F1; Central Teaching 1",
        "Block F2; Information Services - IT Support",
        "Block F3; Central Teaching 2",
        "Block F4; Central Teaching 3",
        "Block G; Library",
        "Block H; Student Association",
        "Block I1; Tioman Hall",
        "Block I2; Langkawi Hall",
        "Block I3; Redang Hall",
        "Block I4; Pangkor Hall",
        "Block I5; Kapas Hall",
        "Block J1; Sipadan Hall",
        "Block J2; Mabul Hall",
        "Block J3; Lankayan Hall",
        "Block J4; Rawa Hall",
        "Block J5; Gemia Hall",
        "Block J6; Perhentian Hall",
        "Block SH 1; Nexus",
        "Block SH 2; Radius",
        "Block K; Sports Complex",
        "Block L1; Warden House",
        "Block L2; Warden House (Tioman Lodge)",
        "Block L3; Warden House (Langkawi Lodge)",
        "Block L4; HR Office (Redang Lodge)",
        "Block VH 1; Visitor House 1 (Pedu)",
        "Block VH 2; Visitor House 2 (Titiwangsa)",
        "Block VH 3; Visitor House 3 (Chini)",
        "Block VH 4; Visitor House 4 (Kenyir)",
        "Block M; Islamic Centre",
        "Block N; Engineering Research Building",
        "Engineering Mixing Lab",
        "Creche; Taska, Childcare Centre"
    )

    val isIndoor = indoorAreas.contains(area)

    if (!isIndoor) {
        LaunchedEffect(Unit) {
            navController.navigate("error_identification/$area")
        }
        return
    }

    var isDialogOpen by remember { mutableStateOf(false) }
    var roomNumber by remember { mutableStateOf("") }
//    val roomNumber = "Room101"
    val locationInfo = "Location Info"
    val firstPredictionResult = "Prediction1,Prediction2"

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { isDialogOpen = true }) {
            Text("Enter Room Number")
        }
    }

    if (isDialogOpen) {
        RoomNumberDialog(
            onCancel = { isDialogOpen = false },
            onConfirm = { enteredRoomNumber ->
                isDialogOpen = false
                roomNumber = enteredRoomNumber

                // Navigate to the Error Identification Screen
                navController.navigate("error_identification/${roomNumber}/${area}/$firstPredictionResult")
            }
        )
    }
}





