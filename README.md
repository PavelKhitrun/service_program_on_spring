<div align="center">

# 🔧 Service Program on Spring

### Сервисная программа для учёта оборудования, запасных частей и заказов

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 📖 О проекте

**Service Program on Spring** — это веб-приложение для автоматизации работы сервисного центра. Система позволяет вести учёт пользователей, оборудования, запасных частей и узлов, оформлять заказы и регистрировать гарантийные обращения.

Проект построен на **Spring Boot 4** с использованием **Spring Data JPA**, **Spring Security**, **Thymeleaf** для серверного рендеринга и **Liquibase** для версионирования схемы базы данных. Отдельный модуль — **кастомный Spring Boot стартер логирования**, подключаемый как локальная зависимость.

### ✨ Основные возможности

- 🔹 Управление пользователями с ролями и аутентификацией (Spring Security)
- 🔹 Учёт оборудования, узлов и запасных частей
- 🔹 Оформление заказов с привязкой к пользователю и списку деталей
- 🔹 Регистрация гарантийных обращений с описанием дефекта и фото
- 🔹 REST API с автоматической документацией через **Swagger UI (OpenAPI 3)**
- 🔹 Миграции БД через Liquibase
- 🔹 Кастомный стартер логирования `custom-logging-spring-boot-starter`

---

## 🛠️ Технологии

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

</div>

| Категория | Технологии |
|---|---|
| **Язык / платформа** | Java 17, Spring Boot 4.1.1 |
| **Сборка** | Gradle, Spring Dependency Management |
| **Веб** | Spring MVC, Thymeleaf, REST |
| **Данные** | Spring Data JPA, Hibernate, PostgreSQL |
| **Миграции** | Liquibase |
| **Безопасность** | Spring Security |
| **Документация API** | SpringDoc OpenAPI 3 (Swagger UI) |
| **Утилиты** | Lombok, Hibernate Validator |
| **Тесты** | JUnit 5 (JUnit BOM 6.0.0) |

---

## 🚀 Быстрый старт

### Требования

- Java 17+
- PostgreSQL 14+
- Gradle (или используйте входящий `./gradlew`)

### 1. Клонирование репозитория

```bash
git clone https://github.com/PavelKhitrun/service_program_on_spring.git
cd service_program_on_spring
```bash

**### 2. Настройка базы данных**
Создайте базу данных PostgreSQL и укажите параметры подключения в src/main/resources/application.properties (или application.yml):

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/service_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```bash

**### 3. Запуск приложения**

```bash
./gradlew bootRun
```bash
