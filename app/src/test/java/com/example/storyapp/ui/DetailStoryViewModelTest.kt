package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.api.ApiService
import com.example.storyapp.ui.activity.detailstory.DetailStoryViewModel
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.MainDispatcherRule
import com.example.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var detailStoryViewModel: DetailStoryViewModel

    private val dummyToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVRMcWpLTXBpTWdJMVlTT24iLCJpYXQiOjE2NzA4NTE0NDl9.-52D5c7mdbvqD0NcHSc5srusYwFaopvq5EDO8L8M37Y"
    private val dummyId = "story-s8QhZ9XycqrYGZV2"

    @Before
    fun setUp(){
        detailStoryViewModel = DetailStoryViewModel(apiService)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when get detail Story Success`() = runTest {
        val dummyDetailResponse = DataDummy.generateDummyDetailResponse()
        val expectedMessage = "Stories fetched successfully"

        `when`(apiService.getStory("Bearer $dummyToken", dummyId)).thenReturn(dummyDetailResponse)

        detailStoryViewModel.getStory(dummyToken, dummyId ){isError, message ->
            assertTrue(!isError)
            assertEquals(message, expectedMessage)
        }

        val detailResponse = detailStoryViewModel.story.getOrAwaitValue()

        verify(apiService).getStory("Bearer $dummyToken", dummyId)
        assertEquals(dummyDetailResponse, detailResponse)
    }
}