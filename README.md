# Home Nursing Admin Dashboard - Kotlin & Android

تطبيق إدارة شامل لمنصة خدمات التمريض المنزلي، مبني باستخدام:
- **Backend**: Spring Boot + Kotlin
- **Mobile**: Android + Jetpack Compose + Kotlin
- **Database**: MySQL

## البنية الأساسية

```
nursing_admin_kotlin/
├── backend/                          # Spring Boot Backend
│   ├── src/main/kotlin/com/nursing/admin/
│   │   ├── entity/                  # JPA Entities
│   │   ├── repository/              # Spring Data Repositories
│   │   ├── service/                 # Business Logic
│   │   ├── controller/              # REST Controllers
│   │   ├── dto/                     # Data Transfer Objects
│   │   ├── security/                # JWT & Security
│   │   └── config/                  # Configuration
│   ├── src/main/resources/
│   │   └── application.yml          # Configuration
│   └── build.gradle.kts             # Gradle Build File
│
├── android-app/                      # Android Application
│   ├── app/
│   │   ├── src/main/kotlin/
│   │   ├── src/main/res/
│   │   └── build.gradle.kts
│   └── build.gradle.kts
│
└── README.md
```

## المميزات

### Backend (Spring Boot)

✅ **المصادقة والأمان**
- تسجيل دخول آمن باستخدام JWT
- تشفير كلمات المرور بـ BCrypt
- CORS مفعل

✅ **إدارة البيانات**
- 9 جداول رئيسية في قاعدة البيانات
- علاقات معقدة بين الجداول
- Pagination و Filtering

✅ **API الشاملة**
- Auth APIs (تسجيل الدخول)
- Patient Management
- Nurse Management
- Order Management
- Payment Tracking
- Commission Management
- Reviews & Complaints
- Notifications
- Analytics

### Android App

✅ **واجهة حديثة**
- Jetpack Compose UI
- Material Design 3
- Dark Mode Support

✅ **الميزات**
- تسجيل دخول آمن
- لوحة تحكم شاملة
- إدارة المستخدمين
- إدارة الطلبات
- تتبع المدفوعات
- التحليلات والإحصائيات
- الإشعارات

## المتطلبات

- Java 17+
- Kotlin 1.9.22+
- MySQL 8.0+
- Android Studio (للتطبيق الجوال)
- Gradle 8.0+

## التثبيت والتشغيل

### Backend

1. **تثبيت قاعدة البيانات**
```bash
mysql -u root -p
CREATE DATABASE nursing_admin;
```

2. **تحديث ملف التكوين**
```yaml
# backend/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/nursing_admin
    username: root
    password: your_password
```

3. **تشغيل التطبيق**
```bash
cd backend
./gradlew bootRun
```

الخادم سيعمل على: `http://localhost:8080/api`

### Android App

1. **فتح المشروع في Android Studio**
```bash
cd android-app
```

2. **تحديث API URL**
```kotlin
// في ملف الإعدادات
const val API_BASE_URL = "http://your-server-ip:8080/api"
```

3. **تشغيل التطبيق**
- اختر جهاز أو محاكي
- اضغط Run

## API Documentation

### Authentication

**Login**
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "password123"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGc...",
    "admin": {
      "id": 1,
      "email": "admin@example.com",
      "name": "Admin User",
      "role": "ADMIN",
      "isActive": true,
      "createdAt": "2024-01-01T00:00:00"
    }
  }
}
```

### Patients

**Get All Patients**
```
GET /api/patients?page=0&size=10
Authorization: Bearer {token}
```

**Create Patient**
```
POST /api/patients
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "أحمد محمد",
  "email": "patient@example.com",
  "phone": "+966501234567",
  "address": "الرياض",
  "age": 45,
  "medicalHistory": "السكري"
}
```

### Orders

**Get All Orders**
```
GET /api/orders?status=PENDING&page=0&size=10
Authorization: Bearer {token}
```

**Assign Nurse to Order**
```
PUT /api/orders/{orderId}/assign-nurse
Authorization: Bearer {token}
Content-Type: application/json

{
  "nurseId": 5
}
```

### Payments

**Get Revenue**
```
GET /api/payments/revenue
Authorization: Bearer {token}
```

**Get Payments by Date Range**
```
GET /api/payments/revenue?startDate=2024-01-01&endDate=2024-01-31
Authorization: Bearer {token}
```

## قاعدة البيانات

### الجداول الرئيسية

1. **admins** - حسابات المسؤولين
2. **patients** - بيانات المرضى
3. **nurses** - بيانات الممرضات
4. **services** - الخدمات المتاحة
5. **orders** - الطلبات
6. **payments** - المدفوعات
7. **commissions** - العمولات
8. **reviews** - التقييمات
9. **complaints** - الشكاوى
10. **notifications** - الإشعارات

## الأمان

- ✅ تشفير كلمات المرور بـ BCrypt
- ✅ JWT Tokens للمصادقة
- ✅ CORS محدود
- ✅ Input Validation
- ✅ SQL Injection Protection (JPA)
- ✅ HTTPS Ready

## الخطوات التالية

- [ ] إنشاء admin user افتراضي
- [ ] بناء واجهة Android الكاملة
- [ ] إضافة Push Notifications
- [ ] إضافة Analytics Dashboard
- [ ] إضافة Export Reports
- [ ] إضافة Multi-language Support

## المساهمة

يرجى اتباع معايير الكود:
- استخدام Kotlin Best Practices
- إضافة Unit Tests
- توثيق الكود
- اتباع Git Commit Convention

## الترخيص

جميع الحقوق محفوظة © 2024

## الدعم

للمساعدة والدعم، يرجى التواصل عبر البريد الإلكتروني.
