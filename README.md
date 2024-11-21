
# TripLogger  

**TripLogger** is a personalized trip journal Android app that allows users to document their travels, write blogs, and revisit their cherished memories. The app uses Firebase for secure user authentication and SQLite for managing blog data locally, ensuring functionality even without an internet connection.  

---

## Features  

- **User Authentication**: Secure login and signup using Firebase Authentication.  
- **Blog Management**: Add, view, update, and delete blogs stored locally using SQLite.  
- **Memories Archive**: Access previously added blogs in an organized list.  
- **Profile Page**: Manage user-specific settings.  
- **Persistent Login**: Auto-login for returning users using SharedPreferences.  
- **Offline Support**: Fully functional offline capabilities for managing blogs.  
- **Interactive UI**: Simple, clean, and intuitive design for effortless navigation.  

---

## Tech Stack  

- **Language**: Kotlin  
- **Local Database**: SQLite  
- **Authentication**: Firebase Authentication  
- **Local Storage**: SharedPreferences for session management  
- **UI Design**: XML layouts  
- **Tools**: Android Studio  

---

## Screens and Navigation  

### 1. **Welcome Page**  
   - Introduction to the app with options to log in or sign up.  

### 2. **Login Page**  
   - Secure Firebase Authentication for registered users.  

### 3. **Signup Page**  
   - Register new users via Firebase Authentication.  

### 4. **Add Blog**  
   - Form to create a new blog, including fields for Title, Description, and Date.  

### 5. **Blog View Page**  
   - Displays detailed information about a selected blog.   

### 6. **Profile Page**  
   - Displays user profile details and a logout button.  

---

## Database Design  

- **SQLite Database**:  
  - **Table Name**: `Blogs`  
    - `Blog_ID` (Primary Key)  
    - `Title` (Text)  
    - `Description` (Text)  
    - `Date` (Text)  
- **SharedPreferences**:  
  - Saves session tokens for persistent login.  

---

## How to Use  

1. Clone the repository:  
   ```bash  
   git clone https://github.com/mandalabhash/TripLogger--CA4-CSE227.git
   ```  
2. Open the project in **Android Studio**.  
3. Set up Firebase Authentication:  
   - Add your `google-services.json` file to the app directory.  
   - Enable Firebase Authentication in the Firebase console.  
4. Build and run the app on an emulator or physical device.  

---

## Future Enhancements  

- **Image Uploads**: Allow users to attach images to their blogs.  
- **Cloud Sync**: Add an option to sync blogs to a cloud database.  
- **Search and Filter**: Provide functionality to search or filter blogs by keywords or dates.  
- **Enhanced UI**: Add animations and a dark mode for better user experience.   

---

## License  

This project is licensed under the MIT License. See the LICENSE file for details.  

---
