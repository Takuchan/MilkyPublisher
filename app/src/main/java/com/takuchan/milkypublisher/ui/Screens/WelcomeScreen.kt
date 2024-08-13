package com.takuchan.milkypublisher.Compose.Welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    onWelcomeComplete: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()

    val pageState = rememberPagerState(pageCount = {4})
    HorizontalPager(state = pageState) { page ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(page){
                0 -> WelcomePage(
                    subtitle = stringResource(id = R.string.welcome_subtitle1),
                    onWelcomeComplete = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(1)
                        }
                    }
                )
                1 -> WelcomePage(
                    subtitle = stringResource(id = R.string.welcome_subtitle2),
                    onWelcomeComplete = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(2)
                        }
                    }
                )
                2 -> WelcomePage(
                    subtitle = stringResource(id = R.string.welcome_subtitle3),
                    onWelcomeComplete = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(3)
                        }
                    }
                )
                3 -> WelcomePage(
                    subtitle = stringResource(id = R.string.welcome_subtitle4),
                    onWelcomeComplete = onWelcomeComplete
                )
            }
        }

    }

}
@Composable
fun WelcomePage(
    subtitle:String,
    onWelcomeComplete: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_title),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onWelcomeComplete
        ) {
            Text("Next")
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        WelcomeScreen(
            onWelcomeComplete = {}
        )

    }
}