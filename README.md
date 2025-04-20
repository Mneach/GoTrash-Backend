# ♻️ GoTrash Backend

Welcome to **GoTrash**, the waste management backend service that’s not trashy at all 😉  
This is the backend brain of my final project (*skripsi*), built to gamify trash disposal and make the world a cleaner place 🌍✨

---

## 🚀 What is GoTrash?

**GoTrash** is an intelligent waste disposal system that uses:
- 🧠 AI & IoT to classify trash
- 📱 Mobile apps to reward users
- 💪 Points system to motivate recycling

The **backend** handles all the logic — from user management to trash bin tracking and notification delivery. You throw the trash, we handle the rest 😉

---

## 🛠️ Tech Stack

- 🔙 Java 21 + Spring Boot
- 🐘 PostgreSQL
- 📦 Docker
- 🔑 JWT Authentication
- 📬 Swagger for API docs
- 📡 Nginx Reverse Proxy
- 🌀 GitHub Actions for CI/CD
- 🧠 Still learning and improving

---

## 🚧 To Be Done (TBD)
- [ ] 🧑‍💻 **Gotrash Dashboard Endpoint** - Working on it
- [ ] 📊 **Monitoring & Logging** – Datadog Monitor
- [ ] 🧩 **System Design Diagram** - I will do it later if i have a time :)

## 📁 Project Structure

```bash
📦 src
 ┣ 📂api               # Controllers (REST APIs)
 ┣ 📂config            # App configuration
 ┣ 📂constant          # Static constants (e.g., role names)
 ┣ 📂entity            # JPA entities
 ┣ 📂exception         # Custom exception classes and handlers
 ┣ 📂repository        # JPA repositories
 ┣ 📂security          # Security config, JWT, filters, etc.
 ┣ 📂service           # Business logic and service layers
 ┗ 📂util              # Utility/helper classes
