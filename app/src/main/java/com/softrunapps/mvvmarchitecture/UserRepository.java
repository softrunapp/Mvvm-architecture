package com.softrunapps.mvvmarchitecture;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository implements UserDao {
    private UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public LiveData<List<User>> getAll() {
        return userDao.getAll();
    }

}
