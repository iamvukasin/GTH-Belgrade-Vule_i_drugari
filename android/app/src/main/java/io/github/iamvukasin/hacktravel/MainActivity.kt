package io.github.iamvukasin.hacktravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import io.github.iamvukasin.hacktravel.models.RequestData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val cities = listOf("Belgrade (BEG)", "Berlin (TXL)", "Munich (MUC)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val citiesAdapter = ArrayAdapter(
            this,
            android.R.layout.select_dialog_item,
            cities
        )

        from_destination_value.setAdapter(citiesAdapter)
        to_destination_value.setAdapter(citiesAdapter)

        search_button.setOnClickListener {
            val requestData = RequestData(
                from_date_value.text.toString(),
                to_date_value.text.toString(),
                from_destination_value.text.toString(),
                to_destination_value.text.toString(),
                adults_count_value.text.toString().toInt(),
                children_count_value.text.toString().toInt()
            )

            pets_count_value.text.toString().toIntOrNull()?.let { petNumber ->
                val intent = Intent(this, PetInfoActivity::class.java).apply {
                    putExtra(EXTRA_PASSENGER_DATA, requestData)
                    putExtra(EXTRA_PET_NUMBER, petNumber)
                }
                startActivity(intent)
            }
        }
    }
}
