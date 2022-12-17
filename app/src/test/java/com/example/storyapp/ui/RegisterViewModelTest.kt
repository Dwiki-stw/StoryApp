package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.api.ApiService
import com.example.storyapp.ui.fragment.register.RegisterViewModel
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.MainDispatcherRule
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
class RegisterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp(){
        registerViewModel = RegisterViewModel(apiService)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when register success`() = runTest {
        val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
        val expectedMessage = "User created"

        `when`(apiService.register("test", "testing@email.com", "123456")).thenReturn(dummyRegisterResponse)

        registerViewModel.registerUser("test", "testing@email.com", "123456"){message ->
            assertEquals(expectedMessage, message)
        }

        verify(apiService).register("test", "testing@email.com", "123456")
    }
}