# testbit-springboot

Proyek ini merupakan implementasi sederhana dari sistem pemesanan produk menggunakan **Spring Boot** dengan arsitektur **microservice sederhana** yang terdiri dari:

- Product Service
- Order Service

Kedua service berkomunikasi melalui REST API dan menggunakan **PostgreSQL** sebagai database.

---

# Arsitektur Sistem

Project ini terdiri dari dua service utama:

### Product Service
Service ini bertanggung jawab untuk mengelola data produk seperti:

- Membuat produk baru
- Melihat daftar produk
- Mengurangi stok produk

Endpoint utama:

GET /api/products  
POST /api/products  
PATCH /api/products/{id}/stock

---

### Order Service
Service ini bertanggung jawab untuk membuat order dari produk yang tersedia.

Ketika order dibuat:

1. Order Service memanggil Product Service
2. Product Service mengurangi stok
3. Jika stok tidak cukup maka transaksi akan dibatalkan

Endpoint utama:

POST /api/orders  
GET /api/orders

---

# Teknologi yang Digunakan

Beberapa teknologi yang digunakan dalam proyek ini:

- Java 21+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Docker Compose
- WebClient (untuk komunikasi antar service)
