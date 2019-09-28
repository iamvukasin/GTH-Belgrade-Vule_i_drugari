package io.github.iamvukasin.hacktravel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.github.iamvukasin.hacktravel.api.createFlightService
import io.github.iamvukasin.hacktravel.models.Flight
import io.github.iamvukasin.hacktravel.models.Pet
import io.github.iamvukasin.hacktravel.models.RequestData
import kotlinx.android.synthetic.main.activity_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val EXTRA_REQUEST_DATA = "EXTRA_REQUEST_DATA"
const val EXTRA_PET_DATA = "EXTRA_PET_DATA"

private val airlinesCodesToNames = mapOf(
    "LH" to "Lufthansa",
    "JU" to "Air Serbia",
    "AF" to "Air France"
)

private val airlineLogosToNames = mapOf(
    "LH" to R.drawable.lh_logo,
    "JU" to R.drawable.ju_logo,
    "AF" to R.drawable.af_logo
)

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val requestData = intent.getSerializableExtra(EXTRA_REQUEST_DATA) as RequestData
        val pets = intent.getParcelableArrayListExtra<Pet>(EXTRA_PET_DATA)
        val service = createFlightService()
        val petsInJson =
            pets.joinToString(separator = ",", prefix = "[", postfix = "]") { it.toString() }

        val fromLocation = getAirportCode(requestData.fromLocation)
        val toLocation = getAirportCode(requestData.toLocation)

        service.getFlights(
            fromLocation,
            toLocation,
            requestData.fromDate,
            requestData.toDate,
            requestData.numAdults,
            requestData.numChildren,
            petsInJson
        ).enqueue(object : Callback<List<Flight>> {
            override fun onFailure(call: Call<List<Flight>>, t: Throwable) {
                Log.w("ERROR", t.localizedMessage ?: "")
                Log.w("ERROR", call.request().url().toString())
            }

            override fun onResponse(call: Call<List<Flight>>, response: Response<List<Flight>>) {
                progress_bar.visibility = View.GONE

                for (flight in response.body()!!) {
                    val singleFlightView = layoutInflater.inflate(
                        R.layout.result_layout,
                        flight_results_content,
                        false
                    )

                    val departingAirlineLogo = singleFlightView.findViewById<ImageView>(R.id.departing_airline_logo)
                    val returningAirlineLogo = singleFlightView.findViewById<ImageView>(R.id.returning_airline_logo)
                    val departingAirline = singleFlightView.findViewById<TextView>(R.id.departing_airline_name)
                    val returningAirline = singleFlightView.findViewById<TextView>(R.id.returning_airline_name)
                    val departingRoute = singleFlightView.findViewById<TextView>(R.id.departing_route)
                    val returningRoute = singleFlightView.findViewById<TextView>(R.id.returning_route)
                    val humanPrice = singleFlightView.findViewById<TextView>(R.id.result_human_price)
                    val petPrice = singleFlightView.findViewById<TextView>(R.id.result_pet_price)
                    val totalPrice = singleFlightView.findViewById<TextView>(R.id.result_total_price)
                    val stopsFrom = singleFlightView.findViewById<TextView>(R.id.stops_from)
                    val stopsTo = singleFlightView.findViewById<TextView>(R.id.stops_to)
                    val dateFrom = singleFlightView.findViewById<TextView>(R.id.date_from)
                    val dateTo = singleFlightView.findViewById<TextView>(R.id.date_to)
                    val durationFrom = singleFlightView.findViewById<TextView>(R.id.duration_from)
                    val durationTo = singleFlightView.findViewById<TextView>(R.id.duration_to)

                    val departingLogoId = airlineLogosToNames[flight.iata_from] ?: R.drawable.other_airline
                    val returningLogoId = airlineLogosToNames[flight.iata_to] ?: R.drawable.other_airline

                    departingAirlineLogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, departingLogoId))
                    returningAirlineLogo.setImageDrawable(ContextCompat.getDrawable(applicationContext, returningLogoId))
                    departingAirline.text = airlinesCodesToNames[flight.iata_from] ?: "Other"
                    returningAirline.text = airlinesCodesToNames[flight.iata_to] ?: "Other"
                    departingRoute.text = "$fromLocation ➡ $toLocation"
                    returningRoute.text = "$toLocation ➡ $fromLocation"
                    humanPrice.text = "${flight.price} EUR"
                    petPrice.text = "${flight.price_with_pets} EUR"
                    totalPrice.text = "${flight.price + flight.price_with_pets} EUR"
                    stopsFrom.text = formatNumberOfStops(flight.stops_from)
                    stopsTo.text = formatNumberOfStops(flight.stops_to)
                    dateFrom.text = flight.flight_date_from
                    dateTo.text = flight.flight_date_to
                    durationFrom.text = flight.flight_time_from
                    durationTo.text = flight.flight_time_to

                    singleFlightView.setOnClickListener {
                        val uri = Uri.parse(flight.link)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }

                    flight_results_content.addView(singleFlightView)
                }
            }
        })
    }

    private fun getAirportCode(location: String): String {
        return """\(([A-Z]{3})\)""".toRegex().find(location)?.groupValues?.getOrNull(1) ?: ""
    }

    private fun formatNumberOfStops(numStops: Int): String {
        return when (numStops) {
            0 -> resources.getString(R.string.direct_flight)
            else -> resources.getQuantityString(R.plurals.num_stops, numStops, numStops)
        }
    }
}
