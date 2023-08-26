package com.example.pruebas.ui.activities

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pruebas.R
import com.example.pruebas.databinding.ActivityBiometricBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.imgFinger.setOnClickListener{
//            autenticateBiometric()
//        }

        hideLayout()

    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asGif()
            .load(R.drawable.actualizar)
            .into(binding.imgCarga);

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()



        loadUserData()

        autenticateBiometric()

        binding.saveButton.setOnClickListener {
            updateUserData()
        }

        binding.btnHome.setOnClickListener {
            var intent = Intent(this, EmptyActivity::class.java)
            intent.putExtra("fragmentToLoad", "third_fragment")
            startActivity(intent)
        }

    }

    private fun checkBiometric() : Boolean{

        val biometricManager = BiometricManager.from(this)
        var returnValid = false

        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS->{
                returnValid=true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                returnValid=false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                returnValid=false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                val intentPrompt = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                intentPrompt.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
                startActivity(intentPrompt)
                returnValid=false
            }
        }
        return returnValid
    }

    private fun autenticateBiometric(){
        if (checkBiometric()){
            val executor = ContextCompat.getMainExecutor(this)

            val biometricPrompt = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion Requerida")
                .setSubtitle("Ingrese su huella digital")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)

                .build()

            val biometricManager = BiometricPrompt(this,executor,
                object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        showLayout()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
            biometricManager.authenticate(biometricPrompt)
        }else{
            Snackbar.make(binding.saveAddress, "No existen los requisitos necesarios", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun loadUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userDocRef = firestore.collection("users").document(user.uid)
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.data
                    binding.saveName.text = Editable.Factory.getInstance().newEditable(userData?.get("name") as? String)
                    binding.saveCorreo.text = Editable.Factory.getInstance().newEditable(userData?.get("email") as? String)
                    binding.saveAddress.text = Editable.Factory.getInstance().newEditable(userData?.get("address") as? String)
                    binding.savePhone.text = Editable.Factory.getInstance().newEditable(userData?.get("phone") as? String)
                    val imageUrl = userData?.get("image_url") as? String
                    Picasso.get().load(imageUrl).into(binding.imgPerfil)
                }
            }
        }
    }

    private fun updateUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userDocRef = firestore.collection("users").document(user.uid)

            val newName = binding.saveName.text.toString()
            val newCorreo = binding.saveCorreo.text.toString()
            val newAddress = binding.saveAddress.text.toString()
            val newPhone = binding.savePhone.text.toString()

            val userDataUpdates = hashMapOf(
                "name" to newName,
                "email" to newCorreo,
                "address" to newAddress,
                "phone" to newPhone
            )

            userDocRef.update(userDataUpdates as Map<String, Any>)
                .addOnSuccessListener {
                    Snackbar.make(binding.root, "Datos actualizados correctamente", Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, "Error al actualizar los datos", Snackbar.LENGTH_SHORT).show()
                }
        }
    }


    private fun hideLayout() {
        binding.saveName.visibility = View.GONE
        binding.saveCorreo.visibility = View.GONE
        binding.saveAddress.visibility = View.GONE
        binding.savePhone.visibility = View.GONE

        binding.btnHome.visibility = View.GONE
        binding.saveButton.visibility = View.GONE
        binding.info.visibility = View.GONE
        binding.imgPerfil.visibility = View.GONE
        binding.imgCarga.visibility = View.GONE
    }

    private fun showLayout() {
        binding.saveName.visibility = View.VISIBLE
        binding.saveCorreo.visibility = View.VISIBLE
        binding.saveAddress.visibility = View.VISIBLE
        binding.savePhone.visibility = View.VISIBLE

        binding.btnHome.visibility = View.VISIBLE
        binding.saveButton.visibility = View.VISIBLE
        binding.info.visibility = View.VISIBLE
        binding.imgPerfil.visibility = View.VISIBLE
        binding.imgCarga.visibility = View.VISIBLE
    }

}