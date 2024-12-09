package com.example.nott_a_problem.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.*
import org.junit.Before
import org.junit.Test

class FirestoreRepositoryTest {

    private lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        firestore = mockk()
    }

    @Test
    fun `upload to Firestore indoor success`() {

        val capturedImageUriString = "testImageUri"
        val area = "Area1"
        val roomNumber = "101"
        val className = "Physics"
        val subclassName = "Mechanics"
        val userID = "testUserId"
        val problemID = "generatedProblemId"

        val task = mockk<Task<DocumentReference>>()
        every { task.addOnSuccessListener(any()) } just awaits
        every { task.addOnFailureListener(any()) } just awaits
        every {
            firestore.collection("problemsRecord").add(any())
        } returns task

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

        firestore.collection("problemsRecord").add(data)
            .addOnSuccessListener {
                println("Data uploaded successfully with problemID: $problemID")
            }
            .addOnFailureListener { exception ->
                println("Error uploading data: ${exception.message}")
            }

        verify {
            firestore.collection("problemsRecord").add(match {
                it["uid"].toString() == userID && it["problemImageURL"].toString() == capturedImageUriString
            }).addOnSuccessListener(any())
        }
    }

    @Test
    fun `upload to Firestore indoor failure`() {

        val capturedImageUriString = "testImageUri"
        val area = "Area1"
        val roomNumber = "101"
        val className = "Physics"
        val subclassName = "Mechanics"
        val userID = "testUserId"
        val errorMessage = "Test error message"

        val task = mockk<Task<DocumentReference>>()
        every { task.addOnSuccessListener(any()) } just awaits
        every { task.addOnFailureListener(any()) } just awaits
        every {
            firestore.collection("problemsRecord").add(any())
        } returns task

        val data = hashMapOf(
            "problemID" to "generatedProblemId",
            "uid" to userID,
            "problemImageURL" to capturedImageUriString,
            "problemLocation" to area,
            "roomNumber" to roomNumber,
            "className" to className,
            "subclassName" to subclassName,
            "status" to "pending",
            "timestamp" to FieldValue.serverTimestamp()
        )


        firestore.collection("problemsRecord").add(data)
            .addOnSuccessListener {}
            .addOnFailureListener { exception ->
                println("Error uploading data: ${exception.message}")
            }

        verify {
            firestore.collection("problemsRecord").add(match {
                it["uid"].toString() == userID && it["problemImageURL"].toString() == capturedImageUriString
            }).addOnFailureListener(match {
                it.toString() == errorMessage
            })
        }
    }

    @Test
    fun `upload to Firestore outdoor success`() {

        val capturedImageUriString = "testImageUri"
        val area = "Area1"
        val className = "Physics"
        val subclassName = "Mechanics"
        val userID = "testUserId"
        val problemID = "generatedProblemId"

        val task = mockk<Task<DocumentReference>>()
        every { task.addOnSuccessListener(any()) } just awaits
        every { task.addOnFailureListener(any()) } just awaits
        every {
            firestore.collection("problemsRecord").add(any())
        } returns task

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

        firestore.collection("problemsRecord").add(data)
            .addOnSuccessListener {
                println("Data uploaded successfully with problemID: $problemID")
            }
            .addOnFailureListener { exception ->
                println("Error uploading data: ${exception.message}")
            }

        verify {
            firestore.collection("problemsRecord").add(match {
                it["uid"].toString() == userID && it["problemImageURL"].toString() == capturedImageUriString
            }).addOnSuccessListener(any())
        }
    }

    @Test
    fun `upload to Firestore outdoor failure`() {

        val capturedImageUriString = "testImageUri"
        val area = "Area1"
        val className = "Physics"
        val subclassName = "Mechanics"
        val userID = "testUserId"
        val errorMessage = "Test error message"

        val task = mockk<Task<DocumentReference>>()
        every { task.addOnSuccessListener(any()) } just awaits
        every { task.addOnFailureListener(any()) } just awaits
        every {
            firestore.collection("problemsRecord").add(any())
        } returns task

        val data = hashMapOf(
            "problemID" to "generatedProblemId",
            "uid" to userID,
            "problemImageURL" to capturedImageUriString,
            "problemLocation" to area,
            "className" to className,
            "subclassName" to subclassName,
            "status" to "pending",
            "timestamp" to FieldValue.serverTimestamp()
        )


        firestore.collection("problemsRecord").add(data)
            .addOnSuccessListener {}
            .addOnFailureListener { exception ->
                println("Error uploading data: ${exception.message}")
            }

        verify {
            firestore.collection("problemsRecord").add(match {
                it["uid"].toString() == userID && it["problemImageURL"].toString() == capturedImageUriString
            }).addOnFailureListener(match {
                it.toString() == errorMessage
            })
        }
    }
}
