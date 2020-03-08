package app.web.postup

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast


class Utils{

    companion object{
        fun showToast(context : Context, msg :String) = Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()


        fun pxToDp(context:Context,px: Int): Int {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }
    }


}