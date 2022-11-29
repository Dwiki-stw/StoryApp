package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.ui.activity.maps.MapsViewModel
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
class MapsViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mapsViewModel: MapsViewModel

    @Before
    fun setUp(){
        mapsViewModel = MapsViewModel()
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when get List Story Success Story must be not Nul`() = runTest {

        mapsViewModel.getLocationStory("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVRMcWpLTXBpTWdJMVlTT24iLCJpYXQiOjE2Njk3MDU3MDh9.P_mlxpa98gJqD9AoytDNRIbVUYBoRGu33SXYwJFxHec")

        val listStory = mapsViewModel.storyWithLocation.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(listStory)
    }
}