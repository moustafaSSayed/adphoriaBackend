# Adphoria Backend API

A Spring Boot REST API application with JWT authentication and bilingual (English/Arabic) support for managing FAQs, Blogs, Research, Previous Work, and Consultations.

## ✨ Features

- 🔐 **JWT Authentication** - Secure authentication with JSON Web Tokens
- 🌍 **Bilingual Support** - Full English and Arabic content support
- 📁 **File Upload** - Cloud-based file storage via Cloudinary (supports all file types)
- 🐳 **Docker Ready** - Containerized deployment support
- 🔒 **Role-Based Access** - Public and protected endpoints
- 📊 **RESTful APIs** - Clean and well-structured API design

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 23 | Programming Language |
| Spring Boot | 3.4.1 | Application Framework |
| Spring Security | 3.4.1 | Authentication & Authorization |
| Spring Data JPA | 3.4.1 | Database ORM |
| MySQL | Latest | Database |
| JWT (JJWT) | 0.12.3 | Token Generation |
| Lombok | 1.18.30 | Reduce Boilerplate Code |
| Cloudinary | 1.36.0 | Cloud File Storage |
| Maven | Latest | Build Tool |
| Docker | Latest | Containerization |

## 🚀 Getting Started

### Prerequisites

- **Java 23** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Docker** (optional, for containerized deployment)

### Environment Configuration

The application supports multiple profiles:
- `dev` - Development environment
- `prod` - Production environment

Configure your database and Cloudinary credentials in `src/main/resources/application-{profile}.properties`

### Running Locally

```bash
# Development mode
mvn spring-boot:run

# With specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The application will start at `http://localhost:8080`

### Docker Deployment

```bash
# Build the Docker image
docker build -t adphoria-backend .

# Run the container
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=your_database_url \
  -e DB_USERNAME=your_username \
  -e DB_PASSWORD=your_password \
  adphoria-backend
```

> **Note**: The `VOLUME` keyword has been removed from the Dockerfile for Railway compatibility. Configure persistent storage through Railway volumes mounted at `/app/uploads`.

---

## 🌍 Bilingual Support

All content entities (FAQ, Blog, Research, PreviousWork) support both **English** and **Arabic** languages using a **nested object structure** for better organization:

- **FAQ**: `question: {en, ar}`, `answer: {en, ar}`
- **Blog**: `title: {en, ar}`, `body: {en, ar}`, `shortDescription: {en, ar}`
- **Research**: `title: {en, ar}`, `body: {en, ar}`, `shortDescription: {en, ar}`
- **PreviousWork**: `name: {en, ar}`, `summary: {en, ar}`, `caseName: {en, ar}`, `caseCategory: {en, ar}`

All bilingual entities also include auto-generated `slug` fields based on the English title for SEO-friendly URLs.

### Response Example:
```json
{
  "blogId": 1,
  "slug": "understanding-legal-rights",
  "blogTitle": {
    "en": "Understanding Legal Rights",
    "ar": "فهم الحقوق القانونية"
  },
  "blogBody": {
    "en": "Full content...",
    "ar": "المحتوى الكامل..."
  }
}
```

---

## 📄 Pagination

All `GET` endpoints for listing resources (except single item retrieval) now support **pagination** using query parameters:

- `page` - Page number (1-indexed, default: 1)
- `size` - Items per page (default: 15)

### Paginated Response Format:
```json
{
  "data": [
    { /* entity data */ }
  ],
  "meta": {
    "currentPage": 1,
    "pageSize": 15,
    "totalPages": 5,
    "totalElements": 73
  }
}
```

**Example Request:**
```http
GET /api/blogs?page=2&size=10
```

---

## 🔐 Authentication

### Register a New Admin

```http
POST /auth/register
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "admin",
  "password": "password123",
  "email": "admin@example.com"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Login

```http
POST /auth/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "admin",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Using the JWT Token

For protected endpoints, include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

---

## 📚 API Endpoints Overview

### Access Control Summary

| Endpoint Pattern | GET | POST | PUT | DELETE |
|-----------------|-----|------|-----|--------|
| `/auth/**` | - | 🌐 Public | - | - |
| `/api/faqs` | 🌐 Public | 🔒 Auth | 🔒 Auth | 🔒 Auth |
| `/api/blogs` | 🌐 Public | 🔒 Auth | 🔒 Auth | 🔒 Auth |
| `/api/researches` | 🌐 Public | 🔒 Auth | 🔒 Auth | 🔒 Auth |
| `/api/previous-works` | 🌐 Public | 🔒 Auth | 🔒 Auth | 🔒 Auth |
| `/api/consultations` | 🔒 Auth | 🌐 Public | 🔒 Auth | 🔒 Auth |
| `/uploads/**` | 🌐 Public | - | - | - |

🌐 **Public** - No authentication required  
🔒 **Auth** - Requires JWT token

---

## ❓ FAQ APIs

### Get All FAQs (Paginated) 🌐

```http
GET /api/faqs?page=1&size=15
```

**Response:**
```json
{
  "data": [
    {
      "questionId": 1,
      "slug": "what-services-do-you-offer",
      "question": {
        "en": "What services do you offer?",
        "ar": "ما هي الخدمات التي تقدمونها؟"
      },
      "answer": {
        "en": "We provide comprehensive legal consultation services.",
        "ar": "نقدم خدمات استشارات قانونية شاملة."
      }
    }
  ],
  "meta": {
    "currentPage": 1,
    "pageSize": 15,
    "totalPages": 2,
    "totalElements": 25
  }
}
```

### Get FAQ by ID 🌐

```http
GET /api/faqs/{id}
```

### Get FAQ by Slug 🌐

```http
GET /api/faqs/slug/{slug}
```

**Example:**
```
GET /api/faqs/slug/what-services-do-you-offer
```

### Create FAQ 🔒

```http
POST /api/faqs
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "question": {
    "en": "What services do you offer?",
    "ar": "ما هي الخدمات التي تقدمونها؟"
  },
  "answer": {
    "en": "We provide comprehensive legal consultation services.",
    "ar": "نقدم خدمات استشارات قانونية شاملة."
  }
}
```

### Update FAQ 🔒

```http
PUT /api/faqs/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "question": {
    "en": "Updated question?",
    "ar": "سؤال محدث؟"
  },
  "answer": {
    "en": "Updated answer.",
    "ar": "إجابة محدثة."
  }
}
```

### Delete FAQ 🔒

```http
DELETE /api/faqs/{id}
Authorization: Bearer <token>
```

---

## 📝 Blog APIs

### Get All Blogs (Paginated) 🌐

```http
GET /api/blogs?page=1&size=15
```

**Response:**
```json
{
  "data": [
    {
      "blogId": 1,
      "slug": "understanding-legal-rights",
      "blogPhoto": "https://res.cloudinary.com/.../photo.jpg",
      "title": {
        "en": "Understanding Legal Rights",
        "ar": "فهم الحقوق القانونية"
      },
      "body": {
        "en": "Full blog content in English...",
        "ar": "محتوى المدونة الكامل بالعربية..."
      },
      "shortDescription": {
        "en": "Brief summary in English",
        "ar": "ملخص موجز بالعربية"
      }
    }
  ],
  "meta": {
    "currentPage": 1,
    "pageSize": 15,
    "totalPages": 3,
    "totalElements": 42
  }
}
```

### Get Blog by ID 🌐

```http
GET /api/blogs/{id}
```

### Get Blog by Slug 🌐

```http
GET /api/blogs/slug/{slug}
```

**Example:**
```
GET /api/blogs/slug/understanding-legal-rights
```

### Create Blog (with Photo) 🔒

```http
POST /api/blogs
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| titleEn | text | Yes | Blog title in English |
| titleAr | text | Yes | Blog title in Arabic |
| bodyEn | text | Yes | Blog content in English |
| bodyAr | text | Yes | Blog content in Arabic |
| shortDescriptionEn | text | No | Short summary in English |
| shortDescriptionAr | text | No | Short summary in Arabic |
| blogPhoto | file | No | Image file (uploaded to Cloudinary) |

### Update Blog 🔒

```http
PUT /api/blogs/{id}
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:** Same as Create Blog

**Alternatively (JSON only - no photo update):**

```http
PUT /api/blogs/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": {
    "en": "Updated English Title",
    "ar": "العنوان المحدث بالعربية"
  },
  "body": {
    "en": "Updated English content...",
    "ar": "المحتوى المحدث بالعربية..."
  },
  "shortDescription": {
    "en": "Updated English summary",
    "ar": "الملخص المحدث بالعربية"
  }
}
```

### Delete Blog 🔒

```http
DELETE /api/blogs/{id}
Authorization: Bearer <token>
```

---

## 🔬 Research APIs

### Get All Research 🌐

```http
GET /api/researches
```

**Response:**
```json
[
  {
    "researchId": 1,
    "researchPhoto": "https://res.cloudinary.com/.../research.jpg",
    "researchEnglishTitle": "Legal Framework Study",
    "researchEnglishBody": "Full research content in English...",
    "englishShortDescription": "Brief summary in English",
    "researchArabicTitle": "دراسة الإطار القانوني",
    "researchArabicBody": "محتوى البحث الكامل بالعربية...",
    "arabicShortDescription": "ملخص موجز بالعربية"
  }
]
```

### Get Research by ID 🌐

```http
GET /api/researches/{id}
```

### Create Research (with Photo) 🔒

```http
POST /api/researches
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| researchEnglishTitle | text | Yes | Research title in English |
| researchEnglishBody | text | Yes | Research content in English |
| englishShortDescription | text | No | Short summary in English |
| researchArabicTitle | text | Yes | Research title in Arabic |
| researchArabicBody | text | Yes | Research content in Arabic |
| arabicShortDescription | text | No | Short summary in Arabic |
| researchPhoto | file | No | Image file (uploaded to Cloudinary) |

### Update Research 🔒

```http
PUT /api/researches/{id}
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:** Same as Create Research

**Alternatively (JSON only - no photo update):**

```http
PUT /api/researches/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "researchEnglishTitle": "Updated English Title",
  "researchEnglishBody": "Updated English content...",
  "englishShortDescription": "Updated English summary",
  "researchArabicTitle": "العنوان المحدث بالعربية",
  "researchArabicBody": "المحتوى المحدث بالعربية...",
  "arabicShortDescription": "الملخص المحدث بالعربية"
}
```

### Delete Research 🔒

```http
DELETE /api/researches/{id}
Authorization: Bearer <token>
```

---

## 💼 Previous Work APIs

### Get All Previous Works 🌐

```http
GET /api/previous-works
```

**Response:**
```json
[
  {
    "previousWorkId": 1,
    "englishPreviousWorkName": "Contract Dispute Resolution",
    "englishSummary": "Successfully resolved a complex contract dispute...",
    "englishCaseName": "Smith vs. Johnson Corp",
    "englishCaseCategory": "Commercial Law",
    "arabicPreviousWorkName": "حل نزاع عقد",
    "arabicSummary": "تم حل نزاع عقد معقد بنجاح...",
    "arabicCaseName": "سميث ضد شركة جونسون",
    "arabicCaseCategory": "القانون التجاري",
    "caseFile": "https://res.cloudinary.com/.../case-document.pdf"
  }
]
```

### Get Previous Work by ID 🌐

```http
GET /api/previous-works/{id}
```

### Create Previous Work (with File) 🔒

```http
POST /api/previous-works
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:**

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| englishPreviousWorkName | text | Yes | Work name in English |
| englishSummary | text | No | Summary in English |
| englishCaseName | text | No | Case name in English |
| englishCaseCategory | text | No | Case category in English |
| arabicPreviousWorkName | text | Yes | Work name in Arabic |
| arabicSummary | text | No | Summary in Arabic |
| arabicCaseName | text | No | Case name in Arabic |
| arabicCaseCategory | text | No | Case category in Arabic |
| caseFile | file | No | Any file type (uploaded to Cloudinary) |

### Update Previous Work 🔒

```http
PUT /api/previous-works/{id}
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:** Same as Create Previous Work

**Alternatively (JSON only - no file update):**

```http
PUT /api/previous-works/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "englishPreviousWorkName": "Updated Work Name",
  "englishSummary": "Updated summary...",
  "englishCaseName": "Updated Case Name",
  "englishCaseCategory": "Updated Category",
  "arabicPreviousWorkName": "اسم العمل المحدث",
  "arabicSummary": "الملخص المحدث...",
  "arabicCaseName": "اسم القضية المحدثة",
  "arabicCaseCategory": "الفئة المحدثة"
}
```

### Delete Previous Work 🔒

```http
DELETE /api/previous-works/{id}
Authorization: Bearer <token>
```

---

## 💬 Consultation APIs

### Get All Consultations (Paginated) 🔒

```http
GET /api/consultations?page=1&size=15
Authorization: Bearer <token>
```

**Response:**
```json
{
  "data": [
    {
      "consultationId": 1,
      "consultationStatus": "PENDING",
      "firstName": "John",
      "lastName": "Doe",
      "causeCategory": "Legal",
      "causeName": "Contract Review",
      "mobileNumber": "+1234567890",
      "email": "john@example.com",
      "consultationBody": "I need help reviewing my employment contract..."
    }
  ],
  "meta": {
    "currentPage": 1,
    "pageSize": 15,
    "totalPages": 1,
    "totalElements": 8
  }
}
```

### Get Consultation by ID 🔒

```http
GET /api/consultations/{id}
Authorization: Bearer <token>
```

### Create Consultation 🌐

```http
POST /api/consultations
Content-Type: application/json
```

> **Note:** This endpoint is **PUBLIC** - anyone can submit a consultation request without authentication.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "causeCategory": "Legal",
  "causeName": "Contract Review",
  "mobileNumber": "+1234567890",
  "email": "john@example.com",
  "consultationBody": "I need help reviewing my employment contract..."
}
```

### Update Consultation 🔒

```http
PUT /api/consultations/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "consultationStatus": "APPROVED",
  "firstName": "John",
  "lastName": "Doe",
  "causeCategory": "Legal",
  "causeName": "Contract Review",
  "mobileNumber": "+1234567890",
  "email": "john@example.com",
  "consultationBody": "Updated consultation details..."
}
```

### Delete Consultation 🔒

```http
DELETE /api/consultations/{id}
Authorization: Bearer <token>
```

---

## 📁 File Upload & Storage

### File Storage System

The application uses **Cloudinary** for cloud-based file storage, supporting:
- ✅ Images (JPEG, PNG, GIF, WebP, etc.)
- ✅ Documents (PDF, DOC, DOCX, etc.)
- ✅ Any file type

### Accessing Uploaded Files 🌐

All uploaded files are accessible via their Cloudinary URLs returned in the API responses. For example:

```
https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/filename.jpg
```

No authentication is required to access uploaded files.

### File Upload Limits

Configure file size limits in `application.properties`:
```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 🧪 Testing Guide

### Testing with Postman

#### 1. Import Collection
Create a new Postman collection and add the endpoints from this documentation.

#### 2. Test Public Endpoints (No Auth)
- `GET /api/faqs` - List all FAQs
- `GET /api/blogs` - List all blogs
- `GET /api/researches` - List all research  
- `GET /api/previous-works` - List all previous work
- `POST /api/consultations` - Submit a consultation

#### 3. Authenticate
1. `POST /auth/register` - Register a new admin (first time only)
2. `POST /auth/login` - Login and copy the JWT token

#### 4. Configure Authorization
- In Postman collection settings:
  - Go to **Authorization** tab
  - Select **Bearer Token**
  - Paste your JWT token

#### 5. Test Protected Endpoints
Now test all CREATE, UPDATE, and DELETE operations for FAQs, Blogs, Research, and Previous Work.

### Testing with cURL

#### Public Endpoints
```bash
# Get all FAQs
curl http://localhost:8080/api/faqs

# Get all blogs
curl http://localhost:8080/api/blogs

# Submit a consultation
curl -X POST http://localhost:8080/api/consultations \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "mobileNumber": "+1234567890",
    "causeCategory": "Legal",
    "causeName": "Contract Review",
    "consultationBody": "Need help with contract"
  }'
```

#### Authentication
```bash
# Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123",
    "email": "admin@example.com"
  }'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

#### Protected Endpoints
```bash
# Create FAQ (replace TOKEN with your JWT)
curl -X POST http://localhost:8080/api/faqs \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "englishQuestion": "What is this?",
    "englishAnswer": "This is a test.",
    "arabicQuestion": "ما هذا؟",
    "arabicAnswer": "هذا اختبار."
  }'

# Create Blog with photo
curl -X POST http://localhost:8080/api/blogs \
  -H "Authorization: Bearer TOKEN" \
  -F "blogEnglishTitle=My Blog" \
  -F "blogEnglishBody=English content" \
  -F "blogArabicTitle=مدونتي" \
  -F "blogArabicBody=المحتوى العربي" \
  -F "blogPhoto=@/path/to/photo.jpg"
```

---

## 📊 HTTP Response Codes

| Code | Description |
|------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Successful delete |
| 400 | Bad Request - Invalid input |
| 401 | Unauthorized - Missing or invalid JWT token |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource doesn't exist |
| 500 | Internal Server Error - Server error |

---

## 📝 License

This project is proprietary software developed for Adphoria.

## 🤝 Support

For questions or issues, contact the development team.
