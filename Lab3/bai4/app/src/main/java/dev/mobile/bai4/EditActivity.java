package dev.mobile.bai4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "contact";
    public static final int CAMERA_REQUEST = 13;
    public static final String EXTRA_PHOTO = "photo";
    private ImageView profile;
    private EditText name;
    private EditText phone;
    private EditText mail;
    private EditText address;
    private EditText homepage;
    private TextView nameEdit;
    private EditText JobEdit;
    private Contact contact;
    private ImageView camera;
    private Button button;
    private Bitmap Avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra(MainActivity.EXTRA_CONTACT);

        camera = findViewById(R.id.imageViewcamera);
        profile = findViewById(R.id.imageViewProfileEdit);

        nameEdit = findViewById(R.id.textViewName);
        JobEdit = findViewById(R.id.editTextJob);
        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        mail = findViewById(R.id.editTextMail);
        address = findViewById(R.id.editTextAddress);
        homepage = findViewById(R.id.editTextHomepage);
        button = findViewById(R.id.buttonSave);

        nameEdit.setText(contact.getUserName());
        JobEdit.setText(contact.getJob());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        mail.setText(contact.getMail());
        address.setText(contact.getAddress());
        homepage.setText(contact.getHomepage());

        button.setOnClickListener(view -> {
            save(view);
        });

        camera.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });

        // nameEdit lay tu name cua contact, thay thế khoảng trắng bằng dấu gạch ngang,
        // viết thường

    }

    public void save(View view) {
        nameEdit.setText(name.getText().toString().toLowerCase().replaceAll("\\s", "_"));
        contact.setUserName(nameEdit.getText().toString());
        contact.setName(name.getText().toString());
        contact.setJob(JobEdit.getText().toString());
        contact.setMail(mail.getText().toString());
        contact.setAddress(address.getText().toString());
        contact.setHomepage(homepage.getText().toString());
        contact.setPhone(phone.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_CONTACT, contact);
        //intent.putExtra(EXTRA_PHOTO, ((BitmapDrawable) profile.getDrawable()).getBitmap());
        intent.putExtra(EXTRA_PHOTO, Avatar);
        startActivity(intent);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Avatar = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(Avatar);
        }
    }
}
