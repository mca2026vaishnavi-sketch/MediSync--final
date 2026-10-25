# MediSync — redesigned Android app

MediSync is a digital healthcare management platform designed to help users organize medical records, prescriptions, lab reports, scans, consultation details, medication schedules, appointments, reminders, and health history in one place.

## UI redesign

The home screen has been redesigned around the user's hand-drawn workflow:

- A personalized greeting and profile shortcut.
- One search field for medicines, appointments, reminders, and records.
- A pastel lavender/blue health-progress card.
- Date tiles for the user's schedule.
- Upcoming medication and medical-record shortcuts.
- Quick access to health guidance, MediBot, and emergency contact.
- Persistent bottom navigation for Home, Records, Reminders, and Profile.

The visual direction uses a soft pastel glassmorphism-inspired palette, rounded cards, subtle borders, and gentle gradients. The other existing app screens and Java data classes are retained.

## Open and run

1. Extract the ZIP file.
2. Open the `MediSync-main` folder in Android Studio.
3. Allow Gradle to sync (internet may be required to download Gradle and dependencies).
4. Select an Android emulator or connect an Android device with USB debugging enabled.
5. Press **Run** to install and launch the app.

The project uses Java, XML layouts, and the existing local database implementation.

## Notes

- The home page's calendar tiles and chart bars are visual placeholders and should be connected to live appointment/health data for production use.
- Search routes the user to a relevant existing section based on the entered keywords; it is not yet a full-text search across every stored field.
- Live doctor availability, external clinic booking, cloud synchronization, and AI-generated medical guidance require their respective service integrations.
- Reminder delivery depends on Android notification permissions and device scheduling behavior.
- This app is a personal organization aid, not a substitute for professional medical advice or emergency services.
