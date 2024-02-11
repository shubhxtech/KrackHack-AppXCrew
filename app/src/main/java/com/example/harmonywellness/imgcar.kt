package com.example.harmonywellness

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CarouselItem(val imageRes: Int)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselBanner(images: List<CarouselItem>, autoSlideInterval: Long = 3000L) {
    var currentPage by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(autoSlideInterval) {
        // Launch a coroutine to automatically switch pages at the specified interval
        while (true) {
            delay(autoSlideInterval)
            coroutineScope.launch {
                currentPage = (currentPage + 1) % images.size
                pagerState.animateScrollToPage(currentPage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Adjust the height as needed
            .padding(10.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) { page ->
            Image(
                painter = painterResource(id = images[page].imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }

        // Display dots for each page
        DotsIndicator(
            pageCount = images.size,
            currentPage = currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun DotsIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Dot(
                isSelected = index == currentPage,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun Dot(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarouselBannerPreview() {
    val carouselItems = listOf(
        CarouselItem(imageRes = R.drawable.sc1),
        CarouselItem(imageRes = R.drawable.sc2),
        CarouselItem(imageRes = R.drawable.sc3),
        CarouselItem(imageRes = R.drawable.sc4),
    )


        CarouselBanner(images = carouselItems)

}


