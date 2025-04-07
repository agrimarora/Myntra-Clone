package com.example.myntra.presentatiom.screens


import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.comman.customColor
import com.example.myntra.domain.model.Userdata
import com.example.myntra.presentatiom.navigation.Routes

import com.example.myntra.presentatiom.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")

@Composable


fun SignupScreenUI(navController: NavHostController,viewModel: ViewModel = hiltViewModel()) {
    val state = viewModel.signupScreenstate // Observe ViewModel state
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phonenumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val opendialog = remember { mutableStateOf(false) }
    val nav = rememberNavController()

    // Trigger the dialog when success state changes
    LaunchedEffect(state.value.success) {
        if (state.value.success == true) {
            opendialog.value = true
        }
    }

    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (state.value.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Something went wrong")
        }
    } else {
        if (opendialog.value) {
            AlertDialog(
                onDismissRequest = { opendialog.value = false },
                confirmButton = {
                    TextButton(onClick = {

                        nav.navigate(Routes.LoginScreen)
                        opendialog.value = false
                    }) {
                        Text(text = "Log in")
                    }
                },
                text = { Text("Sign-up successful! Please log in.") }
            )
        } else {
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
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Sign up",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Name") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = customColor,
                            unfocusedBorderColor = customColor,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = customColor,
                            unfocusedBorderColor = customColor,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = phonenumber.value,
                        onValueChange = { phonenumber.value = it },
                        label = { Text("Phone Number") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = customColor,
                            unfocusedBorderColor = customColor,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Password") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = customColor,
                            unfocusedBorderColor = customColor,
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val userdata = Userdata(name.value, email.value, phonenumber.value, password.value)
                            viewModel.createUser(userdata)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(317.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF68B8B))
                    ) {
                        Text(text = "Sign Up")
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        Text(text = "Already have an account?", color = Color.DarkGray)
                        Text(
                            text = "Log in",
                            color = customColor,
                            modifier = Modifier.clickable { navController.navigate(Routes.LoginScreen) })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left Line
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color.Black)
                        )

                        // OR Text
                        Text(
                            text = "OR",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        // Right Line
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            // Facebook Icon from Drawable Resource
                            Image(
                                painter = (painterResource(id = R.drawable.facebook)), // Replace with your actual drawable
                                contentDescription = "Facebook Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(48.dp))
                            Text(text = "Log in with Facebook", color = Color.Black)
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        onClick = {},

                        modifier = Modifier
                            .fillMaxWidth()
                            .size(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            // Facebook Icon from Drawable Resource
                            Image(
                                painter = (painterResource(id = R.drawable.google)), // Replace with your actual drawable
                                contentDescription = "Facebook Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(48.dp))
                            Text(text = "Log in with Google", color = Color.Black)
                        }
                    }
                }



                }
            }
        }
    }


