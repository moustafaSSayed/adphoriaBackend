# Spring Boot Application API Documentation

A Spring Boot REST API application with JWT authentication for managing FAQs, Blogs, Researches, and Consultations.

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL (or H2 for development)

### Running the Application
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

---

## 🔐 Authentication

### Register a New Admin
```
POST /auth/register
```

**Request Body (JSON):**
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
```
POST /auth/login
```

**Request Body (JSON):**
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
For protected endpoints, add the token to the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

---

## 📚 API Endpoints

### Access Levels Summary

| Endpoint | Method | Access |
|----------|--------|--------|
| `/auth/**` | POST | 🌐 Public |
| `/api/faqs/**` | GET | 🌐 Public |
| `/api/faqs/**` | POST, PUT, DELETE | 🔒 Authenticated |
| `/api/blogs/**` | GET | 🌐 Public |
| `/api/blogs/**` | POST, PUT, DELETE | 🔒 Authenticated |
| `/api/researches/**` | GET | 🌐 Public |
| `/api/researches/**` | POST, PUT, DELETE | 🔒 Authenticated |
| `/api/consultations` | POST | 🌐 Public |
| `/api/consultations/**` | GET, PUT, DELETE | 🔒 Authenticated |

---

## 📋 FAQ APIs

### Get All FAQs 🌐
```
GET /api/faqs
```

**Response:**
```json
[
  {
    "questionId": 1,
    "question": "What is your service?",
    "answer": "We provide consultation services."
  }
]
```

### Get FAQ by ID 🌐
```
GET /api/faqs/{id}
```

### Search FAQs by Question 🌐
```
GET /api/faqs/search?question=service
```

### Create FAQ 🔒
```
POST /api/faqs
```
**Headers:** `Authorization: Bearer <token>`

**Request Body (JSON):**
```json
{
  "question": "What is your service?",
  "answer": "We provide consultation services."
}
```

### Update FAQ 🔒
```
PUT /api/faqs/{id}
```
**Headers:** `Authorization: Bearer <token>`

**Request Body (JSON):**
```json
{
  "question": "Updated question?",
  "answer": "Updated answer."
}
```

### Delete FAQ 🔒
```
DELETE /api/faqs/{id}
```
**Headers:** `Authorization: Bearer <token>`

---

## 📝 Blog APIs

### Get All Blogs 🌐
```
GET /api/blogs
```

**Response:**
```json
[
  {
    "blogId": 1,
    "blogPhoto": "http://localhost:8080/uploads/photo.jpg",
    "blogTitle": "First Blog Post",
    "blogBody": "This is the blog content..."
  }
]
```

### Get Blog by ID 🌐
```
GET /api/blogs/{id}
```

### Search Blogs by Title 🌐
```
GET /api/blogs/search?title=first
```

### Create Blog (with Photo Upload) 🔒
```
POST /api/blogs
Content-Type: multipart/form-data
```
**Headers:** `Authorization: Bearer <token>`

**Form Data:**
| Field | Type | Required |
|-------|------|----------|
| blogTitle | text | Yes |
| blogBody | text | Yes |
| blogPhoto | file | No |

### Update Blog (with Photo Upload) 🔒
```
PUT /api/blogs/{id}
Content-Type: multipart/form-data
```
**Headers:** `Authorization: Bearer <token>`

**Form Data:**
| Field | Type | Required |
|-------|------|----------|
| blogTitle | text | Yes |
| blogBody | text | Yes |
| blogPhoto | file | No |

### Update Blog (JSON only) 🔒
```
PUT /api/blogs/{id}
Content-Type: application/json
```
**Headers:** `Authorization: Bearer <token>`

**Request Body (JSON):**
```json
{
  "blogTitle": "Updated Title",
  "blogBody": "Updated content..."
}
```

### Delete Blog 🔒
```
DELETE /api/blogs/{id}
```
**Headers:** `Authorization: Bearer <token>`

---

## 🔬 Research APIs

### Get All Researches 🌐
```
GET /api/researches
```

**Response:**
```json
[
  {
    "researchId": 1,
    "researchPhoto": "http://localhost:8080/uploads/photo.jpg",
    "researchTitle": "First Research Paper",
    "researchBody": "This is the research content..."
  }
]
```

### Get Research by ID 🌐
```
GET /api/researches/{id}
```

### Search Researches by Title 🌐
```
GET /api/researches/search?title=first
```

### Create Research (with Photo Upload) 🔒
```
POST /api/researches
Content-Type: multipart/form-data
```
**Headers:** `Authorization: Bearer <token>`

**Form Data:**
| Field | Type | Required |
|-------|------|----------|
| researchTitle | text | Yes |
| researchBody | text | Yes |
| researchPhoto | file | No |

### Update Research (with Photo Upload) 🔒
```
PUT /api/researches/{id}
Content-Type: multipart/form-data
```
**Headers:** `Authorization: Bearer <token>`

**Form Data:**
| Field | Type | Required |
|-------|------|----------|
| researchTitle | text | Yes |
| researchBody | text | Yes |
| researchPhoto | file | No |

### Update Research (JSON only) 🔒
```
PUT /api/researches/{id}
Content-Type: application/json
```
**Headers:** `Authorization: Bearer <token>`

**Request Body (JSON):**
```json
{
  "researchTitle": "Updated Title",
  "researchBody": "Updated content..."
}
```

### Delete Research 🔒
```
DELETE /api/researches/{id}
```
**Headers:** `Authorization: Bearer <token>`

---

## 💬 Consultation APIs

### Get All Consultations 🔒
```
GET /api/consultations
```
**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
[
  {
    "consultationId": 1,
    "consultationStatus": "PENDING",
    "firstName": "John",
    "lastName": "Doe",
    "causeCategory": "Legal",
    "causeName": "Contract Review",
    "mobileNumber": "+1234567890",
    "email": "john@example.com",
    "consultationBody": "I need help with my contract..."
  }
]
```

### Get Consultation by ID 🔒
```
GET /api/consultations/{id}
```
**Headers:** `Authorization: Bearer <token>`

### Search Consultations by First Name 🔒
```
GET /api/consultations/search?firstName=John
```
**Headers:** `Authorization: Bearer <token>`

### Create Consultation 🌐
```
POST /api/consultations
```
> **Note:** This endpoint is PUBLIC - anyone can submit a consultation request.

**Request Body (JSON):**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "causeCategory": "Legal",
  "causeName": "Contract Review",
  "mobileNumber": "+1234567890",
  "email": "john@example.com",
  "consultationBody": "I need help with my contract..."
}
```

### Update Consultation 🔒
```
PUT /api/consultations/{id}
```
**Headers:** `Authorization: Bearer <token>`

**Request Body (JSON):**
```json
{
  "consultationStatus": "APPROVED",
  "firstName": "John",
  "lastName": "Doe",
  "causeCategory": "Legal",
  "causeName": "Contract Review",
  "mobileNumber": "+1234567890",
  "email": "john@example.com"
}
```

### Delete Consultation 🔒
```
DELETE /api/consultations/{id}
```
**Headers:** `Authorization: Bearer <token>`

---

## 🧪 Testing with Postman

### Step 1: Import the Collection
Create a new collection in Postman and add the endpoints described above.

### Step 2: Test Public Endpoints (No Auth Required)
1. **GET** `http://localhost:8080/api/faqs` - Get all FAQs
2. **GET** `http://localhost:8080/api/blogs` - Get all blogs
3. **GET** `http://localhost:8080/api/researches` - Get all researches
4. **POST** `http://localhost:8080/api/consultations` - Submit a consultation

### Step 3: Get Authentication Token
1. **POST** `http://localhost:8080/auth/register` - Register (first time)
2. **POST** `http://localhost:8080/auth/login` - Login and get token

### Step 4: Configure Authorization in Postman
1. In your Postman collection, go to **Authorization** tab
2. Select **Type**: `Bearer Token`
3. Paste your JWT token in the **Token** field

### Step 5: Test Protected Endpoints
Now you can test all protected endpoints:
- Create/Update/Delete FAQs
- Create/Update/Delete Blogs
- Create/Update/Delete Researches
- View/Update/Delete Consultations

---

## 📁 File Uploads

Uploaded files are stored in the `uploads/` directory and accessible via:
```
GET /uploads/{filename}
```
This endpoint is public.

---

## 🛠️ Testing with cURL

### Public Endpoints

```bash
# Get all FAQs
curl http://localhost:8080/api/faqs

# Get all blogs
curl http://localhost:8080/api/blogs

# Get all researches
curl http://localhost:8080/api/researches

# Submit a consultation
curl -X POST http://localhost:8080/api/consultations \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com","mobileNumber":"+1234567890","causeCategory":"Legal","causeName":"Contract Review"}'
```

### Authentication

```bash
# Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123","email":"admin@example.com"}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'
```

### Protected Endpoints

```bash
# Create FAQ (replace YOUR_TOKEN with actual token)
curl -X POST http://localhost:8080/api/faqs \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"question":"What is this?","answer":"This is a test."}'

# Create Blog with photo
curl -X POST http://localhost:8080/api/blogs \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "blogTitle=My Blog" \
  -F "blogBody=Blog content here" \
  -F "blogPhoto=@/path/to/photo.jpg"

# Create Research with photo
curl -X POST http://localhost:8080/api/researches \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "researchTitle=My Research" \
  -F "researchBody=Research content here" \
  -F "researchPhoto=@/path/to/photo.jpg"

# Get all consultations
curl http://localhost:8080/api/consultations \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 📊 Response Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (successful delete) |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |
