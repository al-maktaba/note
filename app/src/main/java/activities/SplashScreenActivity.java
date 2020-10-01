package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.note.R;

import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

    TextView citationTextView;
    private String[] citations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Find TextView and our array, finally set text into TextView
        citations = getResources().getStringArray(R.array.citations);
        citationTextView = findViewById(R.id.citationTextView);
        citationTextView.setText(getRandomCitationText());

        animateText();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void animateText() {
        citationTextView.setAlpha(0.0f);
        citationTextView.animate().alpha(1.0f).setDuration(600);
    }

    // method, returns random text to put in TextView
    private String getRandomCitationText(){

        int randomIndex = new Random().nextInt(citations.length);
        String randomCitationText = citations[randomIndex];
        return randomCitationText;
    }
}