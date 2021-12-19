package mirantyJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mirantyJmartAK.jmart_android.model.Account;
import mirantyJmartAK.jmart_android.model.Product;
import mirantyJmartAK.jmart_android.model.ProductCategory;
import mirantyJmartAK.jmart_android.model.Shipment;
import mirantyJmartAK.jmart_android.request.CreateProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * The app page that will display about creating a new product,
 * if user already owns a store.
 *
 * @author Miranty Anjani Putri
 */

public class CreateProductActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static  Product product = null;

    public static Product getLoggedProduct(){
        return product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText nameInput = (EditText) findViewById(R.id.createProductName);
        EditText weightInput = (EditText) findViewById(R.id.createProductWeight);
        EditText priceInput = (EditText) findViewById(R.id.createProductPrice);
        EditText discountInput = (EditText) findViewById(R.id.createProductDiscount);
        CheckBox newCheck = (CheckBox) findViewById(R.id.createProductNew);
        CheckBox usedCheck = (CheckBox) findViewById(R.id.createProductUsed);
        Spinner category = (Spinner) findViewById(R.id.createProductCategorySpinner);
        Spinner shipmentPlans = (Spinner) findViewById(R.id.createProductShipmentSpinner);
        Button create = (Button) findViewById(R.id.buttonCreateProduct);
        Button clear = (Button) findViewById(R.id.buttonCancelCreateProduct);

        newCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    usedCheck.setChecked(false);
                }
            }
        });

        usedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    newCheck.setChecked(false);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput.setText("");
                weightInput.setText("");
                priceInput.setText("");
                discountInput.setText("");
                usedCheck.setChecked(false);
                newCheck.setChecked(false);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            product = gson.fromJson(object.toString(),Product.class);
                            Toast.makeText(CreateProductActivity.this,"Product Terdaftar",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                            startActivity(intent);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println(product);
                    }
                };
                CreateProductRequest requestProduct = new CreateProductRequest(nameInput.getText().toString(), Integer.parseInt(weightInput.getText().toString()), usedCheck.isChecked(), Double.parseDouble(priceInput.getText().toString()), Double.parseDouble(discountInput.getText().toString()), ProductCategory.valueOf(category.getSelectedItem().toString()), shipmentPlansByte(shipmentPlans.getSelectedItem().toString()), listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(requestProduct);
            }
        });
    }

    protected String convertShipmentPlans(String shipment){
        switch (shipment) {
            case "INSTANT":
                return "0";
            case "SAME DAY":
                return "1";
            case "NEXT DAY":
                return "2";
            case "REGULER":
                return "3";
            default:
                return "4";
        }
    }

    protected byte shipmentPlansByte(String SP){

        if(SP == "INSTANT")
        {
            return 1;
        }
        else if(SP == "SAME DAY")
        {
            return 2;
        }
        else if(SP == "NEXT DAY")
        {
            return 4;
        }
        else if(SP == "REGULER")
        {
            return 8;
        }
        else{ //KARGO
            return 16;
        }
    }
}