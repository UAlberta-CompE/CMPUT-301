package com.example.listycitylab3;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(int position, String name, String province) {
        City target = dataList.get(position);
        target.setName(name);
        target.setProvince(province);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities    = { "Edmonton", "Vancouver", "Toronto", "Karachi" };
        String[] provinces = { "AB", "BC", "ON", "Sindh" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // if tap a row, then open dialog in EDIT mode
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);
            AddCityFragment.newInstance(selected, position)
                    .show(getSupportFragmentManager(), "Edit City");
        });

        // if FAB, then open dialog in ADD mode
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                new AddCityFragment().show(getSupportFragmentManager(), "Add City")
        );
    }
}
