package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.api.ApiService
import com.example.storyapp.ui.fragment.login.LoginViewModel
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
class LoginViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockApiService: ApiService


    private lateinit var loginViewModel : LoginViewModel

    @Before
    fun setUp(){
        loginViewModel = LoginViewModel(mockApiService)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `When login success`() = runTest {
        val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
        val expectedMessage = "success"

        `when`(mockApiService.login("setw@gmail.com", "123456")).thenReturn(dummyLoginResponse)

        loginViewModel.loginUser("setw@gmail.com", "123456"){message  ->
            assertEquals(expectedMessage, message)
        }

        val loginResponse = loginViewModel.loginResult.getOrAwaitValue()

        verify(mockApiService).login("setw@gmail.com", "123456")
        assertEquals(dummyLoginResponse, loginResponse)
    }
}