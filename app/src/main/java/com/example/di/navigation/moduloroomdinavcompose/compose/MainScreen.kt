package com.example.di.navigation.moduloroomdinavcompose.compose

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.di.navigation.moduloroomdinavcompose.castillo.cristino.franklin.alfredo.googlelogin.model.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //contexto app
        val context = LocalContext.current

        //Mutable texto
        var message by remember { mutableStateOf("") }

        var username by remember { mutableStateOf("") }

        var imageUrl by remember { mutableStateOf("") }


        if(userData?.profilePictureUrl != null) {

            //actualizamos mutable
            imageUrl = userData.profilePictureUrl

            ImageFromUrlCoil(userData.profilePictureUrl)
            //ImageFromUrlCoil("https://i.blogs.es/3194cd/one-piece/500_333.jpeg")

            /*
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )*/
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.username != null) {
            Log.d("userData", "Datos: $userData")

            //actualizamos mutable
            username = userData.username

            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onSignOut) {
            Text(text = "Cerrar Sesion")
        }

        Divider()

        Button(onClick = { lab4CrashApp()  }) {
            Text(text = "Provocar Crash | Crashlytics")
        }

        Divider()

        TextField(
            value = message,
            onValueChange = { newText -> message = newText },
            label = { Text("Ingrese texto a Guardar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(onClick = { insertDataFireStore(context, message, username, imageUrl)  }) {
            Text(text = "Insertar Dato | FireStore")
        }
    }
}


fun insertDataFireStore(context: Context, message: String, username: String, imageUrl: String){

    if(message == ""){
        Toast.makeText(
            context,
            "No ha ingresado texto a guardar.",
            Toast.LENGTH_LONG
        ).show()

        return
    }

    val db = Firebase.firestore

    val user = hashMapOf(
        "dev" to "Franklin Castillo - CC101020",
        "born" to 2001,
        "username" to username, //username de google
        "imageUrl" to imageUrl, //image google profile
        "message" to message,

    )


    // Add a new document with a generated ID
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            Toast.makeText(
                context,
                "Registro Creado Con Exito.",
                Toast.LENGTH_LONG
            ).show()
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
            Toast.makeText(
                context,
                "Error al crear.",
                Toast.LENGTH_LONG
            ).show()
        }
}

@Composable
fun ImageFromUrlCoil(url: String) {
    Box(
        //modifier = Modifier
            //.background(color = Color.White) // Puedes ajustar el color aquí
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "Imagen",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp, 70.dp)
                .clip(CircleShape),
            onError = { throwable ->
                Log.e("ImageLoading", "Error loading image: ${throwable.toString()}")
            }

        )

    }
}