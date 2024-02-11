package com.mkrdeveloper.onboardingscreenjetpack

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.harmonywellness.MainActivity
import com.example.harmonywellness.R
import com.example.harmonywellness.TextFieldWithIcons
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController, context: MainActivity) {
    val animations = listOf(
        R.raw.intro1,
        R.raw.profile,
        R.raw.intro3
    )
    val titles = listOf(
        "Welcome to AyurLife",
        "Your Name",
        "Great"
    )

    val descriptions = listOf(
        "Discover unbeatable deals on air travel to destinations around the globe.",
        "\"The greatest wealth is health.\" - Virgil",
        "Your health is an investment, not an expense."
    )
    val pagerState = rememberPagerState(
        pageCount = animations.size
    )


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            Modifier.wrapContentSize()
        ) { currentPage ->
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animations[currentPage]))
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(350.dp)
                )
                Text(
                    text = titles[currentPage],
                    textAlign = TextAlign.Center,
                    fontSize = 44.sp,
                    lineHeight = 50.sp,
                    fontWeight = FontWeight.Bold
                )
                if(currentPage==0) {
                    Text(
                        text = "Your Guide to Good Health",
                        Modifier.padding(top = 45.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp

                    )
                }
                else if(currentPage==1){
                    Text(
                        text = descriptions[currentPage],
                        Modifier.padding(top = 45.dp).padding(10.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,


                    )
                    TextFieldWithIcons(s = "Name")
                }
                else{
                    Text(
                        text = descriptions[currentPage],
                        Modifier.padding(top = 45.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp

                    )
                }
            }
        }

        PageIndicator(
            pageCount = animations.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(60.dp)
        )
    }

    ButtonsSection(
        pagerState = pagerState,
        navController = navController,
        context = context
    )

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ButtonsSection(pagerState: PagerState, navController: NavHostController, context: MainActivity) {

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)){
        if (pagerState.currentPage != 2){
            Text(text = "Next",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clickable {

                    scope.launch {
                        val nextPage = pagerState.currentPage + 1
                        pagerState.scrollToPage(nextPage)
                    }
                },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(text = "Back",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .clickable {
                        scope.launch {
                            val prevPage = pagerState.currentPage -1
                            if (prevPage >= 0){
                                pagerState.scrollToPage(prevPage)
                            }
                        }
                    },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }else{
            OutlinedButton(onClick = {

                onBoardingIsFinished(context = context)
                navController.popBackStack()
                    navController.navigate("Home")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            , colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x25E92F1E)
            )
                ) {
                Text(
                    text = "Get Started",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorSingleDot(isSelected = it == currentPage )
        }


    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {

    val width = animateDpAsState(targetValue = if (isSelected) 35.dp else 15.dp, label = "")
    Box(modifier = Modifier
        .padding(2.dp)
        .height(15.dp)
        .width(width.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xFFE92F1E) else Color(0x25E92F1E))
    )
}

private fun onBoardingIsFinished(context: MainActivity) {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
   val editor = sharedPreferences.edit()
    editor.putBoolean("isFinished", true)
    editor.apply()

}


