package com.survivalcoding.gangnam2kiandroidstudy.presentation.auth

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.survivalcoding.gangnam2kiandroidstudy.R

/**
 * Google Sign-In client using Credential Manager (androidx.credentials 1.5.0)
 *
 * 주의:
 * - getIdToken() 호출 시 반드시 Activity context를 전달해야 함
 * - Credential은 CustomCredential로 감싸져 반환되므로 언래핑 필요
 */
class GoogleAuthUiClient(
    private val appContext: Context
) {

    private val credentialManager = CredentialManager.create(appContext)

    private val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(appContext.getString(R.string.default_web_client_id))
        .setFilterByAuthorizedAccounts(false)
        .build()

    private val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    /**
     * Google ID Token 반환
     *
     * @param activity Credential 선택 UI를 띄우기 위한 Activity context
     */
    suspend fun getIdToken(activity: Activity): String {
        try {
            val result = credentialManager.getCredential(
                context = activity,
                request = request
            )

            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                return googleCredential.idToken
            } else {
                throw IllegalStateException("Unexpected credential type")
            }

        } catch (e: GetCredentialCancellationException) {
            // 사용자가 로그인 취소
            throw e

        } catch (e: GetCredentialInterruptedException) {
            // 일시적 중단 (재시도 가능)
            throw e

        } catch (e: GetCredentialUnsupportedException) {
            // 기기 / OS 미지원
            throw e

        } catch (e: GetCredentialProviderConfigurationException) {
            // Credential Provider 설정 문제
            throw e

        } catch (e: GetCredentialException) {
            // 기타 Credential 오류 (계정 없음 포함)
            throw e
        }
    }
}
