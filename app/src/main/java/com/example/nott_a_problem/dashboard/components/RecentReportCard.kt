package com.example.nott_a_problem.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp

@Composable
fun RecentReportCard(report: RecentReports) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = report.problemImageURL),
                contentDescription = "Problem Image",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White.copy(0.1f))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${report.subclassName} at ${report.problemLocation}" + ';' + "${report.roomNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "Status: ${report.status}",
                    fontSize = 12.sp,
                )
            }
        }
    }
}

data class RecentReports(
    val problemImageURL: String,
    val problemLocation: String,
    val status: String,
    val roomNumber: String?,
    val className: String,
    val subclassName: String,
    val timestamp: Timestamp,
    val uid: String,
){
    constructor() : this("", "", "", "", "","", Timestamp.now(), "")
}


