package com.serenapascual.michaelangelowes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serenapascual.michaelangelowes.ui.theme.MichaelangeLowesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MichaelangeLowesTheme {
                MichaelangeLowesApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MichaelangeLowesApp(modifier: Modifier = Modifier) {
    var shouldShowLanding by rememberSaveable { mutableStateOf(true) }
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowLanding) {
            LandingScreen(onCameraClicked = { shouldShowLanding = false },
                onGalleryClicked = { shouldShowLanding = false })
        } else {
            PhotoScreen(modifier)
        }
    }
}

@Composable
fun LandingScreen(
    onCameraClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = modifier.width(250.dp).padding(vertical = 24.dp),
            text = "To begin, take a new photo or select one from your gallery.",
            textAlign = TextAlign.Center)
        CameraButton(modifier = modifier.padding(vertical = 8.dp), onCameraClicked)
        GalleryButton(modifier = modifier.padding(vertical = 8.dp), onGalleryClicked)
    }
}

@Composable
fun CameraButton(modifier: Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = { Icon(Filled.AddAPhoto, "Camera") },
        text = { Text("New photo") }
    )

}

@Composable
fun GalleryButton(modifier: Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = { Icon(Filled.PhotoLibrary, "Gallery") },
        text = { Text("Existing photo") }
    )

}

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is the next screen")
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun PhotoPreview() {
    MichaelangeLowesTheme {
        PhotoScreen()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    MichaelangeLowesTheme {
        LandingScreen(onCameraClicked = {}, onGalleryClicked = {})
    }
}

@Preview
@Composable
fun MichaelangeLowesAppPreview() {
    MichaelangeLowesTheme {
        MichaelangeLowesApp()
    }
}