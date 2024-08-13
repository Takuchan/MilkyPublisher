import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.ui.Screens.CameraPreview
import com.takuchan.milkypublisher.ui.Screens.PreviewScreenSize
import java.util.concurrent.Executors

@Composable
fun PublisherScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(isExpanded, label = "expand_transition")
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(PreviewScreenSize(0f,0f)) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(transition.animateFloat(label = "weight") { if (it) 1f else 0.75f }.value)
                .padding(16.dp)
                .background(Color.LightGray, RoundedCornerShape(30.dp))
                .onGloballyPositioned { coordinates->
                    boxSize = PreviewScreenSize(
                        coordinates.size.width / density.density,
                        coordinates.size.height /density.density
                    )
                }
        ) {

            CameraPreview(executor = cameraExecutor, screenSize = boxSize)
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Shrink" else "Expand"
                )
            }
        }

        AnimatedVisibility(
            visible = !isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            OptionButtons()
        }
    }
}

@Composable
fun OptionButtons() {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        for (i in 1..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OptionButton(
                    text = "Option ${2*i-1}",
                    isSelected = selectedOption == 2*i-1,
                    onClick = { selectedOption = 2*i-1 }
                )
                OptionButton(
                    text = "Option ${2*i}",
                    isSelected = selectedOption == 2*i,
                    onClick = { selectedOption = 2*i }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun OptionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(150.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF6200EE) else Color(0xFFBB86FC),
            contentColor = if (isSelected) Color.White else Color.Black
        )
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun PublisherScreenPreview() {
    PublisherScreen()
}