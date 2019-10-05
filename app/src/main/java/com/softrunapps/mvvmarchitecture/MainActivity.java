package com.softrunapps.mvvmarchitecture;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdapter();
        initRecyclerView();
        initSwipe();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.submitList(users);
            }
        });

    }

    private void initSwipe() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                userViewModel.delete(userAdapter.getUser(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(userAdapter);
    }

    private void initAdapter() {
        userAdapter = new UserAdapter();

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
