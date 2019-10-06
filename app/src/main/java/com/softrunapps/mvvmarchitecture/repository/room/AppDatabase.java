package com.softrunapps.mvvmarchitecture.repository.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = User.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedDbAsyncTask(instance.userDao()).execute();
        }
    };

    private static class PopulatedDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        public PopulatedDbAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insert(new User("john", "johon@gmail.com"));
            userDao.insert(new User("sara", "sara@gmail.com"));
            userDao.insert(new User("cris", "rcis@gmail.com"));
            userDao.insert(new User("rose", "rose@gmail.com"));
            return null;
        }
    }
}
