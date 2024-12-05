package com.example.nott_a_problem.pages.dashboard.components.drawer_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nott_a_problem.R

@Composable
fun DrawerContent(
    profilePictureUrl: String,
    username: String,
    email: String,
    onChangeProfilePicture: () -> Unit,
    onChangePassword: () -> Unit,
    onTermsOfService: () -> Unit,
    onContactUs: () -> Unit,
    onHelp: () -> Unit,
    onLogout: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f),
        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFC1F0FF), Color(0xFF6379FF)) // Light blue gradient
                        )
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                        Image(
                            painter = painterResource(id = R.drawable.account_circle),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(50.dp)
                                .fillMaxWidth(0.2f),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Account Settings Section
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Account Settings",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChangeProfilePicture() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pen), // Replace with your pen icon resource
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Change Profile Picture",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 22.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChangePassword() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pen), // Replace with your pen icon resource
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Change Password",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "More",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTermsOfService() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.file_document), // Replace with your pen icon resource
                        contentDescription = "Terms of Service",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Terms Of Service",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onContactUs() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.email), // Replace with your pen icon resource
                        contentDescription = "Contact Us",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Contact Us",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onHelp() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.help), // Replace with your pen icon resource
                        contentDescription = "Help",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Help\n(Security Office)",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLogout() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout), // Replace with your pen icon resource
                        contentDescription = "Logout",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Red,
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
