package app.web.postup

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.web.postup.R
import android.os.Handler
import android.util.Log
import android.widget.EditText
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    val ANIMATION_DURATION=300L
    val ACCESS_FINE_LOCATION_CODE=1

    lateinit var editNoteContainer :LinearLayout
    lateinit var editNote : EditText

    lateinit var googleMap:GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    lateinit var mapFragment:SupportMapFragment

    var isUp = false
    override fun onMapReady(map : GoogleMap) {
        Log.i("kgp","map ready")
        googleMap = map
        googleMap.uiSettings.isZoomControlsEnabled =true
        googleMap.setOnMarkerClickListener(this)
        getLocationPermission()
    }
    fun getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_FINE_LOCATION_CODE)
            Log.i("kgp","permission non granted")
        }
        else {
            Log.i("kgp","permission granted")
            googleMap.isMyLocationEnabled = true
            setCurrenLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNoteContainer = findViewById(R.id.edit_note_container)
        editNoteContainer.translationY = 1000f
        editNoteContainer.visibility = View.VISIBLE

        editNote = findViewById(R.id.edit_note)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    fun setCurrenLocation(){

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Log.i("kgp",currentLatLng.longitude.toString() + " " + currentLatLng.latitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
            }
        }

    }
    override fun onMarkerClick(marker: Marker?): Boolean {
        return true
    }

    fun showToast(message : String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == ACCESS_FINE_LOCATION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                googleMap.isMyLocationEnabled = true
                setCurrenLocation()
            }
            else{
                showToast(resources.getString(R.string.cannot_use_location))
            }
        }
    }
    fun onClickPostButton(v : View){
        if(isUp){
            slideDown(editNoteContainer)

            //register note !
            Toast.makeText(this, "editNote : " + editNote.text.toString(),Toast.LENGTH_SHORT).show()
            val text = editNote.text.toString()
            googleMap.addMarker(MarkerOptions().position(LatLng(lastLocation.latitude, lastLocation.longitude)))

        }
        else {
            slideUp(editNoteContainer)
        }
        isUp = !isUp
    }
    fun slideUp(view :View){
        view.translationY = view.height.toFloat()
        view.visibility = View.VISIBLE
        editNoteContainer.translationY = view.height.toFloat()
        ObjectAnimator.ofFloat(view, "translationY", 0f).apply {
            duration = ANIMATION_DURATION
            start()
        }

    }
    fun slideDown(view :View){

        ObjectAnimator.ofFloat(view, "translationY", view.height.toFloat()).apply {
            duration = ANIMATION_DURATION
            start()
        }
        Handler().postDelayed(Runnable { view.visibility = View.GONE }, ANIMATION_DURATION)
    }


}
