package com.example.myntra.presentatiom.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myntra.comman.customColor
import com.example.myntra.presentatiom.navigation.Routes
import com.example.myntra.presentatiom.viewmodel.ViewModel
import com.google.firebase.auth.FirebaseAuth
import okhttp3.internal.wait

@Composable
fun forgotpasswordScreenUI(
    viewmodel: ViewModel = hiltViewModel(),
    navController: NavHostController, firebaseAuth: FirebaseAuth
) {

    val email = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background Circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFFFAFAF), // Light pink color
                radius = size.width * 0.52f, // Adjust the size
                center = Offset(
                    size.width * 1.1f,
                    size.height * -0.1f
                ) // Upper right corner
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Forgot Password ",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },

                label = { Text(" Enter your registered E-mail") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = customColor,
                    unfocusedBorderColor = customColor,
                )
            )

            Button(
                onClick = {
                    firebaseAuth.sendPasswordResetEmail(email.value).addOnSuccessListener {
                        Toast.makeText(
                            navController.context,
                            "Password reset link sent to your email",
                            Toast.LENGTH_SHORT

                        )
                        navController.navigate(Routes.LoginScreen)
                    }.addOnFailureListener {
                        Toast.makeText(
                            navController.context,
                            "Something went wrong",
                            Toast.LENGTH_SHORT

                        )
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(317.dp),
                colors = ButtonDefaults.buttonColors(containerColor = customColor)
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }
}