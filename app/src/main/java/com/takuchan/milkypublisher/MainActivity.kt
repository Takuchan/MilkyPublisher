package com.takuchan.milkypublisher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.Compose.Welcome.WelcomeScreen
import com.takuchan.milkypublisher.Repository.Welcome.WelcomeRepository
import com.takuchan.milkypublisher.ViewModel.Welcome.WelcomeViewModel
import com.takuchan.milkypublisher.ui.Components.MilkyPublisherNavHost
import com.takuchan.milkypublisher.ui.theme.MilkyPublisherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val welcomeRepository = remember { WelcomeRepository(context) }
            val welcomeViewModel = remember { WelcomeViewModel(welcomeRepository) }

            val isFirstLaunch by welcomeViewModel.isFirstLaunch.collectAsState()

            MilkyPublisherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    if(isFirstLaunch) {
                         WelcomeScreen(
                             onWelcomeComplete = {
                                 welcomeViewModel.onWelcomeComplete()
                             }
                         )
                    } else {
                        MilkyPublisherNavHost()
                    }
                }
            }
        }
    }

    @Composable
    fun ConnectionStatusButton() {
        OutlinedButton(
            onClick = { /* ボタンクリック時の処理 */ },
            modifier = Modifier
                .padding(end = 8.dp)
                .widthIn(max = 150.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning Icon",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Not connected".take(10),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
