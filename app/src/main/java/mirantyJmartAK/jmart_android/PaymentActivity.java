package mirantyJmartAK.jmart_android;

import static mirantyJmartAK.jmart_android.FirstFragment.clickedProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import mirantyJmartAK.jmart_android.model.Account;
import mirantyJmartAK.jmart_android.model.Payment;
import mirantyJmartAK.jmart_android.model.Product;
import mirantyJmartAK.jmart_android.model.Shipment;
import mirantyJmartAK.jmart_android.request.PaymentRequest;

/**
 * Payment Activity is the page for payment once a product is selected.
 * Once payment is successful, page will move the main menu again.
 *
 * @author Miranty Anjani Putri
 */

public class PaymentActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Payment payment = null;
    private int count = 1;
    Account account = LoginActivity.getLoggedAccount();
    Product product = FirstFragment.clickedProduct;

    private double discounted = (product.price)*((100.0-product.discount)/100.0);

    public static Payment getLoggedPayment(){
        return payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView productName = findViewById(R.id.productPaymentName);
        TextView productWeight = findViewById(R.id.productPaymentWeight);
        TextView productCondition = findViewById(R.id.productPaymentCondition);
        TextView productPrice = findViewById(R.id.productPaymentPrice);
        TextView productDiscount = findViewById(R.id.productPaymentDiscount);
        TextView productCategory = findViewById(R.id.productPaymentCategory);
        TextView productShipmentPlan = findViewById(R.id.productPaymentShipment);
        TextView shipmentCost = findViewById(R.id.productPaymentShipmentCost);
        TextView totalPrice = findViewById(R.id.productPaymentTotal);

        EditText productCount = findViewById(R.id.productPaymentQuantity);
        EditText shipmentAddress = findViewById(R.id.paymentShipmentAddress);

        productName.setText(clickedProduct.name);
        productWeight.setText(String.valueOf(clickedProduct.weight));
        productCondition.setText(convertConditionUsed(clickedProduct.conditionUsed));
        productPrice.setText("Rp " + clickedProduct.price);
        productDiscount.setText(String.valueOf(clickedProduct.discount) + " %");
        productCategory.setText(String.valueOf(clickedProduct.category));
        productShipmentPlan.setText(convertShipmentPlans(clickedProduct.shipmentPlans));
        shipmentCost.setText("Rp 9.000,00");

        Button buyButton = findViewById(R.id.payButton);

        buyButton.setOnClickListener(new View.OnClickListener() {
            Response.ErrorListener errorListener = resp -> Toast.makeText(PaymentActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();
            @Override
            public void onClick(View view) {
                String buyerAddress = shipmentAddress.getText().toString();
                String countProduct = productCount.getText().toString();
                count = Integer.parseInt(countProduct);

                totalPrice.setText("Rp" + (9000.0 + ((product.price * count) - (discounted * count))));
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                payment = gson.fromJson(object.toString(), Payment.class);
                                System.out.println(payment);
                                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                                LoginActivity.getLoggedAccount().balance -= clickedProduct.price*Double.parseDouble(productCount.getText().toString())*(1 - clickedProduct.discount/100);
                                Toast.makeText(PaymentActivity.this,"Payment Berhasil!",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            Toast.makeText(PaymentActivity.this,"Payment Gagal!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        System.out.println(payment);
                    }
                };
                PaymentRequest requestPayment = new PaymentRequest(Integer.parseInt(productCount.getText().toString()), buyerAddress, clickedProduct.shipmentPlans, listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
                requestQueue.add(requestPayment);
            }
        });
    }

    private String convertShipmentPlans(byte shipment){
        switch (shipment) {
            case 0:
                return "INSTANT";
            case 1:
                return "SAME DAY";
            case 2:
                return "NEXT DAY";
            case 3:
                return "REGULER";
            default:
                return "CARGO";
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

    private String convertConditionUsed(boolean conditionUsed){
        if (conditionUsed) {
            return "Used";
        }else{
            return "New";
        }
    }
}