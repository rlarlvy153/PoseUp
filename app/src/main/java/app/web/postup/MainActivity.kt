package app.web.postup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.web.postup.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }
    fun onClickPostButton(v : View){
        Toast.makeText(this,"clicked post button", Toast.LENGTH_SHORT).show();


    }


}
