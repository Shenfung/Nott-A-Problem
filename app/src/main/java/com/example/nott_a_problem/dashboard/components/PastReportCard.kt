package com.example.nott_a_problem.dashboard.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp

@Composable
fun PastReportCard(report: PastReports) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${report.subclassName} at ${report.problemLocation}" + ';',
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = report.roomNumber,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Status: ${report.status}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${report.timestamp.toDate()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }
    }
}

data class PastReports(
    val problemLocation: String,
    val status: String,
    val roomNumber: String,
    val className: String,
    val subclassName: String,
    val timestamp: Timestamp,
    val uid: String,
){
    constructor() : this("", "", "", "", "", Timestamp.now(), "")
}

