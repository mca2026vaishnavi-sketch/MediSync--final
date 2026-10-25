package com.example.medisync;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Small shared helpers for the redesigned UI. They contain no business logic.
 */
public final class UiUtils {

    private UiUtils() {
    }

    /**
     * Call right after setContentView(): wires the header back button (if the
     * layout has one) and keeps content clear of the status bar, navigation bar,
     * display cutout and on-screen keyboard.
     */
    public static void setup(AppCompatActivity activity) {
        EdgeToEdge.enable(activity);
        activity.getWindow().setSoftInputMode(
                android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        View back = activity.findViewById(R.id.btnBack);
        if (back != null) {
            back.setOnClickListener(v -> activity.finish());
        }

        View content = activity.findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(content, (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout());
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(bars.left, bars.top, bars.right,
                    Math.max(bars.bottom, ime.bottom));
            return WindowInsetsCompat.CONSUMED;
        });
    }

    /** Makes the first line of a multi-line item (its title) bold and slightly larger. */
    public static CharSequence emphasizeFirstLine(CharSequence text) {
        if (text == null) {
            return "";
        }
        SpannableString spannable = new SpannableString(text);
        int end = text.toString().indexOf('\n');
        if (end < 0) {
            end = text.length();
        }
        if (end > 0) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(1.1f), 0, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    /** Converts dp to pixels. */
    public static int dp(View view, int dp) {
        return Math.round(dp * view.getResources().getDisplayMetrics().density);
    }
}
