package com.example.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.ui.fragment.register.RegisterViewModel
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
class RegisterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp(){
        registerViewModel = RegisterViewModel()
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when register success Should message User created`() = runTest {
        val expectedMessage = "User created"
        registerViewModel.registerUser("test", "testing@email12.com", "123456")

        val actual = registerViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

    @Test
    fun `when register with invalid email Should message email must be a valid email`() = runTest {
        val expectedMessage = "\"email\" must be a valid email"
        registerViewModel.registerUser("test", "testing@email", "123456")

        val actual = registerViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

    @Test
    fun `when register with invalid password Should message Password must be at least 6 characters long`() = runTest {
        val expectedMessage = "Password must be at least 6 characters long"
        registerViewModel.registerUser("test", "testing@email.com", "12345")

        val actual = registerViewModel.message.getOrAwaitValue(1, TimeUnit.MINUTES)

        assertNotNull(actual)
        assertEquals(expectedMessage, actual)
    }

}