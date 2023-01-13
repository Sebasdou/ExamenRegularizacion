package com.ucc.examenregularizacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class BoletoInfoActivity : AppCompatActivity() {
    private val host = "10.200.21.118:8000"
    private val urlCrear = "http://$host/boletos/crear.php"
    private val urlActualizar = "http://$host/boletos/actualizar.php"
    private val urlEliminar = "http://$host/boletos/eliminar.php"
    private var usuarioId = 0
    private var boletoId = 0
    private var esActualizacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boleto_info)

        val bGuardar : Button = findViewById(R.id.bGuardar)
        val bEliminar : Button = findViewById(R.id.bEliminar)

        if(intent.hasExtra("boletoId")) {
            boletoId = intent.getIntExtra("boletoId", 0)

            if(boletoId == 0) {
                Toast.makeText(
                    applicationContext, "Error al actualizar boleto.", Toast.LENGTH_SHORT
                ).show()
                finish()
            }

            bEliminar.visibility = View.VISIBLE

            esActualizacion = true
        } else {
            usuarioId = intent.getIntExtra("usuarioId", 0)

            if(usuarioId == 0){
                Toast.makeText(
                    applicationContext, "Error con la sesión", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

        bGuardar.setOnClickListener {
            guardarBoleto()
        }

        bEliminar.setOnClickListener {
            eliminarBoleto()
        }
    }

    private fun guardarBoleto() {
        val rgBola1 : RadioGroup = findViewById(R.id.rgBola1)
        val rgBola2 : RadioGroup = findViewById(R.id.rgBola2)
        val rgBola3 : RadioGroup = findViewById(R.id.rgBola3)
        val rgBola4 : RadioGroup = findViewById(R.id.rgBola4)
        val rgBola5 : RadioGroup = findViewById(R.id.rgBola5)

        val rbBolaId1 = rgBola1.checkedRadioButtonId
        val rbBolaId2 = rgBola2.checkedRadioButtonId
        val rbBolaId3 = rgBola3.checkedRadioButtonId
        val rbBolaId4 = rgBola4.checkedRadioButtonId
        val rbBolaId5 = rgBola5.checkedRadioButtonId

        val rbBola1 : RadioButton = rgBola1.findViewById(rbBolaId1)
        val rbBola2 : RadioButton = rgBola2.findViewById(rbBolaId2)
        val rbBola3 : RadioButton = rgBola3.findViewById(rbBolaId3)
        val rbBola4 : RadioButton = rgBola4.findViewById(rbBolaId4)
        val rbBola5 : RadioButton = rgBola5.findViewById(rbBolaId5)

        val strBola1 : String = rbBola1.text.toString()
        val strBola2 : String = rbBola2.text.toString()
        val strBola3 : String = rbBola3.text.toString()
        val strBola4 : String = rbBola4.text.toString()
        val strBola5 : String = rbBola5.text.toString()

        var urlEndpoint = ""
        val boleto = mutableMapOf<String, Any?>()
        boleto["num_a"] = strBola1
        boleto["num_b"] = strBola2
        boleto["num_c"] = strBola3
        boleto["num_d"] = strBola4
        boleto["num_e"] = strBola5

        if(esActualizacion){
            boleto["boleto_id"] = boletoId
            urlEndpoint = urlActualizar
        } else {
            boleto["usuario_id"] = usuarioId
            urlEndpoint = urlCrear
        }

        val boletoJson = JSONObject(boleto)

        enviarDatos(boletoJson, urlEndpoint)
    }

    private fun eliminarBoleto() {
        val boleto = mutableMapOf<String, Any?>()
        boleto["boleto_id"] = boletoId
        val boletoJson = JSONObject(boleto)

        enviarDatos(boletoJson, urlEliminar)
    }

    private fun enviarDatos(json: JSONObject, urlEndpoint: String) {
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.POST,
            urlEndpoint,
            json,
            { response ->
                if(response.getBoolean("exito")){
                    Toast.makeText(
                        this, response.getString("mensaje"), Toast.LENGTH_SHORT
                    ).show()
                    finish()
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