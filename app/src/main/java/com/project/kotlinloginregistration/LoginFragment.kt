package com.project.kotlinloginregistration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.kotlinloginregistration.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false));
        val view = binding.root;

        binding.btn.setOnClickListener {
            login()
        }

        return view;
    }

    private fun login() {
        Toast.makeText(this@LoginFragment.requireContext(), "Yoooo", Toast.LENGTH_SHORT).show()
    }
}