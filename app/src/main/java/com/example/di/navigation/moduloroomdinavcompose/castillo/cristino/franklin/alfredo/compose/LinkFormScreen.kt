package com.example.di.navigation.moduloroomdinavcompose.castillo.cristino.franklin.alfredo.compose

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di.navigation.moduloroomdinavcompose.castillo.cristino.franklin.alfredo.googlelogin.model.UserData
import com.example.di.navigation.moduloroomdinavcompose.compose.ImageFromUrlCoil
import com.example.di.navigation.moduloroomdinavcompose.compose.lab4CrashApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun LinkFormScreen(
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
        var nombre by remember { mutableStateOf("") }

        //Mutable texto
        var zelda by remember { mutableStateOf("") }
        //Mutable texto

        var cancion by remember { mutableStateOf("") }

        //auto
        var username by remember { mutableStateOf("") }

        var imageUrl by remember { mutableStateOf("") }

        //obtenemos imagen usuario google
        if(userData?.profilePictureUrl != null) {

            //actualizamos mutable
            imageUrl = userData.profilePictureUrl
        }
        //obtenemos usuario de google
        if(userData?.username != null) {

            //actualizamos mutable
            username = userData.username

            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(15.dp))
        }

        Button(onClick = onSignOut) {
            Text(text = "Cerrar Sesion")
        }

        Divider()


        Spacer(modifier = Modifier.height(15.dp))

        // FORMULARIO

        // Nombre
        TextField(
            value = nombre,
            onValueChange = { newInput -> nombre = newInput },
            label = { Text("Ingrese su Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Zelda Favorito
        TextField(
            value = zelda,
            onValueChange = { newInput -> zelda = newInput },
            label = { Text("Juego de Zelda Favorito") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Cancion Favorita
        TextField(
            value = cancion,
            onValueChange = { newInput -> cancion = newInput },
            label = { Text("Cancion Imagine Dragons Favorita") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(onClick = { insertLinkFormFirestore(context, nombre, zelda, cancion, username, imageUrl)  }) {
            Text(text = "Insertar Dato | FireStore")
        }
    }
}


fun insertLinkFormFirestore(context: Context, nombre: String, zelda: String, cancion: String, username: String, imageUrl: String){

    if(nombre == "" || zelda == "" || cancion == ""){
        Toast.makeText(
            context,
            "No ha ingresado el texto a guardar.",
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
        "nombre" to nombre, //nombre Input
        "zelda" to zelda, //zelda Input
        "cancion" to cancion, //cancion Input

    )


    // Add a new document with a generated ID
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            Toast.makeText(
                context,
                "Registro Creado Con Exito.",
                Toast.LENGTH_LONG
            ).show()
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
            Toast.makeText(
                context,
                "Error al crear.",
                Toast.LENGTH_LONG
            ).show()
        }
}