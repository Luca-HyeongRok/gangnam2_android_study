package com.survivalcoding.gangnam2kiandroidstudy.presentation.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.survivalcoding.gangnam2kiandroidstudy.R

class GoogleAuthUiClient(
    private val context: Context
) {

    private val credentialManager = CredentialManager.create(context)

    private val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .setFilterByAuthorizedAccounts(false)
        .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    suspend fun getIdToken(): String {
        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val credential = result.credential
        if (credential !is GoogleIdTokenCredential) {
            throw IllegalStateException("Invalid Google credential")
        }

        return credential.idToken
    }
}
