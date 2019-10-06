package com.softrunapps.mvvmarchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.softrunapps.mvvmarchitecture.repository.UserRepository;
import com.softrunapps.mvvmarchitecture.repository.room.User;
import com.softrunapps.mvvmarchitecture.repository.room.UserDao;

import java.util.List;

public class UserViewModel extends AndroidViewModel implements UserDao {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAll();
    }

    @Override
    public void insert(User user) {
        repository.insert(user);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public LiveData<List<User>> getAll() {
        return repository.getAll();
    }
}
