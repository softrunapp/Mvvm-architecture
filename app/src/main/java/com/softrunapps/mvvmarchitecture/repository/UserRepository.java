package com.softrunapps.mvvmarchitecture.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.softrunapps.mvvmarchitecture.repository.room.AppDatabase;
import com.softrunapps.mvvmarchitecture.repository.room.User;
import com.softrunapps.mvvmarchitecture.repository.room.UserDao;

import java.util.List;

public class UserRepository implements UserDao {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
        allUsers = userDao.getAll();
    }

    @Override
    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    @Override
    public void update(User user) {
        new UpdateAsyncTask(userDao).execute(user);
    }

    @Override
    public void delete(User user) {
        new DeleteAsyncTask(userDao).execute(user);
    }

    @Override
    public void deleteAll() {
        new DeleteAllAsyncTask(userDao).execute();
    }

    @Override
    public LiveData<List<User>> getAll() {
        return allUsers;
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public InsertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public UpdateAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public DeleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        public DeleteAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }
}
