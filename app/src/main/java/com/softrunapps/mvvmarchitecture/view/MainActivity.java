package com.softrunapps.mvvmarchitecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softrunapps.mvvmarchitecture.R;
import com.softrunapps.mvvmarchitecture.adapter.UserAdapter;
import com.softrunapps.mvvmarchitecture.UserViewModel;
import com.softrunapps.mvvmarchitecture.repository.room.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_USER_REQUEST = 1;
    public static final int EDIT_USER_REQUEST = 2;

    UserViewModel userViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFloatingButton();
        initAdapter();
        initRecyclerView();
        initSwipe();
        initViewModel();

    }

    private void initViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.submitList(users);
            }
        });
    }

    private void initFloatingButton() {
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_button_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST);
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
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.USER_ID, user.getId());
                intent.putExtra(AddEditActivity.USERNAME, user.getUsernsme());
                intent.putExtra(AddEditActivity.EMAIL, user.getEmail());
                startActivityForResult(intent, EDIT_USER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
            String username = data.getStringExtra(AddEditActivity.USERNAME);
            String email = data.getStringExtra(AddEditActivity.EMAIL);

            User user = new User(username, email);
            userViewModel.insert(user);

            Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_USER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditActivity.USER_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "User can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String username = data.getStringExtra(AddEditActivity.USERNAME);
            String email = data.getStringExtra(AddEditActivity.EMAIL);
            
            User user = new User(username, email);
            user.setId(id);
            
            userViewModel.update(user);

            Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
