package app.web.postup

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.web.postup.R
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.web.postup.BuildConfig
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
    @Volatile
    var movingEditing = false
    lateinit var viewModel :ViewModel

    override fun onMapReady(map : GoogleMap) {

        googleMap = map
        googleMap.run{
            uiSettings.isZoomControlsEnabled = true
            setOnMarkerClickListener(this@MainActivity)
            //googleMap.setPadding(left, top, right, bottom);

        }

        getLocationPermission()
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

        userName = "dummy name"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)



    }
    override fun onResume(){
        super.onResume()
        map_parent.post{
            googleMap.setPadding(0,0,map_parent.width - 200,0)
        }


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
        if(movingEditing) return
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
            setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    movingEditing = false
                }

                override fun onAnimationStart(animation: Animation?) {
                    edit_note_container.visibility = View.VISIBLE
                    movingEditing= true
                }

            })
        }
        view.startAnimation(ani)

    }
    fun slideDown(view :View){
        val ani = TranslateAnimation(0f,0f,0f,view.height.toFloat()).apply{
            duration = ANIMATION_DURATION
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    movingEditing = false
                    edit_note_container.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                    movingEditing= true
                }

            })
        }
        view.startAnimation(ani)
//        Handler().postDelayed({ view.visibility = View.GONE }, ANIMATION_DURATION)
    }


}
