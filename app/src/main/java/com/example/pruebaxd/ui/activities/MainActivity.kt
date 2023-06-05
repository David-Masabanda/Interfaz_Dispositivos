package com.example.pruebaxd.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pruebaxd.logic.validator.LoginValidator
import com.example.pruebaxd.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()



        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun initClass() {
        binding.ingresar.setOnClickListener {
            //obtenemos la instancia de la clase
            //val loginVal =LoginValidator()
            //acceod al metodo y le vincio los dos parametros que necsito
            val check=LoginValidator().checkLogin(
                binding.ejemploCorreo.text.toString(),
                binding.ejemploKey.text.toString()
            )

            if (check){
                //Intents
                var intent =Intent(this,EmptyActivity::class.java
                )
                intent.putExtra("var1","UCE")
                intent.putExtra("var2",binding.ejemploKey.text.toString())
                startActivity(intent)
            }
            else{
                Snackbar.make(
                    binding.titulo
                    ,"Usuario y contraseña invalidos",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            /*
                        if(binding.txtName.text.toString()=="admin" && binding.txtName.text.toString()=="admin"){

                            //Intents
                            var intent =Intent(this,EmptyActivity::class.java
                            )
                            intent.putExtra("var1","UCE")
                            intent.putExtra("var2",binding.txtName.text.toString())
                            startActivity(intent)

                        }else
                        {
                            Snackbar.make(
                                binding.editTextText
                                ,"Usuario y contraseña invalidos",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
            */
            // binding.txtBuscar.text = "El codigo se ejecuta correctamente"

            //SUMA
            /*
            var f = Snackbar.make(
                binding.boton1,
                "Este es otro mensaje",
                Snackbar.LENGTH_SHORT
            )
                .show()*/





        }
    }


    /*
    setContentView(R.layout.activity_main)
    var boton1 = findViewById<TextView>(R.id.boton1)
    var txt_buscar = findViewById<TextView>(R.id.txt_buscar)
    boton1.text = "INGRESAR"

    boton1.setOnClickListener {
        txt_buscar.text = "El evento se ha ejecutado"
        Toast.makeText(
            this,
            "Este es un ejemplo",
            Toast.LENGTH_SHORT
        )
            .show()

        var f=Snackbar.make(boton1,"Este es otro mensaje",  Snackbar.LENGTH_SHORT).show()



}*/

}

