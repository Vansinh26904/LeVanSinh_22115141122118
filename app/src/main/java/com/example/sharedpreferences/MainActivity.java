package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharedpreferences.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText editTextA, editTextB;
    private TextView textViewResultValue;
    private Button buttonTong, buttonClear;
    private ListView listViewHistory;

    private ArrayList<String> historyList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        textViewResultValue = findViewById(R.id.textViewResultValue);
        buttonTong = findViewById(R.id.buttonTong);
        buttonClear = findViewById(R.id.buttonClear);
        listViewHistory = findViewById(R.id.listViewHistory);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Set<String> historySet = sharedPreferences.getStringSet("history", new HashSet<>());
        historyList = new ArrayList<>(historySet);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        listViewHistory.setAdapter(adapter);

        buttonTong.setOnClickListener(v -> {
            try {
                int a = Integer.parseInt(editTextA.getText().toString());
                int b = Integer.parseInt(editTextB.getText().toString());
                int sum = a + b;

                textViewResultValue.setText(String.valueOf(sum));

                String historyItem = a + " + " + b + " = " + sum;
                historyList.add(historyItem);
                adapter.notifyDataSetChanged();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> newHistorySet = new HashSet<>(historyList);
                editor.putStringSet("history", newHistorySet);
                editor.apply();
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonClear.setOnClickListener(v -> {
            historyList.clear();
            adapter.notifyDataSetChanged();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        });
    }
}
