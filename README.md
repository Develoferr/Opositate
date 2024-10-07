# 📱 Opositate - Android App

**Opositate** is an Android application designed to help users prepare for psychometric tests through interactive quizzes and study materials. The app also measures user progress and evaluates their level in various proposed activities.

## 🚀 Key Features

- **Interactive Tests**: Take a variety of psychometric tests in different areas to improve your skills.
- **Study Topics**: Access theoretical content that will help reinforce your knowledge before taking the tests.
- **Progress Measurement**: Visualize your performance over time with customized graphs and reports.
- **Activity Levels**: Each user can see their level in different exercise categories, motivating continuous progress.
- **User Authentication**: Secure login using Firebase Authentication.
- **Cloud Synchronization**: Securely store your progress and data using Firebase Cloud Firestore.

## 🛠️ Technologies Used

The app is developed using the following technologies and patterns:

- **Jetpack Compose**: For creating a modern, declarative, and reactive user interface.
- **MVVM (Model-View-ViewModel)**: Architecture that separates business logic from the UI to facilitate maintenance and testing.
- **Hilt**: Dependency injection for better modularization and scalability.
- **DataStore**: Efficient and secure storage of preferences and simple data.
- **Firebase Authentication**: Secure handling of user authentication with multiple providers (Google, Email, etc.).
- **Firebase Cloud Firestore**: Real-time database to store and synchronize test results and progress.

## 📸 Screenshots


## 📈 Future Features


## 🛠️ Installation and Usage

### Requirements

- **Android 11.0 (Red Velvet Cake)** or higher.
- Internet connection for Firebase synchronization.

### Steps to Clone the Project

1. Clone this repository:

   ```bash
   git clone https://github.com/yourusername/psychtestprep.git
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Configure Firebase:
- Create a project in Firebase Console.
- Add the google-services.json file to the app folder.
5. Run the app on a physical device or emulator.

## 🔑 Firebase Configuration
For the app to work correctly, you must configure Firebase Authentication and Firestore. Follow these steps:

1. Go to the Firebase console.
2. Enable Firebase Authentication (email and/or external providers like Google).
3. Configure the Firestore database to store user results and progress.

## 👥 Contributions
Contributions are welcome! If you want to add improvements or fix any errors, please open an issue or send a pull request.

## 📝 License

## 📫 Contact
If you have questions or want to know more about the app, don't hesitate to get in touch:

Email: programacionfer@gmail.com
GitHub: develoferr