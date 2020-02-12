package com.soogreyhounds.soogreyhoundsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_PHOTO = 1;

    private RecyclerView mPhotoRecyclerView;
    private class PhotoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private Photo mPhoto;
        private TextView mTitleTextView;
        public PhotoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView;
        }
        public void bindPhoto(Photo photo) {
            mPhoto = photo;
            mTitleTextView.setText(photo.getTitle());

        }
        @Override
        public void onClick(View v) {
            Intent editPhotoIntent = new Intent(v.getContext(), PhotoDetailActivity.class);
            editPhotoIntent.putExtra(PhotoDetailActivity.EXTRA_UUID, mPhoto.getUuid());
            startActivityForResult(editPhotoIntent, REQUEST_PHOTO);
        }
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mPhotoRecyclerView = findViewById(R.id.photo_recycler_view);
       mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       updateList();



      /*  Button photoDetailButton = findViewById(R.id.photoDetailButton);
        photoDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PhotoDetailActivity.class);
                startActivity(intent);
            }
        });*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
            case R.id.create_account:
                Intent createAccountIntent = new Intent(this, CreateAccountActivity.class);
                startActivity(createAccountIntent);
                return true;
            case R.id.edit_profile:
                Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
                startActivity(editProfileIntent);
                return true;
            case R.id.add_photo:
                Intent addPhotoIntent = new Intent(this, PhotoDetailActivity.class);
                startActivityForResult(addPhotoIntent, REQUEST_PHOTO);
             /*   Photo photo = new Photo();
                photo.setUuid("234243-dsfsa-23sdf");
                photo.setTitle("A photo");
                photo.setUrl("https://www.test.com/Test.jpg");
                photo.setNote("This is a note");
                PhotoStorage.get(getBaseContext()).addPhoto(photo);
                updateList();*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PhotoViewerActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_photo_viewer);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<Photo> mPhotos;
        public PhotoAdapter(List<Photo> photos) {
            mPhotos = photos;
        }
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getBaseContext());
            return new PhotoHolder(textView);
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Photo photo = mPhotos.get(position);
            photoHolder.bindPhoto(photo);
        }
        @Override
        public int getItemCount() {
            return mPhotos.size();
        }
    }
    private void updateList() {
        mPhotoRecyclerView.setAdapter(new PhotoAdapter(PhotoStorage.get(this).getPhotos()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO:
                    updateList();
                    break;
            }
        }
    }
}
