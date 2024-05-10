package com.example.flyyn.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flyyn.R
import com.example.flyyn.databinding.FragmentLoginBinding
import com.example.flyyn.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (viewModel.validateCredentials(username, password)) {
                viewModel.saveLoginData(requireContext(), username, password)

                findNavController().navigate(R.id.action_loginFragment_to_notesFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Coba periksa ulang username dan password anda",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

}