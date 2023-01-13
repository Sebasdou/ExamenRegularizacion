package com.ucc.examenregularizacion.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ucc.examenregularizacion.BoletoInfoActivity
import com.ucc.examenregularizacion.R
import com.ucc.examenregularizacion.entity.Boleto
import com.ucc.examenregularizacion.holder.BoletoHolder

class BoletoAdapter : RecyclerView.Adapter<BoletoHolder>() {
    private var data : ArrayList<Boleto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoletoHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_boleto, parent, false
        )
        return BoletoHolder(view)
    }

    override fun onBindViewHolder(holder: BoletoHolder, position: Int) {
        val boleto : Boleto = data[position]

        var boletoStr = ""

        if(boleto.estado == "GANADOR"){
            boletoStr = "${boleto.numA}, ${boleto.numB}, ${boleto.numC}, ${boleto.numD}, ${boleto.numE} [GANADOR]"
        } else {
            boletoStr = "${boleto.numA}, ${boleto.numB}, ${boleto.numC}, ${boleto.numD}, ${boleto.numE}"
        }

        holder.tvBoleto.text = boletoStr
        holder.tvFecha.text = "Comprado el ${boleto.fecha}"
        holder.tvBoletoId.text = boleto.id.toString()

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, BoletoInfoActivity::class.java)
            intent.putExtra("boletoId", boleto.id)
            intent.putExtra("numA", boleto.numA)
            intent.putExtra("numB", boleto.numB)
            intent.putExtra("numC", boleto.numC)
            intent.putExtra("numD", boleto.numD)
            intent.putExtra("numE", boleto.numE)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun save(note: Boleto) {
        data.add(note)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
    }
}