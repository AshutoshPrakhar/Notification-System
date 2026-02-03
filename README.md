# Notification-System
A Java-based notification system demonstrating Decorator, Observer, Strategy, and Singleton design patterns with real-world use cases like Email, SMS, and Popup notifications.

## 📖 Overview

This project is a **Java-based Notification System** designed to demonstrate the practical use of multiple **object-oriented design patterns** working together in a real-world scenario.

The system allows:
- Creating notifications
- Enhancing notifications with extra details (timestamp, signature)
- Automatically notifying multiple components
- Sending notifications via multiple channels such as **Email, SMS, and Pop-up**

---

## 🧠 Design Patterns Used

### 1. Decorator Pattern
Used to dynamically add features to notifications without modifying the base notification class.
- Timestamp decorator
- Signature decorator

### 2. Observer Pattern
Used to notify all registered observers whenever a new notification is created.
- Logger
- Notification Engine

### 3. Strategy Pattern
Used to support multiple notification delivery methods.
- Email notification
- SMS notification
- Pop-up notification

### 4. Singleton Pattern
Ensures that only one instance of `NotificationService` exists throughout the application.

---

## ⚙️ System Flow

1. A base notification is created with simple text.
2. Decorators add timestamp and signature to the notification.
3. The notification is sent using `NotificationService`.
4. All registered observers are notified automatically.
5. Logger logs the notification.
6. Notification Engine sends the notification through all configured channels.



