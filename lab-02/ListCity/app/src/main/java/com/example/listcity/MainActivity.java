package com.example.listcity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // UI
    ListView cityList;
    EditText inputCity;
    Button btnAdd;
    Button btnDelete;
    Button btnConfirm;

    // data and adapter
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    // track which row is selected (-1 = None)
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // find views
        cityList = findViewById(R.id.city_list);
        inputCity = findViewById(R.id.input_city);
        btnAdd = findViewById(R.id.btn_add_city);
        btnDelete = findViewById(R.id.btn_delete_city);
        btnConfirm = findViewById(R.id.btn_confirm);

        // initial data
        String[] cities = {"Edmonton", "Calgary", "Vancouver", "Toronto", "Seattle", "New York", "Atlanta"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // adapter (uses content.xml as the row layout)
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // select a row for deletion
        cityList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            selectedPosition = position;
            parent.setSelection(position);
        });

        // ADD CITY (focus the input and show keyboard)
        btnAdd.setOnClickListener(v -> {
            inputCity.requestFocus();
            inputCity.setSelection(inputCity.getText().length()); // moves the cursor to the end of whatever text is already in the box
            showKeyboard(inputCity);
        });

        // CONFIRM (add typed city if valid)
        btnConfirm.setOnClickListener(v -> addCity());

        // also handle keyboard "Done" to add city
        inputCity.setOnEditorActionListener((tv, actionId, event) -> {
            boolean imeDone = (actionId == EditorInfo.IME_ACTION_DONE);
            boolean enterKey = (event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN); // checks if the user pressed the physical Enter key (on a hardware keyboard or some virtual keyboards)
            if (imeDone || enterKey) {
                addCity();
                return true;
            }
            return false;
        });

        // DELETE (remove the selected row)
        btnDelete.setOnClickListener(v -> {
            if (selectedPosition < 0 || selectedPosition >= dataList.size()) {
                Toast.makeText(this, "Tap a city first to select it", Toast.LENGTH_SHORT).show();
                return;
            }
            String removed = dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            selectedPosition = -1;
            Toast.makeText(this, "Removed: " + removed, Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addCity() {
        String name = inputCity.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataList.contains(name)) {
            Toast.makeText(this, "City already in the list", Toast.LENGTH_SHORT).show();
            return;
        }
        dataList.add(name);
        cityAdapter.notifyDataSetChanged(); // without this line, the new city wouldn’t appear until you restarted the activity
        inputCity.setText("");
        hideKeyboard(inputCity);
    }

    private void showKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}