package com.example.nott_a_problem.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

fun uploadReportToFirestoreIndoor(
    capturedImageUriString: String,
    area: String,
    roomNumber: String,
    className: String,
    subclassName: String,
    userID: String?,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val problemID = UUID.randomUUID().toString()

    val data = hashMapOf(
        "problemID" to problemID,
        "uid" to userID,
        "problemImageURL" to capturedImageUriString,
        "problemLocation" to area,
        "roomNumber" to roomNumber,
        "className" to className,
        "subclassName" to subclassName,
        "status" to "pending",
        "timestamp" to FieldValue.serverTimestamp()
    )


    firestore.collection("problemsRecord")
        .add(data)
        .addOnSuccessListener {
            println("Data uploaded successfully with problemID: $problemID")
            onSuccess()
        }
        .addOnFailureListener { exception ->
            println("Error uploading data: ${exception.message}")
            onFailure(exception.message ?: "Unknown error")
        }
}

fun uploadReportToFirestoreOutdoor(
    capturedImageUriString: String,
    area: String,
    className: String,
    subclassName: String,
    userID: String?,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val problemID = UUID.randomUUID().toString()

    val data = hashMapOf(
        "problemID" to problemID,
        "uid" to userID,
        "problemImageURL" to capturedImageUriString,
        "problemLocation" to area,
        "className" to className,
        "subclassName" to subclassName,
        "status" to "pending",
        "timestamp" to FieldValue.serverTimestamp()
    )

    firestore.collection("problemsRecord")
        .add(data)
        .addOnSuccessListener {
            println("Data uploaded successfully with problemID: $problemID")
            onSuccess()
        }
        .addOnFailureListener { exception ->
            println("Error uploading data: ${exception.message}")
            onFailure(exception.message ?: "Unknown error")
        }
}

