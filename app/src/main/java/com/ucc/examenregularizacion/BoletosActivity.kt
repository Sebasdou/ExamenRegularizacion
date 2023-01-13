package com.ucc.examenregularizacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ucc.examenregularizacion.adapter.BoletoAdapter
import com.ucc.examenregularizacion.entity.Boleto
import org.json.JSONArray
import org.json.JSONObject

class BoletosActivity : AppCompatActivity() {
    private val host = "10.200.21.118:8000"
    private val urlExistencia = "http://$host/boletos/existencia.php"
    private val urlListar = "http://$host/boletos/listar.php"
    private var usuarioId = 0
    private lateinit var adapter : BoletoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boletos)

        usuarioId = intent.getIntExtra("usuarioId", 0)

        if(usuarioId == 0){
            Toast.makeText(
                applicationContext, "Error con la sesión", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val fbAgregarTicket : FloatingActionButton = findViewById(R.id.fbAgregarTicket)
        val fbJugar : FloatingActionButton = findViewById(R.id.fbJugar)
        val rvBoletos : RecyclerView = findViewById(R.id.rvBoletos)

        adapter = BoletoAdapter()
        rvBoletos.adapter = adapter
        rvBoletos.layoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL, false
        )

        fbAgregarTicket.setOnClickListener {
            val intentAgregar = Intent(this, BoletoInfoActivity::class.java)
            intentAgregar.putExtra("usuarioId", usuarioId)
            startActivity(intentAgregar)
        }

        fbJugar.setOnClickListener {
            validarJugar()
        }
    }

    override fun onStart() {
        super.onStart()
        listarBoletos()
    }

    private fun listarBoletos() {
        val queue : RequestQueue = Volley.newRequestQueue(this)

        val usuario = mutableMapOf<String, Any?>()
        usuario["usuario_id"] = usuarioId
        val usuarioJson = JSONObject(usuario)

        val request = JsonObjectRequest(
            Request.Method.POST,
            urlListar,
            usuarioJson,
            { response->
                if(response.getBoolean("exito")){
                    llenarLista(response.getJSONArray("respuesta"))
                }
            },
            {
                Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
                Log.e("NotesActivity", "Error de conexión: " + it.message)
            }
        )

        queue.add(request)
    }

    private fun llenarLista(response: JSONArray) {
        adapter.clear()

        for(i in 0 until response.length()){
            val boleto = response[i] as JSONObject
            val nuevoBoleto = Boleto()

            nuevoBoleto.id = boleto.getInt("id")
            nuevoBoleto.numA = boleto.getInt("num_a")
            nuevoBoleto.numB = boleto.getInt("num_b")
            nuevoBoleto.numC = boleto.getInt("num_c")
            nuevoBoleto.numD = boleto.getInt("num_d")
            nuevoBoleto.numE = boleto.getInt("num_e")
            nuevoBoleto.fecha = boleto.getString("fecha")
            nuevoBoleto.estado = boleto.getString("estado")

            adapter.save(nuevoBoleto)
        }
    }

    private fun validarJugar() {
        val queue : RequestQueue = Volley.newRequestQueue(this)

        val usuario = mutableMapOf<String, Any?>()
        usuario["usuario_id"] = usuarioId
        val usuarioJson = JSONObject(usuario)

        val request = JsonObjectRequest(
            Request.Method.POST,
            urlExistencia,
            usuarioJson,
            { response->
                if(response.getBoolean("exito") && response.getBoolean("respuesta")){
                    val intentJugar = Intent(this, JugarActivity::class.java)
                    intentJugar.putExtra("usuarioId", usuarioId)
                    startActivity(intentJugar)
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