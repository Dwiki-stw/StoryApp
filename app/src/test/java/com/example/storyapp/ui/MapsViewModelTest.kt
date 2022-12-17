package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.api.ApiService
import com.example.storyapp.ui.activity.maps.MapsViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService
    private val dummyToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVRMcWpLTXBpTWdJMVlTT24iLCJpYXQiOjE2Njk3MDU3MDh9.P_mlxpa98gJqD9AoytDNRIbVUYBoRGu33SXYwJFxHec"


    private lateinit var mapsViewModel: MapsViewModel

    @Before
    fun setUp(){
        mapsViewModel = MapsViewModel(apiService)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when get List Story Success`() = runTest {
        val dummyLocationResponse = DataDummy.generateDummyLocationResponse()
        val expectedMessage = "success"

        `when`(apiService.getLocation("Bearer $dummyToken", 1)).thenReturn(dummyLocationResponse)

        mapsViewModel.getLocationStory(dummyToken, 1){isError, message ->
            assertTrue(!isError)
            assertEquals(expectedMessage, message)
        }
        val locationResponse = mapsViewModel.storyWithLocation.getOrAwaitValue()

        verify(apiService).getLocation("Bearer $dummyToken", 1)
        assertEquals(dummyLocationResponse, locationResponse)

    }
}