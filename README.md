# Printology Android App

Aplikasi Android untuk Printology https://www.printology.my.id/

## Fitur

- ✅ **Navigasi Bottom Navigation**: 6 menu responsif untuk mobile
- ✅ **AI Chat Assistant**: Gemini AI dengan prompt lengkap seperti website
- ✅ **Contact Form**: EmailJS integration untuk kirim pesan
- ✅ **Firebase Integration**: Push notifications & analytics lengkap
- ✅ **Glass Theme UI**: Desain modern dengan efek kaca
- ✅ **Responsive Design**: Dioptimalkan untuk tampilan mobile

## Struktur Project

```
App-Printology/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/printology/app/
│   │   │   ├── MainActivity.kt
│   │   │   ├── PrintologyApplication.kt
│   │   │   ├── services/
│   │   │   │   └── FCMService.kt
│   │   │   └── ui/
│   │   │       ├── components/
│   │   │       │   └── BottomNavigation.kt
│   │   │       ├── screens/
│   │   │       │   ├── HomeScreen.kt
│   │   │       │   ├── ServicesScreen.kt
│   │   │       │   ├── PromoScreen.kt
│   │   │       │   ├── AboutScreen.kt
│   │   │       │   ├── ContactScreen.kt
│   │   │       │   └── AIChatScreen.kt
│   │   │       └── theme/
│   │   │           ├── Color.kt
│   │   │           ├── Theme.kt
│   │   │           └── Type.kt
│   │   └── res/
│   │       ├── drawable/
│   │       ├── layout/
│   │       └── values/
│   │           └── strings.xml
│   └── build.gradle
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md
```

## Setup

1. **Clone atau download project ini**

2. **Buka di Android Studio**
   - Import project dari folder `App-Printology`

3. **Konfigurasi Firebase**
   - File `google-services.json` sudah tersedia di `app/` directory
   - Konfigurasi Firebase lengkap sesuai dengan website (termasuk Admin SDK keys)

4. **Konfigurasi API Keys**
   - Semua konfigurasi sudah tersedia di `app/src/main/res/values/config.xml`
   - Menggunakan keys yang sama dengan website (.env)

5. **Build dan Run**
   - Connect device Android atau gunakan emulator
   - Klik Run di Android Studio

## Firebase Configuration

Aplikasi ini menggunakan konfigurasi Firebase lengkap yang sama dengan website:

### **Firebase Client (Android)**
- ✅ API Key, Auth Domain, Database URL
- ✅ Project ID, Storage Bucket
- ✅ Messaging Sender ID, App ID
- ✅ Measurement ID

### **Firebase Admin SDK (Server)**
- ✅ Private Key ID & Private Key
- ✅ Client Email & Client ID
- ✅ Certificate URL

### **FCM (Push Notifications)**
- ✅ VAPID Key untuk web push
- ✅ FCM Service siap digunakan

### **EmailJS Integration**
- ✅ Service ID, Public Key
- ✅ Template IDs (Admin & Customer)
- ✅ Business Email configuration

### **Gemini AI Integration**
- ✅ Full prompt dari website TypeScript
- ✅ Rate limiting & fallback models
- ✅ Error handling yang robust
- ✅ Real-time chat experience

### **Glass Theme UI**
- ✅ Gradient backgrounds
- ✅ Translucent surfaces
- ✅ Shadow effects
- ✅ Modern mobile-first design

### **Push Notifications**
- ✅ FCM integration lengkap
- ✅ Real-time notifications dari web
- ✅ Custom notification types (order, promo, service)
- ✅ Notification channels & priority

### **Contact Form**
- ✅ UI persis seperti website
- ✅ Real-time clock Indonesia Barat
- ✅ Contact info grid layout
- ✅ Alternative contact buttons
- ✅ EmailJS integration

### **Google Material Icons**
- ✅ Menggunakan Google Material Icons dari fonts.google.com
- ✅ Icon states: Filled (selected) & Outlined (normal)
- ✅ Consistent iconography across all screens

## Dependencies

- **Jetpack Compose**: UI modern untuk Android
- **Navigation Compose**: Navigasi antar screen
- **Firebase**: Analytics dan Messaging
- **OkHttp**: HTTP client untuk API calls
- **Coil**: Image loading
- **Hilt**: Dependency injection

## Screen Overview

### Home Screen
- Header dengan branding Printology
- Welcome message
- Quick access ke fitur utama

### Services Screen
- List semua layanan cetak
- Print Dokumen, Foto, Stiker, dll.

### Promo Screen
- Informasi promo dan diskon
- Banner promosi

### About Screen
- Informasi tentang Printology
- Visi dan misi

### Contact Screen
- Form kontak dengan validasi
- Informasi lokasi dan kontak
- Integrasi EmailJS untuk pengiriman email

### AI Chat Screen
- Chat interface dengan AI Assistant
- Integrasi Google AI API
- Responsive chat bubbles

## Integrasi Backend

Aplikasi ini menggunakan konfigurasi dari `.env` website:

- **EmailJS**: Untuk form kontak
- **Firebase**: Analytics dan push notifications
- **Google AI**: Untuk AI chat
- **Server Notifications**: Custom notification server

## Build APK

Untuk build APK release:

1. Buka Build > Generate Signed Bundle/APK
2. Pilih APK
3. Configure signing key (atau buat baru)
4. Pilih build variant Release
5. Build APK

## Testing

- Unit tests: `./gradlew test`
- Instrumentation tests: `./gradlew connectedAndroidTest`
- UI tests dengan Espresso

## Contributing

1. Fork repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
