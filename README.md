✅ 1. Launcher Home Screen
Displays selected apps in a grid

Shows only apps that are ticked in the app drawer

Fully themed with pure black background

Apps are aligned at the bottom of the screen

✅ 2. Swipe-Up App Drawer
Swipe up from home screen opens the app drawer

Displays all launchable apps, including dual/work profile apps

Includes a search bar to filter apps by name

Checkbox next to each app to select/deselect for home screen

✅ 3. App Launching
Clicking any app icon (in drawer or home) launches the app

Intent.FLAG_ACTIVITY_NEW_TASK ensures it works from launcher context

✅ 4. Persistent SharedPreferences
Saves selected apps using SharedPreferences

Also stores custom user input (like a note) to display on the home screen

✅ 5. Custom User Message
You can type a message in settings screen

The message is stored and displayed on the top-left of the home screen

Updates dynamically when you return from the settings screen

✅ 6. Launcher Settings Screen
Styled in dark theme with full-width UI

Contains:

An editable text field

A button to open System Launcher Settings (for default app config)

✅ 7. Edge-to-Edge UI + Dark Splash
Edge-to-edge layout with WindowCompat.setDecorFitsSystemWindows

Black status bar & nav bar

Black splash screen (Theme.Launcher.Splash) before Compose draws

Would you like to add more features? Here are a few ideas:

🔧 Suggested Next Features
🧲 Drag & drop icons on home screen

🌙 Dark/light theme toggle

🧹 Clear all selected apps button

🔒 Lock screen or kiosk mode (for child/device control)

📲 Long press on app → App Info / Uninstall / Add to Home

