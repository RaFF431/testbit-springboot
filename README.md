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

#Mengapa Command Pattern Dipilih?
command pattern dipilih untuk memisahkan request handling dan business logic sehingga controller tidak mengakses secara langsung ke service dan hanya bicara pada command invoker sehingga kedepanya apabila terdapat fitur yang ingin ditambahkan menjadi lebih mudah

#Apa itu Mono dalam Project Reactor
mono dalam project reactor adalah reactive publisher yang merepresentasikan operasi asynchronous yang menghasilkan maksimal satu nilai atau kosong (0..1 item). Mono bersifat lazy dan non-blocking.

non-blocking berarti thread tidak menunggu operasi I/O selesai, melainkan menggunakan event-driven callbacks ketika data sudah tersedia. sehingga satu event-loop thread dapat menangani banyak request secara asynchronous.

#perbedaan mendasar WebClient dan HTTP client konvensional
HTTP client konvensional seperti RestTemplate menggunakan model blocking thread-per-request, di mana thread menunggu response sebelum melanjutkan eksekusi. Sebaliknya, WebClient menggunakan reactive non-blocking I/O, sehingga thread tidak idle dan dapat menangani banyak request secara efisien dengan model event-driven.

