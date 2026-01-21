# 🎬 Movies Database App

An Android application to **browse, search, and manage movies** using the **OMDb API**.  
Built with **Jetpack Compose**, **Clean Architecture**, and **MVI**, the app focuses on scalability, offline support, and a modern Netflix-style user experience.

---

## 📖 Project Overview

**Movies Database App** allows users to explore curated movie sections, search for movies, view detailed information, and manage favourites with offline support.

The project is designed as a **production-ready Android app**, emphasizing:
- Unidirectional data flow
- Reactive UI
- Clear separation of concerns
- Maintainable and testable architecture

---

## ✨ Features

- 🔥 **Trending / Popular Movies**
- 🔍 **Search Movies by Title**
- 🎞 **Movie Details Screen**
  - Poster
  - Rating
  - Runtime
  - Genre
  - Release date
  - Plot & additional metadata
- ❤️ **Favourite Movies**
  - Toggle favourite from Home, Search, and Details
  - Favourite state synced across all screens
- 💾 **Offline Support**
  - Favourites stored locally using Room
- 🌐 **Network Handling**
  - Error handling with retry support
- 🔔 **User Feedback**
  - Snackbar on favourite add/remove
  - Subtle favourite icon animation
- 🎨 **Modern UI**
  - Jetpack Compose
  - Hero carousel
  - Smooth navigation and animations

---

## 🧱 Architecture Overview

The app follows **Clean Architecture with MVI (Model–View–Intent)**:

