package dev.mobile.bai2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isFollowing = false;
    private int followers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Generate random numbers for followers and following
        Random random = new Random();
        followers = 100 + random.nextInt(9901); // [100, 10000]
        int following = 100 + random.nextInt(9901); // [100, 10000]

        // Set the random numbers to the TextViews
        TextView followersTextView = findViewById(R.id.textViewFollowersNumber);
        TextView followingTextView = findViewById(R.id.textViewFollowingNumber);
        followersTextView.setText(String.valueOf(followers));
        followingTextView.setText(String.valueOf(following));

        Button followButton = findViewById(R.id.button);
        followButton.setOnClickListener(v -> {
            if (isFollowing) {
                followers--;
                followButton.setText("Follow");
            } else {
                followers++;
                followButton.setText("Unfollow");
            }
            isFollowing = !isFollowing;
            followersTextView.setText(String.valueOf(followers));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}