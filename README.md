# Printology Android App

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Gemini AI](https://img.shields.io/badge/Gemini_AI-4285F4?style=for-the-badge&logo=google&logoColor=white)

Aplikasi Android modern untuk **Printology** - solusi percetakan digital terdepan

[📱 Features](#features) • [🚀 Quick Start](#quick-start) • [🏗️ Architecture](#architecture) • [🔧 Setup](#setup)

</div>

## 🎯 Overview

Aplikasi Android native yang menghadirkan pengalaman mobile terbaik untuk platform Printology, dengan integrasi teknologi terkini dan desain UI/UX modern.

## ✨ Features

### 🎨 **User Experience**
- ✅ **Glass Theme UI** - Desain modern dengan efek kaca dan gradient
- ✅ **Bottom Navigation** - 6 menu responsif dengan smooth animation
- ✅ **Responsive Design** - Dioptimalkan untuk semua ukuran layar mobile

### 🤖 **AI & Automation**
- ✅ **Gemini AI Assistant** - Chatbot cerdas dengan prompt lengkap
- ✅ **Smart Responses** - Konteks percetakan dan desain grafis
- ✅ **Real-time Chat** - Pengalaman obrolan yang natural

### 📧 **Communication**
- ✅ **EmailJS Integration** - Sistem pengiriman email otomatis
- ✅ **Contact Form** - Formulir kontak dengan validasi real-time
- ✅ **Multi-template** - Template untuk admin & customer

### 🔥 **Backend & Cloud**
- ✅ **Firebase Integration** - Analytics, Crashlytics & Performance
- ✅ **FCM Push Notifications** - Notifikasi real-time
- ✅ **Google Services** - Integrasi lengkap dengan ecosystem Google

## 🏗️ Architecture

### **Tech Stack**
```kotlin
// Core Framework
- Kotlin + Jetpack Compose
- Material Design 3
- Clean Architecture

// Backend & APIs
- Firebase (Auth, Firestore, Storage, Messaging)
- Google AI Gemini API
- EmailJS REST API
- OkHttp + Retrofit

// Additional Libraries
- Coil (Image Loading)
- Hilt (Dependency Injection)
- Navigation Compose
- LiveData & ViewModel
```

### **Project Structure**
```
App-Printology/
├── 🎯 app/
│   ├── src/main/
│   │   ├── 📱 AndroidManifest.xml
│   │   ├── 💻 kotlin/com/printology/
│   │   │   ├── MainActivity.kt
│   │   │   ├── PrintologyApplication.kt
│   │   │   ├── 🏗️ di/ (Dependency Injection)
│   │   │   ├── 🔥 services/
│   │   │   │   ├── FCMService.kt
│   │   │   │   ├── GeminiAIService.kt
│   │   │   │   └── EmailJSService.kt
│   │   │   ├── 📊 data/
│   │   │   │   ├── repositories/
│   │   │   │   ├── models/
│   │   │   │   └── datasources/
│   │   │   ├── 🎨 ui/
│   │   │   │   ├── components/
│   │   │   │   │   ├── BottomNavigation.kt
│   │   │   │   │   ├── GlassCard.kt
│   │   │   │   │   └── ChatBubble.kt
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── ServicesScreen.kt
│   │   │   │   │   ├── PromoScreen.kt
│   │   │   │   │   ├── AboutScreen.kt
│   │   │   │   │   ├── ContactScreen.kt
│   │   │   │   │   └── AIChatScreen.kt
│   │   │   │   └── theme/
│   │   │   │       ├── Color.kt
│   │   │   │       ├── Theme.kt
│   │   │   │       └── Type.kt
│   │   └── 📁 res/
│   │       ├── 🎨 drawable/ (Icons & Graphics)
│   │       ├── 📐 layout/ (XML layouts)
│   │       ├── 🔧 values/
│   │       │   ├── strings.xml
│   │       │   ├── colors.xml
│   │       │   └── config.xml (API Keys)
│   │       └── 🛣️ navigation/ (Nav Graphs)
│   └── build.gradle (Module Dependencies)
├── 📦 build.gradle (Project Config)
├── ⚙️ gradle.properties
├── 🔧 settings.gradle
└── 📋 README.md
```

## 🚀 Quick Start

### **Prerequisites**
- Android Studio Arctic Fox atau newer
- Android SDK 21+
- Java 11 atau Kotlin 1.8+

### **Installation Steps**

1. **Clone Repository**
   ```bash
   git clone https://github.com/printology/android-app.git
   cd App-Printology
   ```

2. **Buka di Android Studio**
   - File → Open → Pilih folder project
   - Tunggu Gradle sync selesai

3. **Konfigurasi Firebase**
   ```bash
   # File google-services.json sudah tersedia di:
   app/google-services.json
   ```

4. **Setup API Keys** (Optional - sudah pre-configured)
   ```xml
   <!-- app/src/main/res/values/config.xml -->
   <resources>
       <string name="gemini_api_key">AIza...YourKey</string>
       <string name="emailjs_public_key">user_...YourKey</string>
       <string name="emailjs_service_id">service_printology</string>
   </resources>
   ```

5. **Build & Run**
   - Connect Android device atau start emulator
   - Klik **Run** (Shift + F10)
   - Tunggu build process selesai

## 🔧 Configuration

### **Firebase Setup**
```kotlin
// PrintologyApplication.kt
class PrintologyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        
        // Enable Firebase services
        FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
```

### **Gemini AI Integration**
```kotlin
// GeminiAIService.kt
class GeminiAIService {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    
    suspend fun generateResponse(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Maaf, saya tidak bisa merespons saat ini."
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }
}
```

### **EmailJS Service**
```kotlin
// EmailJSService.kt
class EmailJSService {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    
    suspend fun sendContactForm(
        name: String,
        email: String,
        message: String
    ): Boolean {
        return try {
            val request = Request.Builder()
                .url("https://api.emailjs.com/api/v1.0/email/send")
                .post(formBody)
                .build()
            
            val response = client.newCall(request).execute()
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
```

## 📱 Screens & Features

### **Home Screen** 🏠
- Branding Printology dengan glass effect
- Welcome message personalized
- Quick access to popular services
- Real-time notifications badge

### **Services Screen** 🛍️
- Grid layout semua layanan cetak
- Print Dokumen, Foto, Stiker, Banner
- Detail pricing & specifications
- One-click order initiation

### **AI Chat Screen** 🤖
```kotlin
// AIChatScreen.kt
@Composable
fun AIChatScreen(
    viewModel: AIChatViewModel = hiltViewModel()
) {
    val messages by viewModel.chatMessages.collectAsState()
    
    LazyColumn {
        items(messages) { message ->
            ChatBubble(
                message = message,
                isUser = message.isFromUser
            )
        }
    }
    
    // Input field dengan send button
    MessageInputField { message ->
        viewModel.sendMessage(message)
    }
}
```

### **Contact Screen** 📞
- Contact form dengan validasi
- Business information grid
- Direct call/email/wa buttons
- Location map integration

### **Promo Screen** 🎉
- Carousel promo banners
- Countdown timer untuk limited offers
- Voucher codes & special deals

## 🔥 Firebase Features

### **Push Notifications**
```kotlin
// FCMService.kt
class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification ->
            showNotification(
                title = notification.title ?: "Printology",
                body = notification.body ?: "Pesan baru"
            )
        }
    }
    
    override fun onNewToken(token: String) {
        // Update token ke server
        updateDeviceToken(token)
    }
}
```

### **Analytics Events**
```kotlin
// Track user interactions
fun trackScreenView(screenName: String) {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
    }
}
```

## 🎨 UI/UX Design

### **Glass Theme System**
```kotlin
// Theme.kt
object PrintologyTheme {
    val GlassColors = glassColorPalette(
        primary = Color(0xFF6C63FF),
        surface = Color(0x80FFFFFF),
        onSurface = Color(0xFF333333)
    )
    
    val Shapes = Shapes(
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(24.dp)
    )
}
```

### **Custom Components**
- `GlassCard()` - Card dengan backdrop blur
- `GradientButton()` - Button dengan gradient animasi
- `ChatBubble()` - Bubble chat dengan tail
- `ServiceGridItem()` - Item layanan dengan icon

## 📦 Dependencies

### **Core Dependencies** (`app/build.gradle`)
```kotlin
dependencies {
    // Kotlin & Coroutines
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    
    // Google AI
    implementation("com.google.ai.client.generativeai:generativeai:0.1.1")
    
    // Networking
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    
    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-compiler:2.46.1")
}
```

## 🛠️ Build & Deploy

### **Build APK Release**
```bash
./gradlew assembleRelease
```

### **Generate Signed Bundle**
1. Build → Generate Signed Bundle/APK
2. Pilih **Android App Bundle**
3. Configure signing key
4. Select build variant: **release**
5. Finish

### **Environment Variables**
```properties
# gradle.properties
ORG_GRADLE_PROJECT_geminiApiKey=your_actual_key
ORG_GRADLE_PROJECT_emailjsPublicKey=your_public_key
```

## 🧪 Testing

### **Unit Tests**
```kotlin
class GeminiAIServiceTest {
    @Test
    fun `test AI response generation`() = runTest {
        val service = GeminiAIService()
        val response = service.generateResponse("Halo")
        assertTrue(response.isNotEmpty())
    }
}
```

### **UI Tests**
```kotlin
@RunWith(AndroidJUnit4::class)
class ContactScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testContactFormSubmission() {
        composeTestRule.setContent {
            PrintologyTheme { ContactScreen() }
        }
        
        composeTestRule.onNodeWithText("Nama").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Kirim Pesan").performClick()
    }
}
```

Run tests dengan:
```bash
./gradlew test          # Unit tests
./gradlew connectedTest # Instrumentation tests
```

## 📊 Performance

### **Optimizations**
- Lazy column untuk list yang panjang
- Image compression dengan Coil
- Network caching strategy
- Minimal APK size dengan ProGuard

### **Monitoring**
- Firebase Performance Monitoring
- Crashlytics untuk error tracking
- Analytics untuk user behavior

## 🤝 Contributing

1. **Fork repository**
2. **Create feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit changes**
   ```bash
   git commit -m 'Add amazing feature'
   ```
4. **Push to branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open Pull Request**

### **Code Style**
- Gunakan Kotlin coding conventions
- Follow Material Design guidelines
- Write comprehensive tests
- Update documentation accordingly

## 📄 License

```text
MIT License
Copyright (c) 2024 Printology

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

## 📞 Support

- **Documentation**: [Printology Docs](https://docs.printology.my.id)
- **Website**: [Printology](https://www.printology.my.id)
- **Email**: support@printology.my.id
- **Issues**: [GitHub Issues](https://github.com/printology/android-app/issues)

---

<div align="center">

**Built with ❤️ using Kotlin, Jetpack Compose & Modern Android Development**

[⬆ Back to top](#printology-android-app)

</div>
