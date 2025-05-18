package com.jlino.cineapp.cine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.jlino.cineapp.R

class Cinepolis : AppCompatActivity() {

    private lateinit var spnMovie: Spinner;
    private lateinit var npCLient: NumberPicker;
    private lateinit var npTickets : NumberPicker;
    private lateinit var rbSi :RadioButton;
    private lateinit var rbNo : RadioButton;
    private lateinit var tvPrice : TextView;
    private lateinit var radioGroup: RadioGroup;
    private lateinit var btnBuy : Button;

    var TICKETS_PER_PERSON = 7;
    var PRICE_TICKETS = 12.00
    var CINECO_CARD_REDUCTION = 0.10;
    var haveCineco = false;
    var isReadyToBuy = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinepolis)

        spnMovie=findViewById<Spinner>(R.id.spnMovie);
        npCLient=findViewById<NumberPicker>(R.id.npClient);
        npTickets=findViewById<NumberPicker>(R.id.npTickets);
        rbSi=findViewById<RadioButton>(R.id.rbSi);
        rbNo = findViewById<RadioButton>(R.id.rbNo);
        tvPrice = findViewById<TextView>(R.id.tvPrice);
        radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        btnBuy = findViewById<Button>(R.id.btnBuy)


        npCLient.minValue = 1
        npCLient.maxValue = 10
        npCLient.wrapSelectorWheel = false

        npTickets.minValue = 1
        npTickets.maxValue = 10
        npTickets.wrapSelectorWheel = false

        rbNo.isChecked = false;

        tvPrice.text= "Total: $PRICE_TICKETS";

        val peliculas = arrayOf(
            "Spider-Man: No Way Home",
            "Avengers: Endgame",
            "Doctor Strange: Multiverse of Madness",
            "Iron Man",
            "Black Panther",
            "Guardianes de la Galaxia"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, peliculas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnMovie.adapter = adapter


        npCLient.setOnValueChangedListener { _, _, newVal ->
            val nuevoMax = newVal * TICKETS_PER_PERSON;
            npTickets.maxValue = nuevoMax

            if (npTickets.value > nuevoMax) {
                npTickets.value = nuevoMax
            }
        }


        npTickets.setOnValueChangedListener { _, _, tickets ->
            calculateReductions(tickets)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbSi -> {
                    haveCineco = true;
                    calculateReductions(npTickets.value)

                }
                R.id.rbNo -> {
                     haveCineco = false;
                    calculateReductions(npTickets.value)
                }
            }
        }

        btnBuy.setOnClickListener {

                Toast.makeText(this, "¡Compra realizada con éxito!", Toast.LENGTH_LONG).show()


        }



    }

    fun calculateReductions(tickets:Int){
        if ( tickets > 2 && tickets < 5 ){
            buyTickets(0.10,tickets,haveCineco)
        }
        else if (tickets > 5){
            buyTickets(0.15,tickets,haveCineco)
        }
        else{
            buyTickets(0.00,tickets,haveCineco)
        }
    }

    fun buyTickets (reduction:Double, tickets:Int,carCineco:Boolean){
        var price = tickets * PRICE_TICKETS;
        price = price - (price*reduction);

        if (carCineco == true){
            price = price - (price* CINECO_CARD_REDUCTION)
        }
        tvPrice.text= "Total: $price";
        isReadyToBuy =true;
    }



}

