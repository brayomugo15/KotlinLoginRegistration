package com.project.kotlinloginregistration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.project.kotlinloginregistration.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding;
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false));
        val view = binding.root;
        mAuth = FirebaseAuth.getInstance();

        binding.btn.setOnClickListener {
            login()
        }

        return view;
    }

    private fun login() {
        val email = binding.txtLoginEmail.text.trim();
        val password = binding.txtLoginPassword.text.trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this.context, "Empty fields", Toast.LENGTH_SHORT).show();
        } else {



        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
                    NavHostFragment.findNavController(requireParentFragment()).navigate(action);
                } else {
                    Toast.makeText(this.context, "Error logging in", Toast.LENGTH_SHORT).show()
                }
            }
    }


}