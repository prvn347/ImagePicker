package com.example.imagepicker

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.imagepicker.ui.theme.ImagePickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                  var imageUri by remember { mutableStateOf<Uri?>(null) }
                    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

                    val singlephotopicker = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { uri ->
                            imageUri = uri
                        }
                    )
                    val multiplephotopicker = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickMultipleVisualMedia(),
                        onResult = { uris ->
                            imageUris = uris
                        }
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Button(onClick = { singlephotopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                                    Text(text = "Pick single image")

                                }
                                Button(onClick = { multiplephotopicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                                    Text(text = "Pick multiple images")

                                }

                            }
                        }

                        item {
                            AsyncImage(model = imageUri, contentDescription =null,modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
                        }
                        items(imageUris) { uri ->
                            AsyncImage(model = uri, contentDescription =null,modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop)
                        }
                    }

                }
            }
        }
    }
}

