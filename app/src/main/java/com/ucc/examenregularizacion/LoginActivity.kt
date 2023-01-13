package com.ucc.examenregularizacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private val host = "10.200.21.118:8000"
    private val urlInicioSesion = "http://$host/usuarios/valida.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bLogin : Button = findViewById(R.id.bLogin)

        bLogin.setOnClickListener {
            iniciarSesion()
        }
    }

    private fun iniciarSesion() {
        val etNombre : EditText = findViewById(R.id.etNombre)
        val etContrasena : EditText = findViewById(R.id.etContrasena)

        val usuario = mutableMapOf<String, Any?>()
        usuario["nombre"] = etNombre.text.toString()
        usuario["contrasena"] = etContrasena.text.toString()
        val usuarioJson = JSONObject(usuario)

        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.POST,
            urlInicioSesion,
            usuarioJson,
            { response ->
                if(response.getBoolean("exito") && response.getInt("respuesta") != 0){
                    val intent = Intent(this, BoletosActivity::class.java)
                    intent.putExtra("usuarioId", response.getInt("respuesta"))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this, "Error: "+it.message, Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error de conexión: " + it.message)
            }
        )

        queue.add(request)
    }
}