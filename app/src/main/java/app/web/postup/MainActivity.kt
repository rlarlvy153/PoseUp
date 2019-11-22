package app.web.postup

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.view.animation.TranslateAnimation
import com.web.postup.R
import android.os.Handler


class MainActivity : AppCompatActivity() {

    val ANIMATION_DURATION=500L
    lateinit var editNoteContainer :LinearLayout
    var isUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNoteContainer = findViewById(R.id.edit_note_container)
        editNoteContainer.translationY = 1000f
        editNoteContainer.visibility = View.VISIBLE

    }
    fun onClickPostButton(v : View){
        Toast.makeText(this,"" + v.height, Toast.LENGTH_SHORT).show();
        if(isUp){
            slideDown(editNoteContainer)
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
