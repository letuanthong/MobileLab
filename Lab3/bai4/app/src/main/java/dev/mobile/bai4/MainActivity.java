package dev.mobile.bai4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "contact";
    private ImageView editPen;
    private ImageView profile;
    private TextView name;
    private TextView phone;
    private TextView mail;
    private TextView address;
    private TextView homepage;
    private TextView nameEdit;
    private TextView job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPen = findViewById(R.id.imageViewPen);
        profile = findViewById(R.id.imageViewProfile);
        name = findViewById(R.id.textView9);
        phone = findViewById(R.id.textView10);
        mail = findViewById(R.id.textView11);
        address = findViewById(R.id.textView12);
        homepage = findViewById(R.id.textView13);
        nameEdit = findViewById(R.id.textViewName);
        job = findViewById(R.id.textViewJob);

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra(EditActivity.EXTRA_CONTACT);

        if (contact != null) {
            nameEdit.setText(contact.getUserName());
            job.setText(contact.getJob());
            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            mail.setText(contact.getMail());
            address.setText(contact.getAddress());
            homepage.setText(contact.getHomepage());
        }

        Bitmap photo = (Bitmap) intent.getParcelableExtra(EditActivity.EXTRA_PHOTO);
        if (photo != null) {
            profile.setImageBitmap(photo);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(view);
            }
        };

        editPen.setOnClickListener(listener);
    }
    public void edit(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        Contact contact = new Contact(
                nameEdit.getText().toString(),
                job.getText().toString(),
                name.getText().toString(),
                phone.getText().toString(),
                mail.getText().toString(),
                address.getText().toString(),
                homepage.getText().toString()
        );
        intent.putExtra(EXTRA_CONTACT, contact);
        startActivity(intent);
    }
}