package com.survivalcoding.gangnam2kiandroidstudy.presentation.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.SignInResult
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.UserData
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context
) {
    private val auth = Firebase.auth

    // ✅ GoogleSignInClient 만 사용
    private val googleSignInClient: GoogleSignInClient = run {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    // ✅ signInIntent를 직접 반환
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    // ✅ Intent 결과로부터 Firebase 인증 처리
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return try {
            val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(intent).await()
            val idToken = signInAccount.idToken

            if (idToken.isNullOrBlank()) {
                return SignInResult(data = null, errorMessage = "Google ID Token is null")
            }

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val user = auth.signInWithCredential(credential).await().user

            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    suspend fun signOut() {
        try {
            googleSignInClient.signOut().await() // ✅ GoogleSignInClient 로 로그아웃
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }
}
