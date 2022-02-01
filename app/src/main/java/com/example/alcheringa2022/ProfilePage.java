package com.example.alcheringa2022;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.alcheringa2022.Model.YourOrders_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ProfilePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView name;
    ImageView user_dp;
    ImageView edit_dp_button;
    Button save_button;
    ImageButton back_btn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;

    ArrayList<String> interests;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.user_name);
        user_dp = findViewById(R.id.profile_image);
        edit_dp_button = findViewById(R.id.edit_dp_button);
        save_button = findViewById(R.id.SaveBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);

        interests = new ArrayList<>();

        fill_user_details();

        edit_dp_button.setOnClickListener(v -> CropImage.startPickImageActivity(ProfilePage.this));

        save_button.setOnClickListener(v -> {
            syncWithFirebase();
            Toast.makeText(this,"Your changes are saved",Toast.LENGTH_LONG).show();
            //uploadImage();
        });

        back_btn = findViewById(R.id.backbtn);
        back_btn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageURI = CropImage.getPickImageResultUri(this, data);
            startCrop(imageURI);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                user_dp.setImageURI(result.getUri());
                uploadImage();
            }
        }
    }

    private void startCrop(Uri imageURI) {
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    private void fill_user_details() {
        String shared_name = sharedPreferences.getString("name", "");
        String shared_photoUrl = sharedPreferences.getString("photourl", "");

        if (!shared_name.equals("") && !shared_photoUrl.equals("")) {
            name.setText(shared_name);
            Glide.with(this).load(shared_photoUrl).into(user_dp);
        } else {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            assert user != null;
            String email = user.getEmail();
            assert email != null;

            firestore.collection("USERS").document(email).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", task.getResult().getString("Name"));
                    String nameString = task.getResult().getString("Name");
                    if(nameString != null && !nameString.isEmpty()){
                        name.setText(nameString);
                    }

                    String db_photourl = task.getResult().getString("PhotoURL");

                    if (db_photourl != null) {
                        Glide.with(this).load(db_photourl).into(user_dp);
                        editor.putString("photourl", task.getResult().getString("PhotoURL"));
                    }
                    editor.apply();

                    interests = (ArrayList<String>) task.getResult().get("interests");
                    Log.d("TAG", "The users' interests are: " + interests);
                    setInterests();

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            });
        }
    }

    private void uploadImage() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        assert email != null;

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Users/" + email);
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        Log.d("Image", "Success!!");
                        progressDialog.dismiss();
                        Toast.makeText(ProfilePage.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(ProfilePage.this, "Profile Update Failed", Toast.LENGTH_SHORT).show();
                        Log.d("Image", "Failed!!");
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });

            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                Uri downloadUrl = uri;

                firestore.collection("USERS").document(email).update("PhotoURL", downloadUrl.toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("photourl", downloadUrl.toString());
                editor.apply();
            });
        }
    }

    public void interestItemOnClick(View v) {
        TextView t = (TextView) v;

        int color = t.getCurrentTextColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        if (hexColor.equals("#FFFFFF")) {
            t.setBackgroundResource(R.drawable.interests_highlighted);
            t.setTextColor(Color.parseColor("#EE6337"));
            interests.add(t.getText().toString());
        } else {
            t.setBackgroundResource(R.drawable.interests);
            t.setTextColor(Color.parseColor("#FFFFFF"));
            interests.remove(t.getText().toString());
        }

    }

    public void syncWithFirebase() {
        String email = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        String id = firestore.collection("USERS").document().getId();
        Map<String, Object> data = new HashMap<>();

        data.put("interests", interests);

        assert email != null;
        firestore.collection("USERS").document(email).set(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TAG", "Interests added to firebase");
            } else {
                Toast.makeText(getApplicationContext(), "Some Error Occurred while adding interests",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setInterests() {
        LinearLayout parent_l = findViewById(R.id.interests_layout);
        Log.d("TAG", "setInterests: we are here");
        for (int i = 0; i < parent_l.getChildCount(); i++) {
            LinearLayout l = (LinearLayout) parent_l.getChildAt(i);
            for (int j = 0; j < l.getChildCount(); j++) {
                TextView t = (TextView) l.getChildAt(j);
                String interest_name = t.getText().toString();
                if(interests.contains(interest_name)){
                    t.setBackgroundResource(R.drawable.interests_highlighted);
                    t.setTextColor(Color.parseColor("#EE6337"));
                }
            }

            /*if (child instanceof ViewGroup) {
                recursiveLoopChildren((ViewGroup) child);
                // DO SOMETHING WITH VIEWGROUP, AFTER CHILDREN HAS BEEN LOOPED
            } else {
                if (child != null) {
                    // DO SOMETHING WITH VIEW
                }
            }*/
        }
    }
}