package com.example.nott_a_problem


import com.example.nott_a_problem.repository.ApiService
import com.example.nott_a_problem.repository.ApiService.api
import com.example.nott_a_problem.repository.ClassificationResponse
import com.example.nott_a_problem.viewModel.ImageClassificationViewModel
import io.mockk.*
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class ImageClassificationViewModelTest {

    @get:Rule

    private lateinit var viewModel: ImageClassificationViewModel
    private lateinit var mockApiService: ApiService

    @Before
    fun setup() {
        mockApiService = mockk(relaxed = true)
        viewModel = spyk(ImageClassificationViewModel(), recordPrivateCalls = true)

        mockkObject(ApiService)
    }

    @Test
    fun `classifyImageFromFile success scenario`() = runTest {
        val mockFile = mockk<File>(relaxed = true)
        val mockMultipartBody = mockk<MultipartBody.Part>(relaxed = true)
        val mockClassResponse = mockk<ClassificationResponse> {
            every { predictedClass } returns "MockClass"
            every { error } returns null
        }
        val mockSubclassResponse = mockk<ClassificationResponse> {
            every { predictedClass } returns "MockSubclass"
            every { error } returns null
        }

        every { viewModel["convertFiletoMultipartBody"](mockFile) } returns mockMultipartBody
        coEvery { api.getClassByFile(image = mockMultipartBody) } returns mockClassResponse
        coEvery { api.getSubclassByFile(image = mockMultipartBody, className = any()) } returns mockSubclassResponse

        var onSuccessCalled = false
        var onErrorCalled = false

        viewModel.classifyImageFromFile(
            imageFile = mockFile,
            onSuccess = { onSuccessCalled = true },
            onError = { onErrorCalled = true }
        )

        assertEquals("MockClass", viewModel.className.value)
        assertEquals("MockSubclass", viewModel.subclassName.value)
        assertEquals(mockFile, viewModel.reportImageFile.value)
        assertEquals(null, viewModel.errorMessage.value)
        assert(onSuccessCalled)
        assert(!onErrorCalled)
    }

    @Test
    fun `classifyImageFromFile error scenario`() = runTest {
        val mockFile = mockk<File>(relaxed = true)
        val mockMultipartBody = mockk<MultipartBody.Part>(relaxed = true)
        val exceptionMessage = "Mock error during classification"

        every { viewModel["convertFiletoMultipartBody"](mockFile) } returns mockMultipartBody
        coEvery { api.getClassByFile(image = mockMultipartBody) } throws Exception(exceptionMessage)

        var onSuccessCalled = false
        var onErrorCalled = false

        viewModel.classifyImageFromFile(
            imageFile = mockFile,
            onSuccess = { onSuccessCalled = true },
            onError = { onErrorCalled = true }
        )

        assertEquals(null, viewModel.className.value)
        assertEquals(null, viewModel.subclassName.value)
        assertEquals(exceptionMessage, viewModel.errorMessage.value)
        assert(!onSuccessCalled)
        assert(onErrorCalled)
    }
}
