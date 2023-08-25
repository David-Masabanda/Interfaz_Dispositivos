package com.example.pruebas.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityRegistroBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asGif()
            .load(R.drawable.registro)
            .into(binding.imgRegistro);

        binding.btnRegistro.setOnClickListener {
            authWithFirebaseEmail(
                binding.usEmail.text.toString(),
                binding.usPassword.text.toString()
            )
        }

        binding.btnRetorno.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }



    private fun authWithFirebaseEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    // Obtén los valores de los EditText
                    val name = binding.usName.text.toString()
                    val phone = binding.usPhone.text.toString()
                    val address = binding.usDireccion.text.toString()
                    val password=binding.usPassword.text.toString()

                    // Crea un objeto con los datos
                    val userData = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "phone" to phone,
                        "address" to address,
                        "password" to password
                    )

                    // Guarda los datos en Firestore
                    firestore.collection("users")
                        .document(user?.uid ?: "")
                        .set(userData)
                        .addOnSuccessListener {
                            Log.d(Constans.TAG, "Datos guardados en Firestore")
                            Toast.makeText(
                                baseContext,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Log.w(Constans.TAG, "Error al guardar datos en Firestore", e)
                            Toast.makeText(
                                baseContext,
                                "Error en el registro.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Log.w(Constans.TAG, "Fallo en la creacion de usuarios", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}