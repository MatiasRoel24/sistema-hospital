package com.example.sistema_hospitalario.manager;

import android.content.Context;

import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dao.listeners.OnUsersReceivedListener;
import com.example.sistema_hospitalario.dao.sqliteDAO.SqliteUserDAO;
import com.example.sistema_hospitalario.dto.UserDTO;

import java.util.concurrent.ExecutorService;

public class UserManager {
    private ExecutorService executorService;
    private SqliteUserDAO sqliteUserDAO;

    public UserManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setContext(Context context) {
        sqliteUserDAO = new SqliteUserDAO(context);
    }

    public void createUser(UserDTO user) {
        executorService.execute(() -> sqliteUserDAO.createUser(user));
    }

    public void updateUser(String userId, UserDTO user) {
        executorService.execute(() -> sqliteUserDAO.updateUser(userId, user));
    }

    public void deleteUser(String userId, UserDTO user) {
        executorService.execute(() -> sqliteUserDAO.deleteUser(userId, user));
    }

    public void getUser(String userId, OnUserReceivedListener listener) {
        executorService.execute(() -> sqliteUserDAO.getUser(userId, listener));
    }

    public void getAllUsers(OnUsersReceivedListener listener) {
        executorService.execute(() -> sqliteUserDAO.getAllUsers(listener));
    }
}
