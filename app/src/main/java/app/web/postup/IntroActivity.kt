package app.web.postup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.web.postup.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
    fun onClickSignInWithGoogle(v : View){
        Toast.makeText(this, "clicked sign in with google", Toast.LENGTH_SHORT).show()
        val callMainActivityIntent = Intent(this, MainActivity::class.java)
        callMainActivityIntent.putExtra("extra","hello from Intro activity")
        startActivity(callMainActivityIntent)
        finish()

    }
}
