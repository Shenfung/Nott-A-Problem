package com.example.nott_a_problem.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TermsOfServiceDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = "Terms of Service", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Text(
                            text = """
                            Welcome to Nott-A-Problem. These terms and conditions govern your use of the App. By downloading, installing, or using the App, you agree to be bound by these terms. If you do not agree with any part of these terms, please refrain from using the App.

                            1. Description of Services:
                            - The App provides a platform for the University of Nottingham Malaysia community to report faulty facilities by taking a picture and submitting it.
                            - The App utilizes artificial intelligence to detect the issue and its location automatically.

                            2. User Responsibilities:
                            - Users are responsible for the accuracy and validity of the information provided through the App.
                            - Users must ensure that they have permission to report issues in the locations depicted in the pictures they submit.
                            - Users must comply with all applicable laws and University regulations when using the App.

                            3. Permission Requirements:
                            - By using the App, you consent to allow access to your device's camera and location services to enable the reporting functionality.

                            4. Data Privacy:
                            - The App may collect and process personal data as described in the University of Nottingham Malaysia's privacy policy.
                            - Users' personal data will be handled in accordance with applicable data protection laws and University policies.

                            5. Intellectual Property:
                            - All intellectual property rights related to the App, including its design, content, and functionality, belong to the University of Nottingham Malaysia.
                            - Users may not reproduce, modify, or distribute any part of the App without prior written consent.

                            6. Limitation of Liability:
                            - The University of Nottingham Malaysia shall not be liable for any direct, indirect, incidental, special, or consequential damages arising out of or in any way connected with the use of the App.
                            - The University does not guarantee the accuracy, reliability, or completeness of any information provided through the App.

                            7. Termination:
                            - The University reserves the right to terminate or suspend a user's access to the App at any time without prior notice if they violate these terms or misuse the App.

                            8. Governing Law and Jurisdiction:
                            - These terms and conditions shall be governed by and construed in accordance with the laws of Malaysia.
                            - Any disputes arising from these terms shall be subject to the exclusive jurisdiction of the courts of Malaysia.

                            9. Changes to Terms and Conditions:
                            - The University reserves the right to modify or update these terms and conditions at any time. Users will be notified of any changes, and continued use of the App constitutes acceptance of the revised terms.

                            10. Contact Information:
                            - If you have any questions or concerns regarding these terms and conditions, please contact us by clicking the help option in the profile bar.
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Button(onClick = onDismiss) {
                        Text(text = "Close")
                    }
                }
            }
        }
    }
}

