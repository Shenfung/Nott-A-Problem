package com.example.nott_a_problem.pages.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nott_a_problem.R
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ProfileButton(
    username: String,
    points: Int,
    profilePictureUrl: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 6.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (profilePictureUrl.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profilePictureUrl)
                        .crossfade(true) // Add fade-in transition
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(50.dp)
                        .fillMaxWidth(0.2f),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Default profile picture if URL is empty
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.account_circle),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(50.dp)
                        .fillMaxWidth(0.2f),
                    contentScale = ContentScale.Crop
                )
            }

            // Username and Points
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Points",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Star Icon",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .height(16.dp)
                    )
                    Text(
                        text = ": $points",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // More Options Icon
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = Color.Gray
            )
        }
    }
}