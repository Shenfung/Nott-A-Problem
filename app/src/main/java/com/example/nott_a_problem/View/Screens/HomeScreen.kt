package com.example.nott_a_problem.View.Screens

import BottomNavigationBar
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nott_a_problem.R
import com.example.nott_a_problem.View.Components.ProfileButton
import com.example.nott_a_problem.View.Components.DrawerContent
import com.example.nott_a_problem.View.Components.TermsOfServiceDialog
import com.example.nott_a_problem.View.ReportViews.PastReportSectionCard
import com.example.nott_a_problem.View.ReportViews.PastReports
import com.example.nott_a_problem.View.ReportViews.RecentReportCard
import com.example.nott_a_problem.View.ReportViews.RecentReports
import com.example.nott_a_problem.View.Components.ApplicationBackground
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val userName = remember { mutableStateOf("Loading...") }
    val userPastReports = remember { mutableStateOf<List<PastReports>>(emptyList()) }
    val recentReports = remember { mutableStateOf<List<RecentReports>>(emptyList()) }
    val userEmail = remember { mutableStateOf("") }
    val userPoints = remember { mutableIntStateOf(0) }
    val profilePictureUrl = remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var showTermsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val screenWidth = LocalDensity.current.run {LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )
    val isLoading = remember { mutableStateOf(true) }

    fun fetchUserData(userId: String) {
        db.collection("users").document(userId).addSnapshotListener { document, exception ->
            if (exception != null) {
                Log.e("HomeScreen", "Error fetching user data: ${exception.message}")
                return@addSnapshotListener
            }
            if (document != null && document.exists()) {
                val email = document.getString("email") ?: ""
                userName.value = if (email.endsWith("@nottingham.edu.my")) {
                    email.substringBefore("@")
                } else {
                    "Unknown"
                }
                userEmail.value = email
                userPoints.value = document.getLong("points")?.toInt() ?: 0
                profilePictureUrl.value = document.getString("profilePictureUrl") ?: ""
            }
        }
    }

    // Function to fetch user past reports
    fun fetchUserPastReports(userId: String) {
        db.collection("problemsRecord").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { documents ->
                userPastReports.value = documents.mapNotNull { doc -> doc.toObject(PastReports::class.java) }
                isLoading.value = false
            }
            .addOnFailureListener {
                Log.e("HomeScreen", "Failed to fetch reports")
                isLoading.value = false
            }
    }

    // Function to fetch recent reports
    fun fetchRecentReports() {
        db.collection("problemsRecord").get()
            .addOnSuccessListener { documents ->
                recentReports.value = documents.mapNotNull { doc -> doc.toObject(RecentReports::class.java) }
                isLoading.value = false
            }
            .addOnFailureListener {
                Log.e("HomeScreen", "Failed to fetch reports")
                isLoading.value = false
            }
    }


    LaunchedEffect(userId) {
        userId?.let {
            fetchUserData(it)
            fetchUserPastReports(it)
            fetchRecentReports()
        }
    }
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(
                        profilePictureUrl = profilePictureUrl.value,
                        username = userName.value,
                        email = userEmail.value,
                        onChangePassword = { navController.navigate("change_password") },
                        onTermsOfService = { showTermsDialog = true },
                        onContactUs = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:sam.chinsf@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Inquiry regarding Nott-A-Problem App")
                            }
                            context.startActivity(intent)
                        },
                        onHelp = {
                            val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:0115 951 3557")
                            }
                            context.startActivity(phoneIntent)
                        },
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login") {
                                // Prevent back press after logging out
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                    if (showTermsDialog) {
                        TermsOfServiceDialog(onDismiss = { showTermsDialog = false })
                    }
                }
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }
                    ) { contentPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding)
                        ) {
                            ApplicationBackground()
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Nott-A-Problem",
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (screenWidth.value / 8).sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                ProfileButton(
                                    username = userName.value,
                                    points = userPoints.value,
                                    profilePictureUrl = profilePictureUrl.value
                                ) {
                                    coroutineScope.launch { drawerState.open() }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                PastReportSectionCard(reports = userPastReports.value)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Recent Problems:",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                LazyColumn {
                                    items(recentReports.value.take(5)) { recentReport ->
                                        RecentReportCard(recentReport)
                                    }
                                }
                            }
                }
            }
        }
    }
}












