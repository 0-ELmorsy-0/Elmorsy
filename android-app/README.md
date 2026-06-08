# Nursing Admin - Android App

تطبيق Android احترافي لإدارة منصة خدمات التمريض المنزلي، مبني باستخدام Kotlin و Jetpack Compose.

## المميزات

✅ **واجهة حديثة**
- Jetpack Compose UI
- Material Design 3
- Dark Mode Support
- Responsive Design

✅ **المصادقة والأمان**
- JWT Token Authentication
- Secure Token Storage (DataStore)
- Automatic Token Refresh

✅ **الميزات الرئيسية**
- تسجيل دخول آمن
- لوحة تحكم شاملة
- إدارة المستخدمين
- إدارة الطلبات
- تتبع المدفوعات
- التحليلات والإحصائيات

## البنية

```
android-app/
├── src/main/
│   ├── kotlin/com/nursing/admin/mobile/
│   │   ├── api/                    # API Services
│   │   │   ├── ApiClient.kt
│   │   │   └── AuthService.kt
│   │   ├── data/
│   │   │   ├── models/             # Data Models
│   │   │   └── repository/         # Repositories
│   │   ├── ui/
│   │   │   ├── screens/            # Compose Screens
│   │   │   ├── theme/              # Theme Configuration
│   │   │   └── viewmodel/          # ViewModels
│   │   ├── utils/                  # Utilities
│   │   └── MainActivity.kt
│   ├── res/
│   │   ├── values/                 # String resources
│   │   └── mipmap/                 # App icons
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## المتطلبات

- Android Studio Flamingo أو أحدث
- Android SDK 24+
- Kotlin 1.9.22+
- Java 17+

## التثبيت

1. **فتح المشروع**
```bash
cd android-app
```

2. **تحديث API URL** (إذا لزم الأمر)
```kotlin
// في ApiClient.kt
private const val BASE_URL = "http://your-server-ip:8080/api/"
```

3. **بناء التطبيق**
```bash
./gradlew build
```

4. **تشغيل التطبيق**
- اختر جهاز أو محاكي
- اضغط Run

## الشاشات الرئيسية

### 1. Login Screen
- تسجيل دخول آمن
- معالجة الأخطاء
- Demo Credentials

### 2. Dashboard Screen
- عرض الإحصائيات
- بطاقات سريعة الوصول
- قوائم الإجراءات السريعة

## API Integration

التطبيق يتصل بـ Backend الذي يعمل على:
```
http://localhost:8080/api/
```

### Endpoints المستخدمة

```
POST   /auth/login              - تسجيل الدخول
GET    /patients               - قائمة المرضى
GET    /nurses                 - قائمة الممرضات
GET    /orders                 - قائمة الطلبات
GET    /payments               - المدفوعات
GET    /analytics/dashboard    - التحليلات
```

## State Management

يستخدم التطبيق:
- **ViewModel** - لإدارة حالة الشاشات
- **StateFlow** - للبيانات المتفاعلة
- **DataStore** - لتخزين البيانات المحلية

## الأمان

✅ **JWT Authentication**
- توليد Tokens من الخادم
- تخزين آمن في DataStore
- Automatic Token Refresh

✅ **HTTPS Ready**
- SSL/TLS Support
- Certificate Pinning (اختياري)

✅ **Input Validation**
- Email Validation
- Password Requirements
- Error Handling

## الاختبار

### Test Credentials
```
Email: admin@example.com
Password: password123
```

### Emulator Configuration
```
API Level: 24+
RAM: 2GB+
Storage: 500MB+
```

## الأداء

✅ **Optimization**
- Lazy Loading
- Image Caching
- Efficient State Management
- Minimal Recomposition

✅ **Size**
- APK Size: ~5MB
- Minimal Dependencies
- Optimized Resources

## الخطوات التالية

- [ ] إضافة Navigation بين الشاشات
- [ ] بناء Patients Management Screen
- [ ] بناء Nurses Management Screen
- [ ] بناء Orders Management Screen
- [ ] بناء Analytics Dashboard
- [ ] إضافة Notifications
- [ ] كتابة Unit Tests
- [ ] إضافة CI/CD Pipeline

## المساهمة

يرجى اتباع معايير الكود:
- Kotlin Best Practices
- Compose Guidelines
- Clean Architecture
- SOLID Principles

## الترخيص

جميع الحقوق محفوظة © 2024

## الدعم

للمساعدة والدعم، يرجى التواصل عبر البريد الإلكتروني.
