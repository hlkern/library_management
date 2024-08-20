Proje Adı: Kütüphane Yönetim Sistemi

Bu proje, kullanıcıların kitap ekleyip güncelleyebileceği, kitap ödünç alıp iade edebileceği ve çeşitli raporlar alabileceği bir kütüphane yönetim sistemidir. Farklı rollere sahip kullanıcıların (öğrenci, öğretmen, personel) farklı yetkileri olacak şekilde yapılandırılmıştır.

Teknolojiler: 
* Backend: Java 17, Spring Boot
* Database: PostgreSQL
* Containerization: Docker
* Dependency Management: Gradle
* Testing: JUnit 5, Mockito, Jacoco
* Mapping: MapStruct
* Notification: Simple Mail Transfer Protocol (SMTP)

Bu proje Clean Code ve SOLID prensiplerine uygun olarak geliştirilmiş olup, gerekli yerlerde Open-Closed prensibi kullanılmıştır.

API Endpoint'leri

Kullanıcı İşlemleri
- POST /api/auth/register: Yeni kullanıcı kaydı
- POST /api/auth/login: Kullanıcı girişi
- PUT /api/users/{userId}: Kullanıcı bilgilerini güncelleme

Kitap Yönetimi
- POST /api/books: Kitap ekleme
- PUT /api/books/{bookId}: Kitap güncelleme
- DELETE /api/books/{bookId}: Kitap silme
- GET /api/books: Kitapları listeleme (kategoriye göre filtreleme)

Ödünç Alma ve İade
- POST /api/transactions/borrow: Kitap ödünç alma
- PUT /api/transactions/return/{transactionId}: Kitap iade
- GET /api/transactions/user/{userId}: Kullanıcının ödünç aldığı kitapları listeleme

Arama ve Filtreleme
- GET /api/books/search: Kitap arama ve filtreleme (adı, yazarı, kategorisi)

Bildirimler
- POST /api/notifications/overdue: Gecikmiş kitaplar için bildirim gönderme
- POST /api/notifications/newbooks: Yeni eklenen kitaplar hakkında bildirimler 

Raporlama
- GET /api/reports/mostborrowed: En çok ödünç alınan kitaplar
- GET /api/reports/userhistory/{userId}: Kullanıcıların ödünç alma geçmişi
- GET /api/reports/stock: Stok raporları
