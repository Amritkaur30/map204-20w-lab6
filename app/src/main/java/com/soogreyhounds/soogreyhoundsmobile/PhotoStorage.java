package com.soogreyhounds.soogreyhoundsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.PhotoCursorWrapper;
import database.SooGreyhoundsDBHelper;
import database.SooGreyhoundsDBSchema;

public class PhotoStorage {
    private static PhotoStorage sPhotoStorage;
    private Context mContext;
    private List<Photo> mPhotos;
    private SQLiteDatabase mDatabase;
    public static PhotoStorage get(Context context) {
        if (sPhotoStorage == null) {
            sPhotoStorage = new PhotoStorage(context);
        }
        return sPhotoStorage;
    }
    private PhotoCursorWrapper queryPhotos(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SooGreyhoundsDBSchema.PhotoTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new PhotoCursorWrapper(cursor);
    }
    private PhotoStorage(Context context) {
        mContext = context;
        mDatabase = new SooGreyhoundsDBHelper(mContext).getWritableDatabase();
    }
    public void updatePhoto(Photo photo) {
        String uuid = photo.getUuid();
        ContentValues values = getContentValues(photo);
        mDatabase.update(SooGreyhoundsDBSchema.PhotoTable.NAME, values,
                SooGreyhoundsDBSchema.PhotoTable.Cols.UUID + " = ?",
                new String[] { uuid });
    }
    public void addPhoto(Photo p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(SooGreyhoundsDBSchema.PhotoTable.NAME, null, values);
    }
    public List<Photo> getPhotos() {
        List<Photo> photos = new ArrayList<>();
        PhotoCursorWrapper cursor = queryPhotos(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                photos.add(cursor.getPhoto());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return photos;
    }
    public Photo getPhoto(String uuid) {
        PhotoCursorWrapper cursor = queryPhotos(
                SooGreyhoundsDBSchema.PhotoTable.Cols.UUID + " = ?",
                new String[] { uuid }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPhoto();
        } finally {
            cursor.close();
        }
    }
    private static ContentValues getContentValues(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(SooGreyhoundsDBSchema.PhotoTable.Cols.UUID, photo.getUuid());
        values.put(SooGreyhoundsDBSchema.PhotoTable.Cols.TITLE, photo.getTitle());
        values.put(SooGreyhoundsDBSchema.PhotoTable.Cols.URL, photo.getUrl());
        values.put(SooGreyhoundsDBSchema.PhotoTable.Cols.NOTE, photo.getNote());
        values.put(SooGreyhoundsDBSchema.PhotoTable.Cols.PERSON, photo.getPerson());

        return values;
    }

}
