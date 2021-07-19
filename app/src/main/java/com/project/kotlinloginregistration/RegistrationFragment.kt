package com.project.kotlinloginregistration

import android.os.Bundle
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
        val fname = binding.txtRegFname.text.trim();
        val lname = binding.txtRegLname.text.trim();
        val email = binding.txtRegEmail.text.trim();
        val password = binding.txtRegPassword.text.trim();

        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this.requireContext(), "Fill in all the details", Toast.LENGTH_SHORT).show()
        } else {
            val name = "$fname $lname";

            val user = User(name as String, email as String);
            reference.push().setValue(user as User)
                .addOnCompleteListener { task->
                    if (task.isSuccessful) {
                        register(email, password as String)
                    } else {
                        Toast.makeText(this.requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val action = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment();
                    NavHostFragment.findNavController(requireParentFragment()).navigate(action)
                } else {
                    Toast.makeText(this.requireContext(), "Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
    }


}