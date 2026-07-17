package com.example.quickaid.ui.theme.screens

import android.util.Log
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import com.example.quickaid.models.Ambulance
import com.example.quickaid.repositry.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.quickaid.service.LocationService
@Composable
fun ProfileScreen(
    navController: NavController
) {

    var isEditing by remember { mutableStateOf(false) }
    var active by remember { mutableStateOf(true) }
    val repository = AuthRepository()
    val scope = rememberCoroutineScope()
    var driverName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var driverLicense by remember { mutableStateOf("") }
    var vehicleReg by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var equipment by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }

    var ambulance by remember {
        mutableStateOf<Ambulance?>(null)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {

        try {

            ambulance = withContext(Dispatchers.IO) {
                repository.getProfile()
            }
            driverName = ambulance?.driver_name ?: ""
            phone = ambulance?.driver_phone ?: ""
            driverLicense = ambulance?.driver_license ?: ""
            vehicleReg = ambulance?.vehicle_reg ?: ""
            vehicleType = ambulance?.vehicle_type ?: ""
            equipment = ambulance?.equipment ?: ""
            zone = ambulance?.zone ?: ""

            active = ambulance?.is_active ?: false

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Profile",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            FloatingActionButton(
                onClick = {

                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 45.dp, y = 45.dp)
                    .size(48.dp)
            ) {
                Icon(Icons.Default.Edit, null)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = driverName,
            onValueChange = {
                if (isEditing) driverName = it
            },
            readOnly = !isEditing,
            label = { Text("Driver Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                if (isEditing) phone = it
            },
            readOnly = !isEditing,
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = driverLicense,
            onValueChange = {
                if (isEditing) driverLicense = it
            },
            readOnly = !isEditing,
            label = { Text("Driver License") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = vehicleReg,
            onValueChange = {
                if (isEditing) vehicleReg = it
            },
            readOnly = !isEditing,
            label = { Text("Vehicle Registration") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = vehicleType,
            onValueChange = {
                if (isEditing) vehicleType = it
            },
            readOnly = !isEditing,
            label = { Text("Vehicle Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = equipment,
            onValueChange = {
                if (isEditing) equipment = it
            },
            readOnly = !isEditing,
            label = { Text("Equipment") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = zone,
            onValueChange = {
                if (isEditing) zone = it
            },
            readOnly = !isEditing,
            label = { Text("Zone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Active Status")

                Switch(
                    checked = active,
                    onCheckedChange = {

                        if (isEditing) {

                            active = it

                            if (it) {

                                Log.d("QuickAid", "Starting Location Service")

                                ContextCompat.startForegroundService(
                                    context,
                                    Intent(context, LocationService::class.java)
                                )

                            } else {

                                context.stopService(
                                    Intent(context, LocationService::class.java)
                                )

                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                if (!isEditing) {
                    isEditing = true
                }else {

                    scope.launch {

                        try {

                            withContext(Dispatchers.IO) {
                                repository.updateProfile(
                                    driverName,
                                    phone,
                                    driverLicense,
                                    vehicleReg,
                                    vehicleType,
                                    equipment,
                                    zone,
                                    active
                                )
                            }

                            isEditing = false

                        }catch (e: Exception) {
                            Log.e("QuickAid", "Save button failed", e)
                        }
                    }
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(Icons.Default.Edit, null)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                if (isEditing)
                    "Save Changes"
                else
                    "Edit Profile"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Edit, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout")
        }

    } }
