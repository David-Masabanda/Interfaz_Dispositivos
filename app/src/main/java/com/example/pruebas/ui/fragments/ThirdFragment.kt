package com.example.pruebas.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.pruebas.R
import com.example.pruebas.databinding.FragmentThirdBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(
            layoutInflater, container, false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .asGif()
            .load(R.drawable.esperando)
            .into(binding.gifImageView);

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        // Cargar datos del usuario
        loadUserData()

        binding.btnCambiarImg.setOnClickListener {
            val options = arrayOf("Tomar foto", "Seleccionar de la galería")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Elegir una opción")
            builder.setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        // Tomar foto
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraResult.launch(intent)
                    }
                    1 -> {
                        // Seleccionar de la galería
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
            }

            val dialog = builder.create()
            dialog.show()
        }

    }


    private fun loadUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userDocRef = firestore.collection("users").document(user.uid)
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.data
                    binding.userName.text = userData?.get("name") as? String
                    binding.userCorreo.text = userData?.get("email") as? String
                    binding.userDireccion.text = userData?.get("address") as? String
                    binding.userTelefono.text = userData?.get("phone") as? String

                    // Obtén la URL de la imagen de la base de datos
                    val imageUrl = userData?.get("image_url") as? String
                    Log.d("HOLA", imageUrl ?: "imageUrl es nulo")
                    if (imageUrl != null) {
                        // Carga y muestra la imagen desde la URL
                        val imageUri = Uri.parse(imageUrl)
                        Picasso.get().load(imageUri).into(binding.userImagen)
                    } else {
                        Log.d("UCE","Llega a esta parte")
                        // No hay URL de imagen en la base de datos, muestra la imagen por defecto
                        binding.userImagen.setImageResource(R.drawable.avatar) // Imagen por defecto

                    }
                }
            }
        }
    }


    private val cameraResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        when(result.resultCode){
            Activity.RESULT_OK->{
                val image =  result.data?.extras?.get("data") as Bitmap
                uploadImageToStorageAndFirestore(image)
                binding.userImagen.setImageBitmap(image)
            }
            Activity.RESULT_CANCELED->{}
        }
    }


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            val imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
            uploadImageToStorageAndFirestore(imageBitmap)
            binding.userImagen.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadImageToStorageAndFirestore(image: Bitmap) {
        val user = auth.currentUser
        if (user != null) {
            val imageRef = storage.reference.child("profile_images/${user.uid}.jpg")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()

            val uploadTask = imageRef.putBytes(imageData)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        // Actualiza la URL de la imagen en Firestore
                        firestore.collection("users").document(user.uid)
                            .update("image_url", downloadUri.toString())
                            .addOnSuccessListener {
                                Log.d("UCE", "URL de imagen actualizada en Firestore")
                            }
                            .addOnFailureListener { e ->
                                Log.w("UCE", "Error al actualizar la URL de imagen en Firestore", e)
                            }
                    }
                } else {
                    Log.w("UCE", "Error al subir la imagen a Firebase Storage", task.exception)
                }
            }
        }
    }





}