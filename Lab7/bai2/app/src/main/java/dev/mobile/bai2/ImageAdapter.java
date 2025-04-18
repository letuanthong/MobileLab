package dev.mobile.bai2;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final String TAG = "ImagaAdapter";
    private ArrayList<ImageData> mImages;
    private Context context;
    private SparseBooleanArray mSelectedItems;
    private OnImageListener mOnImageListener;
    public void setOnImageListener(OnImageListener onImageListener) {
        this.mOnImageListener = onImageListener;
    }
    public interface OnImageListener {
        void onImageClick(int position);
    }

    public ImageAdapter(Context context, ArrayList<ImageData> images) {
        this.context = context;
        this.mImages = images;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.media_row, parent, false));
    }

    private String formatTimestamp(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
//       get day/month/year
        String day = (String) android.text.format.DateFormat.format("dd", date);
        String month = (String) android.text.format.DateFormat.format("MM", date);
        String year = (String) android.text.format.DateFormat.format("yyyy", date);
        return day + "/" + month + "/" + year;
    }
    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }
    private String getDurationString(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private boolean addFile(Context context, File file) {
        ContentResolver contentResolver = context.getContentResolver();
        String where = MediaStore.MediaColumns.DATA + "=?";
        String[] selectionArgs = new String[]{file.getAbsolutePath()};

//        add file to external storage
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.MediaColumns.SIZE, file.length());

        try {
//            add image file or video file
            Uri result = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            return result != null;
        } catch (SecurityException e) {
            PendingIntent pendingIntent = null;
            ArrayList<Uri> uris = new ArrayList<>();
            uris.add(Uri.fromFile(file));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                pendingIntent = MediaStore.createWriteRequest(context.getContentResolver(), uris);
            } else {
                if (e instanceof RecoverableSecurityException) {
                    pendingIntent = ((RecoverableSecurityException) e).getUserAction().getActionIntent();
                }
            }
            if (pendingIntent != null) {
                IntentSender intentSender = pendingIntent.getIntentSender();
                try {
                    context.startIntentSender(intentSender, null, 0, 0, 0);
                    return true;
                } catch (IntentSender.SendIntentException sendIntentException) {
                    sendIntentException.printStackTrace();
                }
            }
        }
        return false;
    }
    private boolean deleteFile(Context context, File file) {
        ContentResolver contentResolver  = context.getContentResolver();
        String where = MediaStore.MediaColumns.DATA + "=?";
        String[] selectionArgs = new String[]{file.getAbsolutePath()};
        int rows;
        try {
            // delete image or video
            rows = contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, selectionArgs);
            if (rows == 0) {
                rows = contentResolver.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, where, selectionArgs);
            }
            if (rows == 0) {
                return false;
            }
            return true;
        } catch (SecurityException e) {
            PendingIntent pendingIntent = null;
            ArrayList<Uri> uris = new ArrayList<>();
            uris.add(Uri.fromFile(file));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                pendingIntent = MediaStore.createDeleteRequest(context.getContentResolver(), uris);
            } else {
                if (e instanceof RecoverableSecurityException) {
                    pendingIntent = ((RecoverableSecurityException) e).getUserAction().getActionIntent();
                }
            }
            if (pendingIntent != null) {
                IntentSender intentSender = pendingIntent.getIntentSender();
                try {
                    context.startIntentSender(intentSender, null, 0, 0, 0);
                    return true;
                } catch (IntentSender.SendIntentException sendIntentException) {
                    sendIntentException.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ImageData image = mImages.get(position);
        Glide.with(context)
                .asBitmap()
                .load(image.getPath())
                .into(holder.imageView);

        if (image.isVideo()) {
            holder.textView.setText("Video: " + getDurationString(Long.parseLong(image.getDuration())));
        } else {
            holder.textView.setText("Image");
        }
        holder.tvAddedTime.setText(formatTimestamp(String.valueOf(image.getDateAdded())));
        holder.checkBox.setChecked(mSelectedItems.get(position, false));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedItems.get(position, false)) {
                    mSelectedItems.delete(position);
                } else {
                    mSelectedItems.put(position, true);
                }
                notifyItemChanged(position);
            }
        });
//        holder.itemView.setBackgroundColor(mSelectedItems.get(position) ? ContextCompat.getColor(context, R.color.colorAccent) : ContextCompat.getColor(context, R.color.colorPrimary));
        holder.itemView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return Math.max(mImages.size(), 0);
    }

    public void deleteItemSelected() {
        ArrayList<Integer> selectedItems = getSelectedItems();
        Log.w(TAG, "deleteItemSelected: " + selectedItems.toString() );
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            int position = selectedItems.get(i);
            ImageData imageDeleted = mImages.remove(position);
            boolean isDeleted = deleteFile(context, new File(imageDeleted.getPath()));
            Log.w(TAG, "File deleted : " + imageDeleted.toString() );

            Log.w(TAG, "File deleted : " + isDeleted );
            notifyItemRemoved(selectedItems.get(i));
        }
        notifyDataSetChanged();
        clearSelections();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, tvAddedTime;
        CheckBox checkBox;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            tvAddedTime = itemView.findViewById(R.id.tv_date_added);
            checkBox = itemView.findViewById(R.id.check_box);
        }

        @Override
        public void onClick(View v) {
            if (mOnImageListener != null) {
                int position = getAdapterPosition();
                mOnImageListener.onImageClick(position);
            }
        }
    }
}

