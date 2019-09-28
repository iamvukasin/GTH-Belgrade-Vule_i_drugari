package io.github.iamvukasin.hacktravel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import io.github.iamvukasin.hacktravel.models.Pet
import io.github.iamvukasin.hacktravel.models.RequestData
import kotlinx.android.synthetic.main.activity_pet_info.*

const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"
const val EXTRA_PET_NUMBER = "EXTRA_PET_NUMBER"

class PetInfoActivity : AppCompatActivity() {

    private val petInfos = mutableListOf<View>()
    private val petTypes = listOf("Dog", "Cat")
    private val petBreeds = listOf(
        listOf("Beagle", "Labrador"),
        listOf("All")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_info)

        val requestData = intent.getSerializableExtra(EXTRA_PASSENGER_DATA) as RequestData
        val petNumber = intent.getIntExtra(EXTRA_PET_NUMBER, 0)

        for (i in 1..petNumber) {
            val singlePetInfo = layoutInflater.inflate(
                R.layout.single_pet_info_layout,
                list_pet_info_content, false
            )

            val petTitle = singlePetInfo.findViewById<TextView>(R.id.pet_title)
            val petType = singlePetInfo.findViewById<AppCompatAutoCompleteTextView>(R.id.pet_type)
            val petBreed = singlePetInfo.findViewById<AppCompatAutoCompleteTextView>(R.id.pet_breed)

            petTitle.text = "Pet $i"
            petType.setAdapter(
                ArrayAdapter(
                    this,
                    android.R.layout.select_dialog_item,
                    petTypes
                )
            )
            petType.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                petBreed.setText("")
                petBreed.setAdapter(
                    ArrayAdapter(
                        this,
                        android.R.layout.select_dialog_item,
                        petBreeds[position]
                    )
                )
            }

            list_pet_info_content.addView(singlePetInfo)
            petInfos.add(singlePetInfo)
        }

        search_button.setOnClickListener {
            val listOfPets = mutableListOf<Pet>()

            for (petInfo in petInfos) {
                listOfPets.add(
                    Pet(
                        petInfo.findViewById<AppCompatAutoCompleteTextView>(R.id.pet_type).text.toString(),
                        petInfo.findViewById<AppCompatAutoCompleteTextView>(R.id.pet_breed).text.toString(),
                        petInfo.findViewById<TextInputEditText>(R.id.pet_age).text.toString().toInt(),
                        petInfo.findViewById<TextInputEditText>(R.id.pet_weight).text.toString().toInt()
                    )
                )
            }

            val intent = Intent(this, ResultsActivity::class.java).apply {
                putExtra(EXTRA_REQUEST_DATA, requestData)
                putExtra(EXTRA_PET_DATA, ArrayList(listOfPets))
            }
            startActivity(intent)
        }
    }
}
