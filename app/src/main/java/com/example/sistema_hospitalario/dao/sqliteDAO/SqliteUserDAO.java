package com.example.sistema_hospitalario.dao.sqliteDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.sistema_hospitalario.dao.listeners.OnUsersReceivedListener;
import com.example.sistema_hospitalario.manager.DataBaseManager;
import com.example.sistema_hospitalario.dao.UserDAO;
import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements UserDAO {

    private static final String TAG = "USER DAO";
    private DataBaseManager db;

    public SqliteUserDAO(Context context) {
        db = DataBaseManager.getInstance(context);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        ContentValues values = new ContentValues();
        values.put("firstName", userDTO.getFirstName());
        values.put("lastName", userDTO.getLastName());
        values.put("dni", userDTO.getDni());
        values.put("userName", userDTO.getUserName());
        values.put("email", userDTO.getEmail());
        values.put("password", userDTO.getPassword());
        values.put("medical_license", userDTO.getMedical_license());

        db.getWritableDatabase().insert("user", null, values);
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
            ContentValues values = new ContentValues();
            values.put("firstName", userDTO.getFirstName());
            values.put("lastName", userDTO.getLastName());
            values.put("dni", userDTO.getDni());
            values.put("userName", userDTO.getUserName());
            values.put("email", userDTO.getEmail());
            values.put("password", userDTO.getPassword());
            values.put("medical_license", userDTO.getMedical_license());

            String whereClause = "id=?";
            String[] whereArgs = { userId };

            db.getWritableDatabase().update("user", values, whereClause, whereArgs);
    }

    @Override
    public void deleteUser(String userId, UserDTO userDTO) {
        String whereClause = "id=?";
        String[] whereArgs = { userId };

        db.getWritableDatabase().delete("user", whereClause, whereArgs);
    }

    @Override
    public void getUser(String userId, OnUserReceivedListener listener) {
        String query = "SELECT * FROM user WHERE id=?";
        String[] whereArgs = { userId };

        Cursor cursor = db.getReadableDatabase().rawQuery(query, whereArgs);
        UserDTO user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new UserDTO(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("userName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("medical_license"))
            );
            cursor.close();
        }

        UserDTO finalUser = user;
        Log.d(TAG, "getUser: " + finalUser.toString());
    }

    @Override
    public void getAllUsers(OnUsersReceivedListener listener) {
        String query = "SELECT * FROM user";
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        List<UserDTO> users = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                UserDTO user = new UserDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("userName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medical_license"))
                );
                users.add(user);
            }
            cursor.close();
        }

        Log.d(TAG, "getAllUsers: " + users);
    }
}
