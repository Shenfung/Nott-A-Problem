package com.example.nott_a_problem.pages.services

import com.google.firebase.Timestamp

// Define a data class to hold the problem data
data class ProblemData(
    val problemClass: String,
    val pIndoorLocation: String,
    val date: Timestamp,
    val problemDepartment: String,
    val problemDescription: String,
    val problemId: String,
    val problemImageURL: String,
    val problemLocation: String,
    val problemPriority: String,
    val problemReportNum: Int,
    val problemStatus: String,
    val problemSubClass: String,
    val problemTitle: String,
    val uid: String,
    val latitude: Double,
    val longitude: Double
) {
    // Convert the problemData object to a Map for Firestore
    fun toJson(): Map<String, Any> {
        return mapOf(
            "problemClass" to problemClass,
            "pIndoorLocation" to pIndoorLocation,
            "date" to date,
            "problemDepartment" to problemDepartment,
            "problemDescription" to problemDescription,
            "problemId" to problemId,
            "problemImageURL" to problemImageURL,
            "problemLocation" to problemLocation,
            "problemPriority" to problemPriority,
            "problemReportNum" to problemReportNum,
            "problemStatus" to problemStatus,
            "problemSubClass" to problemSubClass,
            "problemTitle" to problemTitle,
            "uid" to uid,
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}
