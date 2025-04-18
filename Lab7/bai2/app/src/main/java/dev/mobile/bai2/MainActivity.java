package dev.mobile.bai2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.VideoView;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ArrayList<ImageData> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mImages = getImageDataList(this);

        mAdapter = new ImageAdapter(this, mImages);

        mRecyclerView.setAdapter(mAdapter);

        String[] permissions = new String[]{
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(String.format("package:%s", new Object[]{getPackageName()})));
            startActivityForResult(intent, 1);
        }
        if (!hasPermission(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            mImages = getImageDataList(this);
            Log.w(TAG, "onCreate: " + mImages.toString());
            mAdapter.notifyDataSetChanged();
        }

        mAdapter.setOnImageListener(position -> {
            Log.w(TAG, "onImageClick: " + mImages.get(position).getPath());
//                new intent open image cliked
            ImageData imageData = mImages.get(position);
            show(MainActivity.this, imageData.getPath());
        });
    }

    private void show(Context context, String path) {
        if (path.endsWith(".mp4") || path.endsWith(".3gp") || path.endsWith(".avi") || path.endsWith(".wmv")) {
            // Nếu là video, tạo một VideoView để hiển thị video trên AlertDialog
            VideoView videoView = new VideoView(context);
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();

            // Create AlertDialog để hiển thị video
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(videoView);
            builder.setPositiveButton("OK", null);
            builder.show();
        } else {
            // Nếu là ảnh, sử dụng Glide để load ảnh từ đường dẫn và hiển thị trên ImageView trên AlertDialog
            ImageView imageView = new ImageView(context);
            imageView.setImageURI(Uri.parse(path));

            // Create AlertDialog để hiển thị ảnh
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(imageView);
            builder.setPositiveButton("OK", null);
            builder.show();
        }
    }

    private boolean hasPermission(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (Environment.isExternalStorageManager()) {
                    mImages = getImageDataList(this);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "onActivityResult: " + "Permission Denied");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mImages = getImageDataList(this);
                mAdapter.notifyDataSetChanged();
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mImages = getImageDataList(this);
                mAdapter.notifyDataSetChanged();
            }

            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                mImages = getImageDataList(this);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.btnDelete ) {

//                create dialog confirm to delete
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete");
            builder.setMessage("Are you sure to delete?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                mAdapter.deleteItemSelected();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();

        }else{
            super.onOptionsItemSelected(item);
        }
        return true;
    }

    private ArrayList<ImageData> getImageDataList(Context context) {
        ArrayList<ImageData> imageData = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.DURATION
        };
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                sortOrder
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                int mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
                long dateModified = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION));

                boolean isVideo = mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

                imageData.add(new ImageData(id, path, isVideo, mimeType, dateModified, duration));
            }
            cursor.close();
        }
        return imageData;
    }
}
