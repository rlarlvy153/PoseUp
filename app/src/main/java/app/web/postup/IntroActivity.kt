package app.web.postup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.web.postup.R

import com.google.firebase.auth.GoogleAuthProvider






class IntroActivity : AppCompatActivity() {

    private val TAG = "kgp::IntroActivity::"
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    private val RC_SIGN_IN = 900
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance()


        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

    }
    fun onClickSignInWithGoogle(v : View){

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)


//        val callMainActivityIntent = Intent(this, MainActivity::class.java)
//        callMainActivityIntent.putExtra("extra","hello from Intro activity")
//        startActivity(callMainActivityIntent)
//        finish()

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // 구글 로그인 성공
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)

                account?.let{
                    Log.i(TAG,account.displayName + "")
                    Log.i(TAG,account.id + "")
                    Log.i(TAG,account.familyName + "")
                    Log.i(TAG,account.email + "")
                    Log.i(TAG,account.givenName + "")
                    Log.i(TAG,account.toString() + "")

                    val callMainActivityIntent = Intent(this, MainActivity::class.java)
                    callMainActivityIntent.putExtra("userName",account.displayName)
                    startActivity(callMainActivityIntent)
                    finish()
                }

            } catch (e: ApiException) {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct:GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(acct!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this
                ) { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    } else {
                        // 로그인 실패
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
        }
    }
}
