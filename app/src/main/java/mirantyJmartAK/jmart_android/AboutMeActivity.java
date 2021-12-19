package mirantyJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import mirantyJmartAK.jmart_android.model.Account;
import mirantyJmartAK.jmart_android.model.Store;
import mirantyJmartAK.jmart_android.request.LoginRequest;
import mirantyJmartAK.jmart_android.request.RegisterRequest;
import mirantyJmartAK.jmart_android.request.StoreRequest;
import mirantyJmartAK.jmart_android.request.TopUpRequest;

/**
 * The app page that will display about the user's account details.
 *
 * @author Miranty Anjani Putri
 */
public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Visbilitynya gone
        Button registerStoreGede = findViewById(R.id.buttonRegisterStore);
        CardView registerAStore = findViewById(R.id.RegisterAStore);
        CardView storeTerdaftar = findViewById(R.id.ViewRegisteredStore);

        //Visible dan account details
        TextView userName = findViewById(R.id.aboutMeUserName);
        TextView userEmail = findViewById(R.id.aboutMeUserEmail);
        TextView userBalance = findViewById(R.id.aboutMeUserBalance);
        EditText TopUp = findViewById(R.id.topUpAmount);
        Button topUpButton = findViewById(R.id.buttonTopUp);

        //Account Details
        Account loggedUser = LoginActivity.getLoggedAccount();
        userName.setText(loggedUser.name);
        userEmail.setText(loggedUser.email);
        userBalance.setText("Rp " + loggedUser.balance);

        //RegisterAStore
        EditText storeName = findViewById(R.id.RegisterStoreName);
        EditText storeAddress = findViewById(R.id.RegisterStoreAddress);
        EditText storePhoneNumber = findViewById(R.id.RegisterStorePhoneNumber);
        Button registerStoreButton = findViewById(R.id.registerStoreButton);
        Button cancelStoreReg = findViewById(R.id.cancelRegisterStore);

        //Registered Store CardView
        TextView registeredStoreName = findViewById(R.id.storeName);
        TextView registeredStoreAddress = findViewById(R.id.storeAddress);
        TextView registeredStorePhoneNumber = findViewById(R.id.storePhoneNumber);

        registerStoreGede.setVisibility(View.GONE);
        registerAStore.setVisibility(View.GONE);
        storeTerdaftar.setVisibility(View.GONE);

        if (LoginActivity.getLoggedAccount().store == null) {
            registerStoreGede.setVisibility(View.VISIBLE);
        }
        else {
            registeredStoreName.setText("" + LoginActivity.getLoggedAccount().store.name);
            registeredStoreAddress.setText("" + LoginActivity.getLoggedAccount().store.address);
            registeredStorePhoneNumber.setText("" + LoginActivity.getLoggedAccount().store.phoneNumber);
            storeTerdaftar.setVisibility(View.VISIBLE);
        }

        registerStoreGede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStoreGede.setVisibility(View.GONE);
                registerAStore.setVisibility(View.VISIBLE);
                cancelStoreReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerAStore.setVisibility(View.GONE);
                        registerStoreGede.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        registerStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            LoginActivity.getLoggedAccount().store = gson.fromJson(object.toString(), Store.class);
                            System.out.println(LoginActivity.getLoggedAccount().store);
                            Toast.makeText(AboutMeActivity.this, "Store Created!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        } catch (JSONException e) {
                            Toast.makeText(AboutMeActivity.this, "Create Store Failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                StoreRequest request = new StoreRequest(LoginActivity.getLoggedAccount().id, storeName.getText().toString(), storeAddress.getText().toString(), storePhoneNumber.getText().toString(), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(request);
            }
        });

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Boolean.parseBoolean(response)) {
                            Toast.makeText(AboutMeActivity.this, "Top Up Berhasil!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AboutMeActivity.this, "Top Up Gagal!", Toast.LENGTH_SHORT).show();
                        }
                        LoginActivity.getLoggedAccount().balance += Double.parseDouble(TopUp.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                };
                TopUpRequest topUpRequest = new TopUpRequest(LoginActivity.getLoggedAccount().id, Double.parseDouble(TopUp.getText().toString()), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topUpRequest);
            }
        });

    }
}