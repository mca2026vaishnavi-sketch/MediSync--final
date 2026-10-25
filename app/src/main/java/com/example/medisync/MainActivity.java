package com.example.medisync;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); UiUtils.setup(this);

        View btnRecord=findViewById(R.id.btnRecord);
        View btnReminders=findViewById(R.id.btnReminders);
        View btnResolve=findViewById(R.id.btnResolve);
        View btnEmergency=findViewById(R.id.btnEmergency);
        View navHome = findViewById(R.id.navHome);
        View navRecords = findViewById(R.id.navRecords);
        View navReminders = findViewById(R.id.navReminders);
        View navAccount = findViewById(R.id.navAccount);
        View btnChatbot = findViewById(R.id.cardMediBot);
        View profileButton = findViewById(R.id.profileButton);
        View btnCalendar = findViewById(R.id.btnCalendar);
        View btnRemindersHeader = findViewById(R.id.btnRemindersHeader);
        EditText homeSearch = findViewById(R.id.homeSearch);

        navHome.setSelected(true);
        findViewById(R.id.cardJourney).setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, RecordActivity.class)));
        findViewById(R.id.rowMedicines).setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, MedicineListActivity.class)));
        findViewById(R.id.rowAppointments).setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, AppointmentListActivity.class)));

        profileButton.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, AccountActivity.class)));
        btnCalendar.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, AppointmentListActivity.class)));
        btnRemindersHeader.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, ReminderActivity.class)));
        homeSearch.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                String query = homeSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    String normalized = query.toLowerCase(java.util.Locale.ROOT);
                    Class<?> destination;
                    if (normalized.contains("medicine") || normalized.contains("medication") || normalized.contains("pill")) {
                        destination = MedicineListActivity.class;
                    } else if (normalized.contains("appointment") || normalized.contains("doctor") || normalized.contains("clinic")) {
                        destination = AppointmentListActivity.class;
                    } else if (normalized.contains("reminder") || normalized.contains("vaccine") || normalized.contains("follow-up")) {
                        destination = ReminderActivity.class;
                    } else {
                        destination = RecordActivity.class;
                    }
                    startActivity(new Intent(MainActivity.this, destination));
                }
                return true;
            }
            return false;
        });
        btnRecord.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    RecordActivity.class
            );
            startActivity(intent);
        });
        btnReminders.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ReminderActivity.class
            );
            startActivity(intent);
        });
        btnResolve.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ResolverActivity.class
            );
            startActivity(intent);
        });
        btnChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ChatbotActivity.class
            );
            startActivity(intent);
        });
        btnEmergency.setOnClickListener(v->{
            android.content.SharedPreferences prefs=getSharedPreferences(
                    "EmergencyContactData",
                    MODE_PRIVATE
            );
            String name = prefs.getString("contactName", "");
            String phone = prefs.getString("phoneNumber", "");

            if (phone.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Please save an emergency contact first",
                        Toast.LENGTH_LONG
                ).show();

                Intent intent = new Intent(
                        MainActivity.this,
                        EmergencyContactActivity.class
                );
                startActivity(intent);

            } else {
                Intent intent = new Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + phone)
                );
                startActivity(intent);

                Toast.makeText(
                        MainActivity.this,
                        "Emergency contact: " + name,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        // Home: stay on the current screen
        navHome.setOnClickListener(v -> {
            showMessage("You are already on Home");
        });

// Records: open Record Information
        navRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
        });

// Reminders: open the Reminders screen
        navReminders.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

// Account: open Account screen
        navAccount.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AccountActivity.class
            );
            startActivity(intent);
        });
        }
    @Override
    protected void onResume() {
        super.onResume();
        refreshHome();
    }

    /** Fills the greeting, the signed-in user's name and the next four days. */
    private void refreshHome() {
        java.util.Calendar now = java.util.Calendar.getInstance();
        int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
        String greeting = hour < 12 ? "Good morning"
                : (hour < 17 ? "Good afternoon" : "Good evening");
        ((android.widget.TextView) findViewById(R.id.tvGreeting)).setText(greeting);

        String username = getSharedPreferences("MediSyncAccount", MODE_PRIVATE)
                .getString("username", "");
        String firstName = "";
        if (!username.isEmpty()) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            android.database.Cursor cursor = dbHelper.getUserDetails(username);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String fullName = cursor.getString(
                            cursor.getColumnIndexOrThrow("name"));
                    if (fullName != null && !fullName.trim().isEmpty()) {
                        firstName = fullName.trim().split("\\s+")[0];
                    }
                }
                cursor.close();
            }
        }
        ((android.widget.TextView) findViewById(R.id.tvHomeName)).setText(
                firstName.isEmpty() ? "Welcome" : "Hi, " + firstName + " \uD83D\uDC4B");

        int[] names = {R.id.tvDayName1, R.id.tvDayName2, R.id.tvDayName3, R.id.tvDayName4};
        int[] nums = {R.id.tvDayNum1, R.id.tvDayNum2, R.id.tvDayNum3, R.id.tvDayNum4};
        java.text.SimpleDateFormat dayFormat =
                new java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault());
        for (int i = 0; i < 4; i++) {
            java.util.Calendar day = (java.util.Calendar) now.clone();
            day.add(java.util.Calendar.DAY_OF_MONTH, i);
            ((android.widget.TextView) findViewById(names[i])).setText(dayFormat.format(day.getTime()));
            ((android.widget.TextView) findViewById(nums[i])).setText(
                    String.valueOf(day.get(java.util.Calendar.DAY_OF_MONTH)));
        }
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
}

}