package com.example.kspeu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        loginEditText = view.findViewById(R.id.login_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        loginButton = view.findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            signIn()
        }
        return view
    }

    private fun signIn() {
        val email = loginEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Проверка на пустоту
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Email или пароль не могут быть пустыми.", Toast.LENGTH_SHORT).show()
            return
        }

        // Вывод значений в лог
        Log.d("LoginFragment", "Email: $email, Password: $password")

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    // Переход на следующий экран
                    findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
                    Toast.makeText(context, "Authentication success.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    // Вывод ошибки в лог
                    Log.e("LoginFragment", "Authentication failed", task.exception)
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}