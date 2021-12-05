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
import mirantyJmartAK.jmart_android.request.RegisterRequest;
import mirantyJmartAK.jmart_android.request.StoreRequest;

public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Store loggedStore = LoginActivity.getLoggedAccount().store;
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
        TextView registeredStoreName = findViewById(R.id.RegisteredStoreName);
        TextView registeredStoreAddress = findViewById(R.id.RegisteredStoreAddress);
        TextView registeredStorePhoneNumber = findViewById(R.id.RegisteredStorePhoneNumber);

        if (loggedUser.store != null) {
            storeTerdaftar.setVisibility(View.VISIBLE);
            registeredStoreName.setText(loggedUser.store.name);
            registeredStoreAddress.setText(loggedUser.store.address);
            registeredStorePhoneNumber.setText(loggedUser.store.phoneNumber);
        }
        else {
            registerStoreGede.setVisibility(View.VISIBLE);
            registerStoreGede.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerStoreGede.setVisibility(View.GONE);
                    registerAStore.setVisibility(View.VISIBLE);

                    registerStoreButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String storeNama = storeName.getText().toString();
                            String storeAdd = storeAddress.getText().toString();
                            String storePhone = storePhoneNumber.getText().toString();

                            Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject object = new JSONObject(response);
                                        if (object != null)
                                        {
                                            Toast.makeText(AboutMeActivity.this, "Register Store Success.", Toast.LENGTH_SHORT).show();
                                            loggedStore = gson.fromJson(object.toString(), Store.class);
                                            registerAStore.setVisibility(View.GONE);
                                            storeTerdaftar.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                        Toast.makeText(AboutMeActivity.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            StoreRequest storeRequest = new StoreRequest(storeName.getText().toString(), storeAddress.getText().toString(), storePhoneNumber.getText().toString(), listener, null);
                            RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                            requestQueue.add(storeRequest);
                        }
                    });

                }
            });
        }

    }
}