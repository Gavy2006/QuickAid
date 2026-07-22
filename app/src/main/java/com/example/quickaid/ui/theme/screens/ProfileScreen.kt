package com.example.quickaid.ui.theme.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.quickaid.service.LocationService
import com.example.quickaid.ui.theme.navigation.Screen

@Composable
fun ProfileScreen(
    navController: NavController
) {  

    val context = LocalContext.current

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

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->

        if (uri != null) {

            selectedImageUri = uri

            scope.launch {

                try {

                    val imageUrl = withContext(Dispatchers.IO) {
                        repository.uploadProfileImage(context, uri)
                    }

                    withContext(Dispatchers.IO) {
                        repository.updateProfilePhoto(imageUrl)
                    }

                    ambulance = withContext(Dispatchers.IO) {
                        repository.getProfile()
                    }

                } catch (e: Exception) {
                    Log.e("QuickAid", "Image Upload Failed", e)
                }

            }
        }
    }


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
                modifier = Modifier.size(100.dp)
            ) {
                when {
                    selectedImageUri != null -> {

                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    !ambulance?.profile_photo.isNullOrBlank() -> {

                        AsyncImage(
                            model = ambulance?.profile_photo,
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = driverName.firstOrNull()?.uppercase() ?: "D",
                                fontSize = 36.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                SmallFloatingActionButton(
                    onClick = {
                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(6.dp, 6.dp)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
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
                scope.launch {
                    try {

                        withContext(Dispatchers.IO) {
                            repository.signOut()
                        }

                        withContext(Dispatchers.Main) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }

                    } catch (e: Exception) {
                        Log.e("QuickAid", "Logout failed", e)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Logout")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout")
        }

    } }
