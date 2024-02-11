package com.mkrdeveloper.onboardingscreenjetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.harmonywellness.BottomNavigationBar
import com.example.harmonywellness.NavHostContainer
import com.example.harmonywellness.R

@Composable
fun HomeScreen() {

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Home Screen", fontSize = 44.sp)
    }

}
@Composable
fun dekhte() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = " Profile picture",
                contentScale = ContentScale.Fit
            )
        }

        Text(buildAnnotatedString {
            append("Hello, ")
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            ) {
                append("Shubh")
            }
        }, modifier = Modifier.padding(start = 10.dp))

        Spacer(modifier = Modifier.weight(1f))

        BadgedBox(modifier = Modifier.padding(end = 10.dp),
            badge = {
                Badge(
                    Modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .align(Alignment.BottomEnd)
                )
            }) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "notification icon",
                tint = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myApp(){
    // remember navController so it does not
    // get recreated on recomposition
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { dekhte()},
                    modifier = Modifier.padding(horizontal = 18.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                    )
                ) }


            ,
            // Bottom navigation
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }, content = { padding ->
                // Navhost: where screens are placed
                NavHostContainer(navController = navController, padding = padding)
            }
        )
    }

}
