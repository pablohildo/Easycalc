package com.pablohildo.easycalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var tvResult:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById<TextView>(R.id.tvResult)
        tvResult.isSelected = true
        tvResult.setSingleLine(true)
        tvResult.movementMethod = ScrollingMovementMethod()
    }
    fun onEqualClick(v: View){
        val toCalc = tvResult.text.toString()
        if(!toCalc.isNullOrEmpty()){
            try{
                val result = calculate(toCalc)
                tvResult.text = ""+result
            } catch (ex: Exception) {
                val builder = AlertDialog.Builder(this@MainActivity);
                builder.setMessage("Por favor digite uma operação válida.").setTitle("Operação inválida")
                builder.setPositiveButton("OK"){ _, _ ->}
                val dialog = builder.create()
                dialog.show()
                tvResult.text = ""
            }
        }
    }
    fun onClearClick(v: View){
        tvResult.text = ""
    }
    fun onDigitClick(v: View){
        val btn:Button = v as Button
        tvResult.append(btn.text)
    }
    fun calculate(content: String): Float {
        var opperand1: Float
        var opperand2: Float
        var result: Float = 0F
        val isSum: (Char) -> Boolean = {it == '+'}
        val isSub: (Char) -> Boolean = {it == '-'}
        val isDiv: (Char) -> Boolean = {it == '\u00F7'}
        val isMult: (Char) -> Boolean = {it == '\u00D7'}
        val sumCount = content.count(isSum)
        val subCount = content.count(isSub)
        val divCount = content.count(isDiv)
        val multCount = content.count(isMult)
        if ((sumCount + subCount + divCount+multCount)!=1){
            throw Exception()
        } else {
            val separated = content.split('+', '-', '\u00F7', '\u00D7')
            opperand1 = separated[0].trim().toFloat()
            opperand2 = separated[1].trim().toFloat()
            if(sumCount == 1){
                result = opperand1 + opperand2
            } else if (subCount == 1){
                result = opperand1 - opperand2
            } else if (divCount == 1){
                result = opperand1 / opperand2
            } else if (multCount == 1){
                result = opperand1 * opperand2
            }
        }
        return result
    }
}
