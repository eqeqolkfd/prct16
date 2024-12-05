package com.example.a05122024;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText nameEd, descriptionEd, imageNameEd;
    private Button addBtn, delBtn, updBtn;
    private ListView listView;
    private ImageView imageView;
    private ArrayAdapter<String> adapter;
    private String selectItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        nameEd = findViewById(R.id.nameText);
        descriptionEd = findViewById(R.id.descriptionText);
        imageNameEd = findViewById(R.id.imageNameText);
        addBtn = findViewById(R.id.addButton);
        updBtn = findViewById(R.id.updateButton);
        delBtn = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getItemNames());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemName = adapter.getItem(position);
                ClothingItem item = Paper.book().read(selectItemName, null);
                if (item != null) {
                    nameEd.setText(item.getName());
                    descriptionEd.setText(item.getDescription());
                    imageNameEd.setText(item.getImageName());
                    int imageResId = getResources().getIdentifier(item.getImageName(), "drawable", getPackageName());
                    Picasso.get().load(imageResId).into(imageView);
                }
            }
        });

        addBtn.setOnClickListener(v -> {
            String name = nameEd.getText().toString();
            String description = descriptionEd.getText().toString();
            String imageName = imageNameEd.getText().toString();

            if (!name.isEmpty() && !description.isEmpty() && !imageName.isEmpty()) {
                ClothingItem item = new ClothingItem(name, description, imageName);
                Paper.book().write(name, item);
                updateItemList();
                clearInputs();
            }
        });

        updBtn.setOnClickListener(v -> {
            if (selectItemName == null) {
                Toast.makeText(MainActivity.this, "Пожалуйста, сначала выберите товар", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = nameEd.getText().toString();
            String description = descriptionEd.getText().toString();
            String imageName = imageNameEd.getText().toString();

            if (!name.isEmpty() && !description.isEmpty() && !imageName.isEmpty()) {
                ClothingItem updateItem = new ClothingItem(name, description, imageName);
                Paper.book().write(selectItemName, updateItem);
                updateItemList();
                clearInputs();
            }
        });

        delBtn.setOnClickListener(v -> {
            if (selectItemName == null) {
                Toast.makeText(MainActivity.this, "Пожалуйста, сначала выберите товар", Toast.LENGTH_SHORT).show();
                return;
            }

            Paper.book().delete(selectItemName);
            updateItemList();
            clearInputs();
        });
    }

    private void clearInputs() {
        nameEd.setText("");
        descriptionEd.setText("");
        imageNameEd.setText("");
        imageView.setImageResource(0);
        selectItemName = null;
    }

    private void updateItemList() {
        adapter.clear();
        adapter.addAll(getItemNames());
        adapter.notifyDataSetChanged();
    }

    private List<String> getItemNames() {
        return new ArrayList<>(Paper.book().getAllKeys());
    }
}