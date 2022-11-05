package com.example.storyapp.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.ui.activity.listStory.ListStoryActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener{
            val intent = Intent(this@LoginFragment.requireContext(), ListStoryActivity::class.java)
            startActivity(intent)
            activity?.finish()
            //loginViewModel.setDataLogin(binding.loginEmail.text.toString(), binding.loginPassword.text.toString())
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner){
                showResult(it.message.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showResult(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}