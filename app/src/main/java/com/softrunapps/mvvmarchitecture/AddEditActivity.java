package com.softrunapps.mvvmarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditActivity extends AppCompatActivity {
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    private EditText usernameEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        initView();
        initParameters();
        initActionBar();
    }

    private void initParameters() {
        if (isEdit()) {
            usernameEditText.setText(getIntent().getStringExtra(USERNAME));
            emailEditText.setText(getIntent().getStringExtra(EMAIL));
        }
    }

    private void initActionBar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        if (isEdit()) {
            getSupportActionBar().setTitle("Edit User");
        } else {
            getSupportActionBar().setTitle("Add User");
        }

    }

    private void initView() {
        usernameEditText = findViewById(R.id.edit_text_username);
        emailEditText = findViewById(R.id.edit_text_email);
    }

    private boolean isEdit() {
        return getIntent().hasExtra(USER_ID);
    }

    private void saveUser() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        if (username.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(this, "username or email is empty", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        int id = getIntent().getIntExtra(USER_ID, -1);
        if (id != -1) {
            data.putExtra(USER_ID, id);
        }
        data.putExtra(USERNAME, username);
        data.putExtra(EMAIL, email);
        setResult(RESULT_OK, data);
        finish();
    }

    public static void open(Context context, User user) {
        Intent intent = new Intent(context, AddEditActivity.class);
        intent.putExtra(USER_ID, user.getId());
        intent.putExtra(USERNAME, user.getUsernsme());
        intent.putExtra(EMAIL, user.getEmail());
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_user:
                saveUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
