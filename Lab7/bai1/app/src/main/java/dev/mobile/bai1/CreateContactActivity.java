package dev.mobile.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create Contact");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nameEdtx = findViewById(R.id.nameEdtx);
        TextView emailEdtx = findViewById(R.id.emailEdtx);
        TextView phoneNumberEdtx = findViewById(R.id.phoneNumberEdtx);
        Button addContactBtn = findViewById(R.id.addContactBtn);

        addContactBtn.setOnClickListener(v -> {
            String name = nameEdtx.getText().toString();
            String email = emailEdtx.getText().toString();
            String phoneNumber = phoneNumberEdtx.getText().toString();

            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Please enter data all fields", Toast.LENGTH_SHORT).show();
            } else {
                addContact(name, email, phoneNumber);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addContact(String name, String email, String phone) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                .putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Notify MainActivity that a contact was added
            setResult(RESULT_OK);
            finish();
        }
    }
}

