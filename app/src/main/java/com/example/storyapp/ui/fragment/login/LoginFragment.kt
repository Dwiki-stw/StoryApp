package com.example.storyapp.ui.fragment.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.response.LoginResult
import com.example.storyapp.response.ResponseLogin
import com.example.storyapp.ui.activity.listStory.ListStoryActivity
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        binding.buttonLogin.setOnClickListener{
            loginViewModel.loginUser(binding.loginEmail.text.toString(), binding.loginPassword.text.toString())
        }

        loginViewModel.message.observe(viewLifecycleOwner){
            showResult(it.toString())
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner){
            login(it)
        }

        loginViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun playAnimation(){
        val editEmail = ObjectAnimator.ofFloat(binding.loginEmail, View.ALPHA, 1f).setDuration(2000)
        val editPassword = ObjectAnimator.ofFloat(binding.loginPassword, View.ALPHA, 1f).setDuration(2000)
        val buttonLogin = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(2000)

        AnimatorSet().apply {
            playTogether(editEmail, editPassword, buttonLogin)
            start()
        }
    }

    private fun login(responseLogin: ResponseLogin){

        if (responseLogin.message == "success"){

            saveLoginState(responseLogin.loginResult)

            val intent = Intent(this@LoginFragment.requireContext(), ListStoryActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun saveLoginState(save: LoginResult){
        val pref = requireContext().dataStore
        val savePreference = UserPreference.getInstance(pref)


        lifecycleScope.launch{
            savePreference.saveData(true, save.name, save.token)
        }
    }

    private fun showResult(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) VISIBLE else  GONE
    }

}