package app.web.postup

import android.Manifest
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
import android.view.animation.TranslateAnimation
import android.widget.EditText
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.web.postup.PostData.PostApi
import app.web.postup.PostData.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.web.postup.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    val ANIMATION_DURATION=300L
    val ACCESS_FINE_LOCATION_CODE=1


    lateinit var googleMap:GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    lateinit var mapFragment:SupportMapFragment

    var userName:String = ""
    var isUp = false

    lateinit var viewModel :ViewModel

    override fun onMapReady(map : GoogleMap) {

        googleMap = map
        googleMap.run{
            uiSettings.isZoomControlsEnabled = true
            setOnMarkerClickListener(this@MainActivity)
        }

        getLocationPermission()



    }
    fun getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_FINE_LOCATION_CODE)
            Timber.d("kgp permission denied")
        }
        else {
            Timber.d("kgp permission granted")
            googleMap.isMyLocationEnabled = true
            setCurrenLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //        userName = intent.getStringExtra("userName")

        userName = "asdf"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.postList.observe(this, Observer{
            for (item in it) {
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(item.location.lat, item.location.lng))
                        .title(item.userName)
                        .snippet(item.text)
                )
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.location.lat, item.location.lng), 17f))
            }
        })


    }
    override fun onResume(){
        super.onResume()
        viewModel.getPostList()
    }
    fun setCurrenLocation(){

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3

            location.let{
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
            }
        }

    }
    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let{
            marker.showInfoWindow()

        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == ACCESS_FINE_LOCATION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                googleMap.isMyLocationEnabled = true
                setCurrenLocation()
            }
            else{

                Utils.showToast(this,resources.getString(R.string.cannot_use_location))
            }
        }
    }
    fun onClickPostButton(v : View){

        if(isUp){
            slideDown(edit_note_container)

            //register note !
            val text = edit_note.text.toString()
            if(text.isNotBlank()){
                googleMap.addMarker(MarkerOptions().position(LatLng(lastLocation.latitude, lastLocation.longitude))
                    .title(userName)
                    .snippet(text)
                )
            }
            else{
                Utils.showToast(this,resources.getString(R.string.empty_post))
            }
        }
        else {
            slideUp(edit_note_container)
        }
        isUp = !isUp
    }
    fun slideUp(view :View){

        val ani = TranslateAnimation(0f,0f,view.height.toFloat(),0f).apply{
            duration = ANIMATION_DURATION
            fillAfter = true
        }
        view.run{
            visibility = View.VISIBLE
            startAnimation(ani)
        }
    }
    fun slideDown(view :View){
        val ani = TranslateAnimation(0f,0f,0f,view.height.toFloat()).apply{
            duration = ANIMATION_DURATION
            fillAfter = true

        }

        view.startAnimation(ani)
        Handler().postDelayed({ view.visibility = View.GONE }, ANIMATION_DURATION)
    }


}
