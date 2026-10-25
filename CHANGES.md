# MediSync UI redesign - change notes

Not built or run: the environment that produced this had no Android SDK, Gradle or network.
Open in Android Studio and run `./gradlew assembleDebug`.

## Design system
- values/colors.xml, dimens.xml (+ values-land, values-sw600dp, values-sw600dp-land for side padding), styles.xml, themes.xml (light only; values-night removed)
- drawables: ms_* shapes (card, input, buttons, chips, bubbles, date tiles, badges), 30 vector icons (ic_*), ic_logo
- Old medisync_* / card_* / chart_* drawables removed (no longer referenced)

## Layouts rewritten (all view IDs and view types preserved)
Login, Register, Home, Profile/Account, Medical Records hub, Health Profile, Medical Report (+list, +item),
Medicines (add, list, item), Appointments (book, list, item), Reminders hub, Medicine Reminder,
Emergency Contact (+list, +item), MediBot chat, Health Guidance (Resolver).

## Java (UI-only edits)
- New UiUtils.java: back button wiring, system-bar/keyboard insets, bold list titles
- UiUtils.setup(this) added after setContentView in every Activity
- MainActivity: dynamic greeting, user name and next-4-days strip; listeners for 3 new home rows
- ChatbotActivity.addMessage: themed bubbles, dp padding, max width
- Resolver/HealthProfile spinners use themed item layouts
- List adapters: first line of each card is emphasised
