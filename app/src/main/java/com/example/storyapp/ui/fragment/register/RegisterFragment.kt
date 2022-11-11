package com.example.storyapp.ui.fragment.register



import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.storyapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        binding.buttonRegister.setOnClickListener {
            registerViewModel.setDataRegister(binding.registerName.text.toString(), binding.registerEmail.text.toString(), binding.registerPassword.text.toString())
        }

        registerViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        registerViewModel.responseRegister.observe(viewLifecycleOwner){
            showResult(it.message)
        }

        registerViewModel.message.observe(viewLifecycleOwner){
            showResult(it)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun playAnimation(){
        val editName = ObjectAnimator.ofFloat(binding.registerName, View.ALPHA, 1f).setDuration(500)
        val editEmail = ObjectAnimator.ofFloat(binding.registerEmail, View.ALPHA, 1f).setDuration(500)
        val editPassword = ObjectAnimator.ofFloat(binding.registerPassword, View.ALPHA, 1f).setDuration(500)
        val buttonRegister = ObjectAnimator.ofFloat(binding.buttonRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(editName, editEmail, editPassword, buttonRegister)
            start()
        }
    }

    private fun showResult(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}