package com.example.nott_a_problem.View.Screens

import android.net.Uri
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.nott_a_problem.View.Components.RoomNumberDialog

@Composable
fun IndoorCheckScreen(
    navController: NavController,
    capturedImageUriString: String?,
    area: String?,
    className: String?,
    subclassName: String?
) {
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
            navController.navigate("error_identification_outdoor/${capturedImageUriString}/${area}/${className}/${subclassName}")
        }
        return
    }else{
        var isDialogOpen by remember { mutableStateOf(false) }
        var roomNumber by remember { mutableStateOf("") }

        RoomNumberDialog(
            onCancel = { isDialogOpen = false },
            onConfirm = { enteredRoomNumber ->
                roomNumber = enteredRoomNumber

                navController.navigate(
                    "error_identification_indoor/" +
                            "${Uri.encode(capturedImageUriString)}/" +
                            "${Uri.encode(area)}/" +
                            "${Uri.encode(roomNumber)}/" +
                            "${Uri.encode(className)}/" +
                            "${Uri.encode(subclassName)}"
                )
            }
        )
    }
}





