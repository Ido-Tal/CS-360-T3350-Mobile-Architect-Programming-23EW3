package com.example.iteminventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ItemEditActivity extends AppCompatActivity {

    ImageButton increase, decrease;
    EditText itemName;
    EditText itemQuantity;
    Button addItemButton;
    Boolean checker;
    String text1;
    String quantity1;
    InventoryDatabase databaseItems;
    int id_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        increase = findViewById(R.id.increaseQuantity);
        decrease = findViewById(R.id.decreaseQuantity);
        itemName = findViewById(R.id.inputItemName);
        itemQuantity = findViewById(R.id.textQuantity);
        addItemButton = findViewById(R.id.addItemClick);
        databaseItems = new InventoryDatabase(this);

        increase.setOnClickListener(view -> {
            int input = 0, total;
            String value = itemQuantity.getText().toString().trim();
            if (!value.isEmpty()) {
                input = Integer.parseInt(value);
            }
            total = input + 1;
            itemQuantity.setText(String.valueOf(total));
        });

        addItemButton.setOnClickListener(view -> addToDatabase());

        decrease.setOnClickListener(view -> {
            int input, total;
            String quantity = itemQuantity.getText().toString().trim();
            if (quantity.equals("0")) {
                Toast.makeText(this, "Quantity reached zero", Toast.LENGTH_LONG).show();
            } else {
                input = Integer.parseInt(quantity);
                total = input - 1;
                itemQuantity.setText(String.valueOf(total));
            }
        });
    }

    public void addToDatabase() {
        String message = isEditTextEmpty();
        if (!checker) {
            int id = id_temp;
            String text = text1;
            String quantity = quantity1;

            Item item = new Item(id, text, quantity);
            databaseItems.addItem(item);
            Toast.makeText(this,"Item added", Toast.LENGTH_LONG).show();

            Intent add = new Intent();
            setResult(RESULT_OK, add);
            this.finish();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public String isEditTextEmpty() {
        String message = "";
        quantity1 = itemQuantity.getText().toString().trim();
        text1 = itemName.getText().toString().trim();

        if (text1.isEmpty()) {
            itemName.requestFocus();
            checker = true;
            message = "Invalid item name";
        } else {
            checker = false;
        }
        return message;
    }
}