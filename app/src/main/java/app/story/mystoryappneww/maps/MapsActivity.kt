package app.story.mystoryappneww.maps


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.story.mystoryappneww.R
import app.story.mystoryappneww.databinding.ActivityMapsBinding
import app.story.mystoryappneww.dataclass.ListStoryItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTV3bnRybmhuZGFVUUVmVlMiLCJpYXQiOjE2ODM2NDk4MDB9.aM3MrlpeLPLurzHiH1bJ46klDhTwQfrDDzWdqipNLFU"
        val url = "https://story-api.dicoding.dev/v1/stories?location=1"
        val request = Request.Builder().url(url).method("GET", null).addHeader("Authorization", "Bearer $token").build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to Fetch Stories", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val jsonObject = JSONObject(body)
                try {
                    val storiesArray = jsonObject.getJSONArray("listStory")

                    val stories = Gson().fromJson(storiesArray.toString(), Array<ListStoryItem>::class.java)

                    runOnUiThread {
                        for (story  in stories) {
                            val latLng = LatLng(story.lat, story.lon)
                            mMap.addMarker(MarkerOptions().position(latLng).title(story.name))
                        }
                        mMap.setMinZoomPreference(5f)
                        val indonesia = LatLng(-2.548926, 118.014863)
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesia))
                    }
                } catch(e: Exception) {
                    Log.e(TAG, "Invalid JSON Response : $e")
                }
            }

        })
    }
    companion object {
        private const val TAG = "MapsActivity"
    }
}