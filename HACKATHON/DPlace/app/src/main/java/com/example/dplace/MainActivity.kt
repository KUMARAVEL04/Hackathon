package com.example.dplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.d_place.GridViewMine
import com.example.dplace.ui.theme.DPlaceTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.example.dplace.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


var ab:String = ""
val job = CoroutineScope(Dispatchers.IO)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DPlaceTheme {
                @Composable
                fun Navigate() {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = LoginViewModel()
                            LoginScreen(viewModel,applicationContext,navController)
                        }
                        composable("grid") {
                            GridViewMine(7,9)
                        }
                    }
                }
                Navigate()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
//        SocketHandler.closeConnection()
        job.cancel()
        super.onDestroy()
    }
}


fun hexToColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

data class DataRequest(
    val position:Int,
    val color:String
)

data class DataResponse(
    val id:Int,
    val position:Int,
    val color:String
)

interface ChaseDeuxInterface {
    @GET("/")
    suspend fun all(): Response<List<DataResponse>>

    @GET("/return")
    suspend fun clear(): Response<List<DataResponse>>

    @POST("/ab")
    suspend fun push(@Body dataRequest: DataRequest) :  Response<List<DataResponse>>
}

object RetrofitInstance {
    var urlText:String = "http://localhost:8000/"
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val api:ChaseDeuxInterface by lazy {
        Retrofit.Builder().baseUrl(urlText)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ChaseDeuxInterface::class.java)

    }
}
