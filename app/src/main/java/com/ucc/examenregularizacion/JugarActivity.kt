package com.ucc.examenregularizacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class JugarActivity : AppCompatActivity() {
    private val host = "10.200.21.118:8000"
    private val urlJugar = "http://$host/boletos/jugar.php"
    private var usuarioId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugar)

        usuarioId = intent.getIntExtra("usuarioId", 0)

        if(usuarioId == 0){
            Toast.makeText(
                applicationContext, "Error con la sesión", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val bJugar : Button = findViewById(R.id.bJugar)

        bJugar.setOnClickListener {
            jugar()
        }
    }

    private fun jugar() {
        val queue : RequestQueue = Volley.newRequestQueue(this)

        val usuario = mutableMapOf<String, Any?>()
        usuario["usuario_id"] = usuarioId
        val usuarioJson = JSONObject(usuario)

        val request = JsonObjectRequest(
            Request.Method.POST,
            urlJugar,
            usuarioJson,
            { response->
                if(response.getBoolean("exito")){
                    val tvGanador : TextView = findViewById(R.id.tvGanador)
                    tvGanador.text = response.getString("respuesta")
                }
            },
            {
                Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
                Log.e("NotesActivity", "Error de conexión: " + it.message)
            }
        )

        queue.add(request)
    }
}