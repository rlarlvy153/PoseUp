package kr.co.youngcha.postup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity(){
    val ANIMATION_DURATION=300L
    val ACCESS_FINE_LOCATION_CODE=1



    var userName:String = ""
    var isUp = false
    @Volatile
    var movingEditing = false
    lateinit var viewModel : ViewModel
    val mapFragment = MapFragment.instance
    val myPageFragment = MyPageFragment.instance



    fun callFragment(pos:Int?){
        Timber.d("$pos")
        val transaction = supportFragmentManager.beginTransaction()
        when(pos){
            0-> transaction.replace(R.id.main_tab_container,mapFragment)
            1-> transaction.replace(R.id.main_tab_container,myPageFragment)
        }
        transaction.commitNow()
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


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        getLocationPermission()



        callFragment(0)
        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                callFragment(tab?.position)
            }
        })

        //        userName = intent.getStringExtra("userName")


////        slideDown(edit_note_container)
//



    }


    override fun onResume(){

        super.onResume()

//        viewModel.getUserInfo()
//        viewModel.getPostByPostId(3)
//        viewModel.getPostList()
//        edit_note_container.visibility = View.GONE
//        edit_note_container2.visibility = View.INVISIBLE
//        edit_note_container.setBackgroundColor(resources.getColor(R.color.background_edit_container_invisible))


    }






    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == ACCESS_FINE_LOCATION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                googleMap.isMyLocationEnabled = true
//                setCurrenLocation()
            }
            else{

                Utils.showToast(
                    this,
                    resources.getString(R.string.cannot_use_location)
                )
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
