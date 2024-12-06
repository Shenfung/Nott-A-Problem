package com.example.nott_a_problem.pages.dashboard

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.nott_a_problem.pages.services.DashboardBackground
import com.example.nott_a_problem.pages.dashboard.components.ProfileButton
import com.example.nott_a_problem.pages.dashboard.components.drawer_components.DrawerContent
import com.example.nott_a_problem.pages.dashboard.components.drawer_components.TermsOfServiceDialog
import com.example.nott_a_problem.pages.dashboard.components.past_report_section_components.PastReportSectionCard
import com.example.nott_a_problem.pages.dashboard.components.past_report_section_components.Report
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val userName = remember { mutableStateOf("Loading...") }
    val reports = remember { mutableStateOf<List<Report>>(emptyList()) }
    val userEmail = remember { mutableStateOf("") }
    val userPoints = remember { mutableStateOf(0) }
    val profilePictureUrl = remember { mutableStateOf("") }

    // Firebase Firestore instance
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var showTermsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }

    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )

    val sampleReports = listOf(
        Report("Monthly Report - May", "2024-05-01", "Details about the May report."),
        Report("Weekly Report - Week 20", "2024-05-15", "Weekly summary for week 20."),
        Report("Daily Report - May 20", "2024-05-20", "Activities completed on May 20."),
        Report("Yearly Report - 2023", "2023-12-31", "Summary of the year 2023."),
        Report("Project Report", "2024-03-10", "Project progress and updates.")
    )


    LaunchedEffect(userId) {
        userId?.let {
            db.collection("users").document(it).addSnapshotListener { document, exception ->
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
    }

    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        userId?.let {
            db.collection("reports").whereEqualTo("userId", it).get()
                .addOnSuccessListener { documents ->
                    reports.value = documents.mapNotNull { doc -> doc.toObject(Report::class.java) }
                    isLoading.value = false
                }
                .addOnFailureListener {
                    Log.e("HomeScreen", "Failed to fetch reports")
                    isLoading.value = false
                }
        }
    }

    // Wrap the entire content in a Box
    Box(modifier = Modifier.fillMaxSize()) {
        // Background and Drawer
        DashboardBackground()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    profilePictureUrl = profilePictureUrl.value,
                    username = userName.value,
                    email = userEmail.value,
                    onChangeProfilePicture= {navController.navigate("change_profile_picture") },
                    onChangePassword= {navController.navigate("change_password") },
                    onTermsOfService= { showTermsDialog = true },
                    onContactUs= {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:sam.chinsf@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry regarding Nott-A-Problem App")
                        }
                        context.startActivity(intent)},
                    onHelp = {
                        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:0115 951 3557")
                        }
                        context.startActivity(phoneIntent)
                    },
                    onLogout = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login")
                    }
                )

                if (showTermsDialog) {
                    TermsOfServiceDialog(onDismiss = { showTermsDialog = false })
                }
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
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
                    ProfileButton(
                        username = userName.value,
                        points = userPoints.value,
                        profilePictureUrl = profilePictureUrl.value
                    ) {
                        coroutineScope.launch { drawerState.open() }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    PastReportSectionCard(reports = sampleReports)

                    Text(
                        text = "Recent Problems:",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.fillMaxWidth() // Ensures the text spans full width
                    )
                }
            }
        }

        BottomNavigationBar(
            navController = navController,
        )
    }
}











