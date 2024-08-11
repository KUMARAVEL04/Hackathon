package com.example.dplace

import android.app.Activity
import android.content.Context
import android.credentials.CredentialManager
import android.credentials.GetCredentialRequest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CustomCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.security.MessageDigest
import java.util.UUID

private suspend fun googleSignIn(context: Context): kotlinx.coroutines.flow.Flow<Result<AuthResult>> {
    val firebaseAuth = FirebaseAuth.getInstance()
    return callbackFlow {
        try {
            val credentialManager: androidx.credentials.CredentialManager = androidx.credentials.CredentialManager.create(context)
            val ranNonce: String = UUID.randomUUID().toString()
            val bytes: ByteArray = ranNonce.toByteArray()
            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            val digest: ByteArray = md.digest(bytes)
            val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("44735269550-8pgicsbuf17t0hostq930tigjallluom.apps.googleusercontent.com")
                .setNonce(hashedNonce)
                .build()
            val request: androidx.credentials.GetCredentialRequest = androidx.credentials.GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                println("${authCredential}")
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                println("${authResult.user?.uid}")
                ab="${authResult.user?.uid}"
                trySend(Result.success(authResult))
                println("herenowsuc")
            } else {
                throw RuntimeException("Received an invalid credential type")
            }
        } catch (e: GetCredentialCancellationException) {
            trySend(Result.failure(Exception("Sign-in was canceled. Please try again.")))
            println("herenow")
        } catch (e: Exception) {
            println("here")
            trySend(Result.failure(e))
        }

        awaitClose { }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = LoginViewModel(),context: Context,navController: NavController
) {
    val isDarkTheme = isSystemInDarkTheme()
    val onClick = { viewModel.handleGoogleSignIn(context,navController) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Surface(
            shape = CircleShape,
            color = when (isDarkTheme) {
                true -> Color(0xFF131314)
                false -> Color(0xFFFFFFFF)
            },
            modifier = Modifier
                .height(50.dp)
                .width(260.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = when (isDarkTheme) {
                            true -> Color(0xFF8E918F)
                            false -> Color.Transparent
                        }
                    ),
                    shape = CircleShape
                )
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Spacer(modifier = Modifier.width(14.dp))
                Image(
                    painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Continue with Google")
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }
}

class LoginViewModel : ViewModel() {
    var dialogShow = false
    fun handleGoogleSignIn(context: Context,navController:NavController) {
        viewModelScope.launch {
            googleSignIn(context).collect { result->
                result.fold(
                    onSuccess = {
                        job.launch(Dispatchers.IO) {
                            try{
                                runBlocking {
                                    val response = RetrofitInstance.api.clear()
                                    if (response.isSuccessful) {
                                        response.body()?.let {
                                            ax = it
                                            println("ax:${ax.toString()}")
                                        }
                                    } else {
                                        println("not ax:${ax.toString()}")
                                    }
                                }
                                withContext(Dispatchers.Main) {
                                    navController.navigate("grid")
                                }
                            } catch (e: Exception) {withContext(Dispatchers.Main) {
                                Toast
                                    .makeText(
                                        context,
                                        "Network Error, Try Again",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                dialogShow = true
                            }} catch (e: Error) {withContext(Dispatchers.Main) {
                                Toast
                                    .makeText(
                                        context,
                                        "Network Error, Try Again",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                dialogShow = true
                            }} catch (e: HttpException){withContext(Dispatchers.Main) {
                                Toast
                                    .makeText(
                                        context,
                                        "Network Error, Try Again",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                dialogShow = true
                            }}

                        }
                        println("Successs")
                    },
                    onFailure = { e ->
                        Toast
                            .makeText(
                                context,
                                "Network Error, Try Again",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        dialogShow = true
                    }
                )
            }
        }
    }

}