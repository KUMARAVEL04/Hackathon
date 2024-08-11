package com.example.d_place

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import com.example.dplace.DataRequest
import com.example.dplace.DataResponse
import com.example.dplace.R
import com.example.dplace.RetrofitInstance
import com.example.dplace.ab
import com.example.dplace.ax
import com.example.dplace.hexToColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.math.min

val job = CoroutineScope(Dispatchers.IO)

@Composable
fun GridViewMine(row: Int, col: Int) {
    var enabled by remember {
        mutableStateOf(true)
    }
    var scale by remember { mutableStateOf(1f) }
    var chosenColor by remember { mutableStateOf(0) }
    val colors = remember { listOf(Color.Cyan, Color.Red, Color.DarkGray, Color.Black) }

    var axState by remember { mutableStateOf(ax) }
    var dialogShow by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        job.launch {
            while (isActive) {
                try{
                    val res = RetrofitInstance.api.all()
                    if (res.isSuccessful) {
                        res.body()?.let {
                            axState = it
                        }
                    }
                    delay(5000L)
                } catch (e: Exception) {
                    Toast
                        .makeText(
                            context,
                            "Network Error, Try Again",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    dialogShow = true
                } catch (e: Error) {
                    Toast
                        .makeText(
                            context,
                            "Network Error, Try Again",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    dialogShow = true
                } catch (e: HttpException){
                    Toast
                        .makeText(
                            context,
                            "Network Error, Try Again",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    dialogShow = true
                }
            }
        }
    }
    var text1 by remember {
        mutableStateOf(15)
    }
    var text2 by remember {
        mutableStateOf(15)
    }
    var text3 by remember {
        mutableStateOf(15)
    }
    var text4 by remember {
        mutableStateOf(15)
    }
    var text5 by remember {
        mutableStateOf(15)
    }
    var text6 by remember {
        mutableStateOf(15)
    }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.1f)
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text1 < 15) {
                                text1++
                            }
                        })
                    Text(text=text1.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text1 > 0) {
                                text1--
                            }
                        })
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text2 < 15) {
                                text2++
                            }
                        })
                    Text(text=text2.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text2 > 0) {
                                text2--
                            }
                        })
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text3 < 15) {
                                text3++
                            }
                        })
                    Text(text=text3.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text3 > 0) {
                                text3--
                            }
                        })
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text4 < 15) {
                                text4++
                            }
                        })
                    Text(text=text4.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text4 > 0) {
                                text4--
                            }
                        })
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text5 < 15) {
                                text5++
                            }
                        })
                    Text(text=text5.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text5 > 0) {
                                text5--
                            }
                        })
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text6 < 15) {
                                text6++
                            }
                        })
                    Text(text=text6.toString(), modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier
                        .background(Color.Blue)
                        .weight(0.25f)
                        .padding(12.dp)
                        .clickable {
                            if (text6 > 0) {
                                text6--
                            }
                        })
                }
            }
        }
    ) { padding ->
        Box(contentAlignment = Alignment.Center) {
            Column {
                val ab by remember { mutableStateOf(ab) }
                Text(text = ab)
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    var minsize by remember { mutableStateOf(0.dp) }
                    var size by remember { mutableStateOf(0.dp) }
                    with(LocalDensity.current) {
                        if (constraints.maxWidth / col > constraints.maxHeight / row) {
                            minsize = (constraints.maxHeight / row).toDp()
                            size = (constraints.maxHeight).toDp()
                        } else {
                            minsize = (constraints.maxWidth / col).toDp()
                            size = (constraints.maxWidth).toDp()
                        }
                    }
                    Column(modifier = Modifier.size(size)) {
                        for (i in 0 until row) {
                            Row {
                                for (j in 0 until col) {
                                    val colorx = hexToColor(axState[i * col + j].color)
                                    Box(
                                        modifier = Modifier
                                            .size(minsize)
                                            .padding(2.dp)
                                            .background(colorx)
                                            .border(2.dp, Color.Blue)
                                            .clickable {
                                                if (enabled) {
                                                    coroutineScope.launch(Dispatchers.IO) {
                                                        try{
                                                            val response =
                                                                RetrofitInstance.api.push(
                                                                    DataRequest(
                                                                        i * col + j,
                                                                        "#${
                                                                            Integer.toHexString(
                                                                                text1
                                                                            )
                                                                        }${
                                                                            Integer.toHexString(
                                                                                text2
                                                                            )
                                                                        }${Integer.toHexString(text3)}${
                                                                            Integer.toHexString(
                                                                                text4
                                                                            )
                                                                        }${Integer.toHexString(text5)}${
                                                                            Integer.toHexString(
                                                                                text6
                                                                            )
                                                                        }"
                                                                    )
                                                                )
                                                            if (response.isSuccessful) {
                                                                response
                                                                    .body()
                                                                    ?.let {
                                                                        enabled = false
                                                                        println("disabled")
                                                                        job.launch {
                                                                            delay(5000L)
                                                                            enabled = true
                                                                            println("enabled")
                                                                        }
                                                                        ax = it
                                                                        axState = ax
                                                                    }
                                                            }
                                                        } catch (e: Exception) {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Network Error, Try Again",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                            dialogShow = true
                                                        } catch (e: Error) {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Network Error, Try Again",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                            dialogShow = true
                                                        } catch (e: HttpException){
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Network Error, Try Again",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                            dialogShow = true
                                                        }
                                                    }
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

