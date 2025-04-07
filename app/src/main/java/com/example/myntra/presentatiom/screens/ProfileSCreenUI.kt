package com.example.myntra.presentatiom.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myntra.comman.customColor
import com.example.myntra.presentatiom.navigation.SubNavigation
import com.example.myntra.presentatiom.viewmodel.ViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileSCreenUI(viewModel: ViewModel = hiltViewModel(), firebaseAuth: FirebaseAuth) {
    val state = viewModel.ProfileScreenState
    var name = remember { mutableStateOf(state.value.userdata?.Userdata?.name) }
    var phonenuber = remember { mutableStateOf(state.value.userdata?.Userdata?.phonenumber) }
    var email = remember { mutableStateOf(state.value.userdata?.Userdata?.email) }
    val text= remember { mutableStateOf("Edit Profile") }
    LaunchedEffect(key1 = true) {
        viewModel.getUserbyuid(firebaseAuth.currentUser!!.uid.toString())
        name.value =  state.value.userdata?.Userdata?.name
        phonenuber.value =  state.value.userdata?.Userdata?.phonenumber
        email.value =  state.value.userdata?.Userdata?.email

    }
    var readonly = remember { mutableStateOf(true) }

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
            drawCircle(
                color = Color(0xFFFFAFAF), // Light pink color
                radius = size.width * 0.5f, // Adjust size
                center = Offset(
                    size.width * -0.2f,
                    size.height * 1.05f
                ) // Bottom left corner
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text("Profile")
            Spacer(modifier = Modifier.height(10.dp))
            name.value?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { name.value = it },  // ✅ Correctly updates the name variable
                    readOnly = readonly.value
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            email.value?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { email.value = it },  // ✅ Correctly updates the name variable
                    readOnly = readonly.value
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            phonenuber.value?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { phonenuber.value = it },  // ✅ Correctly updates the name variable
                    readOnly = readonly.value
                )
            }

            Button(onClick = {
                firebaseAuth.signOut()



            }, modifier = Modifier.fillMaxWidth().padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(customColor.value))
            ) {
                Text("Log Out")

            }
            Button(
                onClick = {
                    readonly.value = !readonly.value
                    if (readonly.value) {
                        viewModel.updateUserData(
                            userdataparent = state.value.userdata!!.copy(
                                Userdata = state.value.userdata!!.Userdata?.copy(
                                    name = name.value,
                                    phonenumber = phonenuber.value,
                                    email = email.value
                                )
                            )
                        )
                        text.value = "Edit Profile"
                    } else {
                        text.value = "Save Profile"
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(1.dp, Color(0xFFF68B8B), shape = RoundedCornerShape(30.dp)),

                colors = ButtonDefaults.buttonColors(containerColor = Color(Color.White.toArgb()))
            ) {
                Text(text.value, color = Color.Black)
            }



        }
    }}
