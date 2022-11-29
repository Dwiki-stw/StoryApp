package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.ui.fragment.login.LoginViewModel
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
class LoginViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel : LoginViewModel

    @Before
    fun setUp(){
        loginViewModel = LoginViewModel()
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `When login success Should message success`() = runTest {
        val expectedMessage = "success"
        loginViewModel.loginUser("setw@gmail.com", "123456")

        val actual = loginViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

    @Test
    fun `When login with the wrong email Should message User not found`() = runTest {
        val expectedMessage = "User not found"

        loginViewModel.loginUser("setw@wrong.email", "123456")

        val actual = loginViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

    @Test
    fun `When login with the wrong password Should message Invalid password`() = runTest {
        val expectedMessage = "Invalid password"

        loginViewModel.loginUser("setw@gmail.com", "wrongpassword")

        val actual = loginViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

}