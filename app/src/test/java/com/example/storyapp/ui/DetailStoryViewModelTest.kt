package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.ui.activity.detailstory.DetailStoryViewModel
import com.example.storyapp.utils.MainDispatcherRule
import com.example.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailStoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailStoryViewModel: DetailStoryViewModel

    private val dummyToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVRMcWpLTXBpTWdJMVlTT24iLCJpYXQiOjE2Njk3MDU3MDh9.P_mlxpa98gJqD9AoytDNRIbVUYBoRGu33SXYwJFxHec"
    private val dummyId = "story-8X8zZfxl-7oGsG6k"

    @Before
    fun setUp(){
        detailStoryViewModel = DetailStoryViewModel()
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when get detail Story Success Story should be not Null`() = runTest {

        detailStoryViewModel.getStory( dummyToken, dummyId )

        val story = detailStoryViewModel.story.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(story)
    }
}