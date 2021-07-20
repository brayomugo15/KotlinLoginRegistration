package com.project.kotlinloginregistration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.kotlinloginregistration.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding;
    private lateinit var mAuth: FirebaseAuth;
    private lateinit var reference: DatabaseReference;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentRegistrationBinding.bind(inflater.inflate(R.layout.fragment_registration, container, false));
        val view = binding.root;

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().reference.child("Users");

        binding.btnReg.setOnClickListener {
            register()
        }
        return view;
    }

    private fun register() {
        val fname = binding.txtRegFname.text.toString().trim();
        val lname = binding.txtRegLname.text.toString().trim();
        val email = binding.txtRegEmail.text.toString().trim();
        val password = binding.txtRegPassword.text.toString().trim();

        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this.requireContext(), "Fill in all the details", Toast.LENGTH_SHORT).show()
        } else {
            val name = "$fname $lname";

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful) {
                        register(name, email)
                    } else {
                        Toast.makeText(this.requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun register(name: String, email: String) {

        val user = User(name, email);

        reference.push().setValue(user)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val action = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment();
                    NavHostFragment.findNavController(requireParentFragment()).navigate(action)
                } else {
                    Toast.makeText(this.requireContext(), "Error " + task.exception, Toast.LENGTH_SHORT).show()
                    Log.e("Registration", "Exception: " + task.exception)
                }
            }
    }


}