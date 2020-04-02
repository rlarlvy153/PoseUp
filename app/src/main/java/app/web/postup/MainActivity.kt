package app.web.postup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.web.postup.BuildConfig
import com.web.postup.R
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
        viewModel.postList.observe(this, Observer{
            for (item in it) {
                Timber.d("hh ${item.text}")
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(item.location.lat.toDouble(), item.location.lng.toDouble()))
                        .title(item.userName)
                        .snippet(item.text)
                )
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.location.lat, item.location.lng), 17f))
            }
        })
//        slideDown(edit_note_container)




    }


    override fun onResume(){
        super.onResume()
        map_parent.post{
            googleMap.setPadding(0,0,map_parent.width - 200,0)
        }
        viewModel.getUserInfo()
//        viewModel.getPostByPostId(3)
//        viewModel.getPostList()
//        edit_note_container.visibility = View.GONE
//        edit_note_container2.visibility = View.INVISIBLE
//        edit_note_container.setBackgroundColor(resources.getColor(R.color.background_edit_container_invisible))


    }
    fun setCurrenLocation(){

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3

            location.let{
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
                viewModel.getPostByRangeFromHere(lastLocation.latitude, lastLocation.longitude, 0.001)
//                tempGetPost()

            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let{
            marker.showInfoWindow()

        }
        return false
    }

    fun onClickSendButton(v : View){
        val text = to_send_text.text.toString()
        if(!text.isBlank()){
            viewModel.addPost(text,lastLocation.latitude.toFloat(), lastLocation.longitude.toFloat())
            to_send_text.setText("")
        }

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
//    fun onClickPostButton(v : View){
//
//        if(movingEditing) return
//        if(isUp){
//            slideDown(edit_note_container)
//
//            //register note !
//            val text = edit_note.text.toString()
//            if(text.isNotBlank()){
//                Timber.d("kgp lastlocation : ${lastLocation.latitude} , ${lastLocation.longitude}")
//                viewModel.addPost(text,lastLocation.latitude.toFloat(), lastLocation.longitude.toFloat())
//            }
//            else{
//                Utils.showToast(this,resources.getString(R.string.empty_post))
//            }
//        }
//        else {
//            slideUp(edit_note_container)
//        }
//        isUp = !isUp
//    }
//    fun slideUp(view :View){
//
//        val ani = TranslateAnimation(0f,0f,view.height.toFloat(),0f).apply{
//            duration = ANIMATION_DURATION
//            fillAfter = true
//            setAnimationListener(object : Animation.AnimationListener{
//                override fun onAnimationRepeat(animation: Animation?) {
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    movingEditing = false
//                }
//
//                override fun onAnimationStart(animation: Animation?) {
//                    edit_note_container.setBackgroundColor(resources.getColor(R.color.background_edit_container_visible))
//                    edit_note_container.visibility = View.VISIBLE
//                    edit_note_container2.visibility = View.VISIBLE
//
//                    movingEditing= true
//                }
//
//            })
//        }
//        view.startAnimation(ani)
//
//    }
//    fun slideDown(view :View){
//
//        val ani = TranslateAnimation(0f,0f,0f,view.height.toFloat()).apply{
//            duration = ANIMATION_DURATION
//            fillAfter = true
//            setAnimationListener(object : Animation.AnimationListener{
//                override fun onAnimationRepeat(animation: Animation?) {
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    movingEditing = false
//                    edit_note_container.visibility = View.GONE
//                    edit_note_container2.visibility = View.GONE
//                    edit_note_container.setBackgroundColor(resources.getColor(R.color.background_edit_container_invisible))
//
//
//                }
//
//                override fun onAnimationStart(animation: Animation?) {
//                    edit_note.clearFocus()
//                    val imm: InputMethodManager =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(edit_note.windowToken, 0)
//                    movingEditing= true
//                }
//
//            })
//        }
//        view.startAnimation(ani)
//    }


}
