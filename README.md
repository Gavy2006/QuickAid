# 🚑 QuickAid - Emergency Ambulance Driver Application

QuickAid is an Android application built using **Kotlin**, **Jetpack Compose**, and **Supabase** to assist ambulance drivers in managing emergency requests, sharing live location, and maintaining driver information in real time.

The application is designed with a modern Android architecture and focuses on fast, reliable, and real-time communication between emergency responders and the backend.

---

## 📱 Features

### Authentication
- Driver Sign Up
- Driver Login
- Secure Session Management
- Logout

### Driver Profile
- View Driver Details
- Update Profile Information
- Upload Profile Photo
- Persistent Profile Image using Supabase Storage

### Live Location
- Foreground Location Service
- Continuous GPS Tracking
- Real-Time Location Updates
- Driver Availability Status

### Backend Integration
- Supabase Authentication
- Supabase Database
- Supabase Storage
- Real-Time Data Synchronization

### Upcoming Features
- Emergency Request Notifications
- Accept / Reject Emergency Requests
- Live Request Monitoring
- Google Maps Navigation
- Trip Status Management
- Ride History

---

# 🛠 Tech Stack

### Language
- Kotlin

### UI
- Jetpack Compose
- Material 3

### Backend
- Supabase
  - Authentication
  - PostgreSQL Database
  - Storage

### Architecture
- Repository Pattern
- Kotlin Coroutines
- State Management with Compose

### Android APIs
- Fused Location Provider
- Foreground Services
- Runtime Permissions
- Activity Result APIs

### Libraries
- Coil
- Ktor Client
- Kotlinx Serialization

---

# 📂 Project Structure

```
app
│
├── models
├── navigation
├── repository
├── service
├── supabase
├── ui
│   ├── screens
│   ├── components
│   └── theme
└── utils
```

---

# 🚀 Current Progress

| Feature | Status |
|----------|--------|
| Driver Authentication | ✅ Completed |
| Profile Management | ✅ Completed |
| Profile Image Upload | ✅ Completed |
| Supabase Storage | ✅ Completed |
| Live GPS Updates | ✅ Completed |
| Foreground Location Service | ✅ Completed |
| Driver Availability | ✅ Completed |
| Emergency Request Flow | 🚧 In Progress |
| Google Maps Navigation | ⏳ Planned |

---

# 📸 Screenshots

> Screenshots will be added soon.

---

# ⚙️ Installation

### Clone the repository

```bash
git clone https://github.com/Gavy2006/QuickAid.git
```

### Open the project

Open the project in **Android Studio**.

### Configure Supabase

Add your own:

- Supabase URL
- Supabase Anon Key

inside your configuration file.

### Run the application

Build and run the application on an Android device or emulator.

---

# 🎯 Project Goal

QuickAid aims to simplify emergency ambulance operations by providing drivers with:

- Real-time emergency request handling
- Live GPS tracking
- Reliable backend synchronization
- Modern Android user experience
- Fast and secure communication

---

# 📈 Future Improvements

- Push Notifications
- Google Maps Route Navigation
- Driver Analytics
- Offline Location Caching
- Multi-language Support
- SOS Status Updates
- Emergency Trip History
- Driver Performance Dashboard

---

# 👨‍💻 Author

**Gavy**

- GitHub: https://github.com/Gavy2006

---

⭐ If you found this project useful, consider giving it a star on GitHub.
