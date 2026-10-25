# 🎂 Birthday Reminder App

A simple and user-friendly Android application that helps users manage birthdays and keep track of upcoming celebrations.

Developed as part of the **Mobile Application Development (MAD) lab activity: Implement Data Sharing Using Contacts Content Provider**, this application demonstrates how an Android application can access contact information using the Android Contacts Content Provider.

## 📌 Project Overview

The Birthday Reminder App allows users to view contacts stored on their Android devices, select contacts, and manage their birthday information.

The application demonstrates data sharing between Android applications through the Contacts Content Provider. It retrieves available contact information and allows users to associate birthday details with selected contacts.

Users can maintain birthday records and view upcoming birthdays through a simple and easy-to-use interface.

## ✨ Features

- 📱 **Contact Access:** Retrieve contacts stored on the device.
- 👤 **Contact Selection:** Select a contact for birthday management.
- 🎂 **Birthday Management:** Add or manage birthdays for selected contacts.
- 📅 **Upcoming Birthdays:** View upcoming birthday reminders.
- 🔍 **Contact Details:** Display available contact information.
- 💾 **Local Data Storage:** Save birthday information for future reference.
- 🎨 **User-Friendly Interface:** Simple and easy-to-navigate design.

## 🛠️ Technologies Used

- **Platform:** Android
- **IDE:** Android Studio
- **Programming Language:** Kotlin
- **Data Sharing:** Android Contacts Content Provider
- **API:** Android `ContactsContract`
- **Contact Access:** `ContentResolver`
- **UI:** XML Layouts and Android UI Components
- **Version Control:** Git and GitHub

## 📚 Concepts Implemented

### 1. Contacts Content Provider

The Android Contacts Content Provider allows applications to access contact information stored on the device, subject to the required permissions.

In this project, it can be used to retrieve contact names, contact IDs, and available phone numbers.

### 2. Content Resolver

The `ContentResolver` API is used to query the Contacts Content Provider and retrieve contact information.

It provides a standard way for an Android application to communicate with a content provider.

### 3. Contacts Contract

`ContactsContract` provides constants and definitions for accessing contact information.

Commonly used components include:

- `ContactsContract.Contacts` — Access contact records.
- `ContactsContract.CommonDataKinds.Phone` — Access phone-number information.
- `ContactsContract.CommonDataKinds.Email` — Access email information, if required.

### 4n. Birthday Storage

If birthday information is not available in a contact, the application can allow users to enter it manually and save it in the app's own local storage.

## 🔄 Application Workflow

1. Launch the Birthday Reminder App.
2. Grant contact access permission when requested.
3. Retrieve and display available contacts.
4. Select a contact.
5. Enter or manage the person's birthday.
6. Save the birthday information.
7. View saved birthdays and upcoming celebrations.

## 📸 Screenshots


### 1. Home Screen

Displays the main screen of the Birthday Reminder App.

![Home Screen](screenshots/ss1.png)

### 2. Contact Selection Screen

Displays the contacts available for birthday management.

![Contact Selection Screen](screenshots/ss2.png)

### 3. Birthday Details Screen

Displays birthday information for a selected contact.

![Birthday Details Screen](screenshots/ss3.png)




## 🎯 Learning Objectives

This project demonstrates the following concepts:

- Understanding Android Content Providers.
- Sharing data between Android applications.
- Accessing contacts using `ContactsContract`.
- Querying data through `ContentResolver`.
- Requesting and handling runtime permissions.
- Retrieving and displaying contact information.
- Managing birthday information within an Android application.
- Designing a simple Android user interface.

## 🔮 Future Enhancements

The application can be improved by adding:

- Automatic birthday reminders through notifications.
- Upcoming birthday sorting.
- Countdown to the next birthday.
- Birthday cards and personalized greetings.
- Search and filter options.
- Contact profile pictures.
- Calendar integration.
- Custom notification settings.


**Result:** The Birthday Reminder App demonstrates how an Android application can retrieve contact information through the Contacts Content Provider and use it to manage birthday details.

## 👨‍💻 Author

Rohit Kumar Singh

**Birthday Reminder App**
