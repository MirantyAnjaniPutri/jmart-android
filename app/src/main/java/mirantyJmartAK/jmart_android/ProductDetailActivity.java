package mirantyJmartAK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Product Detail Activity is the page to display product info
 * once a product / item is selected from the First Fragment
 * (fragment for products).
 *
 * @author Miranty Anjani Putri
 */

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        TextView productName = findViewById(R.id.productDetailName);
        TextView productWeight = findViewById(R.id.productDetailWeight);
        TextView productCondition = findViewById(R.id.productDetailCondition);
        TextView productPrice = findViewById(R.id.productDetailPrice);
        TextView productDiscount = findViewById(R.id.productDetailDiscount);
        TextView productCategory = findViewById(R.id.productDetailCategory);
        TextView productShipmentPlan = findViewById(R.id.productDetailShipment);

        productName.setText(FirstFragment.clickedProduct.name);
        productWeight.setText(String.valueOf(FirstFragment.clickedProduct.weight));
        productCondition.setText(convertConditionUsed(FirstFragment.clickedProduct.conditionUsed));
        productPrice.setText("Rp " + String.valueOf(FirstFragment.clickedProduct.price));
        productDiscount.setText(String.valueOf(FirstFragment.clickedProduct.discount) + " %");
        productCategory.setText(String.valueOf(FirstFragment.clickedProduct.category));
        productShipmentPlan.setText(convertShipmentPlans(FirstFragment.clickedProduct.shipmentPlans));

        Button buyButton = findViewById(R.id.buyButton);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
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

    private String convertConditionUsed(boolean conditionUsed){
        if (conditionUsed) {
            return "Used";
        }else{
            return "New";
        }
    }
}