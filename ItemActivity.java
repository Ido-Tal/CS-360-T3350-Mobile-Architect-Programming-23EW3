package com.example.iteminventoryapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "com.winters.invtracker.item";
    private InventoryDatabase databaseItems;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private int[] colorItems;
    private Item selectedItems;
    private int itemPosition = RecyclerView.NO_POSITION;
    private ActionMode actionMode = null;
    ArrayList<Item> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        colorItems = getResources().getIntArray(R.array.color_item);
        databaseItems = InventoryDatabase.getInstance(getApplicationContext());
        recyclerView = findViewById(R.id.itemRecyclerView);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        allItems = (ArrayList<Item>) databaseItems.getItems();
        itemAdapter = new ItemAdapter(databaseItems.getItems());
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter = new ItemAdapter(databaseItems.getItems());
        recyclerView.setAdapter(itemAdapter);
    }


    public void addItemClick(View view) {
        Intent intent = new Intent(ItemActivity.this, ItemEditActivity.class);
        startActivity(intent);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private Item anItem;
        private final TextView itemNameText;
        private final TextView itemQuantityText;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemNameText = itemView.findViewById(R.id.itemText);
            itemQuantityText = itemView.findViewById(R.id.itemQuantity);
        }

        public void bind(Item item, int position) {
            anItem = item;
            itemNameText.setText(item.getDescription());
            itemQuantityText.setText(item.getQuantity());

            if (itemPosition == position) {
                itemNameText.setBackgroundColor(Color.GRAY);
            } else {
                int colorIndex = item.getDescription().length() % colorItems.length;
                itemNameText.setBackgroundColor(colorItems[colorIndex]);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (actionMode != null) {
                return false;
            }
            selectedItems = anItem;
            itemAdapter.notifyItemChanged(itemPosition);
            actionMode = ItemActivity.this.startActionMode(mActionModeCallback);
            return true;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ItemActivity.this, ItemActivity.class);
            intent.putExtra(ItemActivity.EXTRA_ITEM, anItem.getDescription());
            startActivity(intent);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private final List<Item> itemList;
        public ItemAdapter(List<Item> items) {
            itemList = items;
        }

        public void removeItem(Item item) {
            int index = itemList.indexOf(item);
            if (index >= 0) {
                itemList.remove(index);
                notifyItemRemoved(index);
            }
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            holder.bind(itemList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }
    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete) {
                databaseItems.deleteItem(selectedItems);
                itemAdapter.removeItem(selectedItems);
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            itemAdapter.notifyItemChanged(itemPosition);
            itemPosition = RecyclerView.NO_POSITION;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(ItemActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}