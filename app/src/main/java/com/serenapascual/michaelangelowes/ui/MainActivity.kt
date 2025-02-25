package com.serenapascual.michaelangelowes.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serenapascual.michaelangelowes.R
import com.serenapascual.michaelangelowes.ui.theme.MichaelangeLowesTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        // Registers a photo picker activity launcher in single-select mode.
        pickMediaLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            MichaelangeLowesTheme {
                MichaelangeLowesApp()
            }
        }
    }

    @Composable
    fun MichaelangeLowesApp() {
        // Registers a camera activity launcher.
        // TODO: request permission
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                // TODO: handle result
                if (success) Log.d("Camera", "Successfully took picture")
            }
        )

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "LandingScreen" ){
            composable("LandingScreen"){
                LandingScreen(cameraLauncher =  cameraLauncher,
                    pickMediaLauncher = pickMediaLauncher)
            }
            composable("ImageScreen"){ PhotoScreen() }
        }
    }

    @Composable
    fun LandingScreen(modifier: Modifier = Modifier,
                      cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
                      pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>) {
        val context = LocalContext.current
        val imageUri = createImageUri(context)

        Surface(modifier, color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .width(250.dp)
                        .padding(vertical = 24.dp),
                    text = getString(R.string.select_photo_instructions),
                    textAlign = TextAlign.Center
                )
                CameraButton(modifier = modifier.padding(vertical = 8.dp),
                    onClick = {
                        cameraLauncher.launch(imageUri)
                    })
                GalleryButton(modifier = modifier.padding(vertical = 8.dp),
                    onClick = {
                        pickMediaLauncher.launch(PickVisualMediaRequest.Builder()
                            .setMediaType(PickVisualMedia.ImageOnly)
                            .build()
                        )
                    })
            }
        }
    }

    @Composable
    fun CameraButton(modifier: Modifier, onClick: () -> Unit) {
        ExtendedFloatingActionButton(
            modifier = modifier,
            onClick = onClick,
            icon = { Icon(Filled.AddAPhoto, getString(R.string.camera_button_description)) },
            text = { Text(getString(R.string.camera_button_text)) }
        )
    }

    @Composable
    fun GalleryButton(modifier: Modifier, onClick: () -> Unit) {
        ExtendedFloatingActionButton(
            modifier = modifier,
            onClick = onClick,
            icon = { Icon(Filled.PhotoLibrary, getString(R.string.gallery_button_description)) },
            text = { Text(getString(R.string.gallery_button_text)) }
        )

    }

    @Composable
    fun PhotoScreen(modifier: Modifier = Modifier) {
        Surface(modifier, color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This is the next screen")
            }
        }
    }
}

fun createImageUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
    val imageFile = File.createTempFile(
        "JPEG_${timeStamp}_", //prefix
        ".jpg", //suffix
        context.cacheDir //directory
    )
    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        imageFile
    )
}
