package com.ucc.examenregularizacion.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ucc.examenregularizacion.R

class BoletoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var  tvBoletoId : TextView
    var tvBoleto : TextView
    var  tvFecha : TextView

    init {
        tvBoletoId = itemView.findViewById(R.id.tvBoletoId)
        tvBoleto = itemView.findViewById(R.id.tvBoleto)
        tvFecha = itemView.findViewById(R.id.tvFecha)
    }
}