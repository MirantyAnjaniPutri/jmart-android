package mirantyJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import mirantyJmartAK.jmart_android.model.Account;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount () {
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.login);
        EditText email = (EditText)findViewById(R.id.loginEmail);
        EditText password = (EditText)findViewById(R.id.loginPass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals(loggedAccount.email) &&
                        password.getText().toString().equals(loggedAccount.password)) {
                    Toast.makeText(getApplicationContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    Intent moveIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(moveIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Informasi login tidak sesuai.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}