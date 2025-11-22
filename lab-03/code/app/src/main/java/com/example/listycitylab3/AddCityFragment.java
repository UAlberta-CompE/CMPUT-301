package com.example.listycitylab3;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    // keys for the Bundle
    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS  = "arg_pos";

    // factory: create fragment in EDIT mode
    public static AddCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POS, position);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, String name, String province);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // determine mode (ADD vs EDIT) from arguments
        Bundle args = getArguments();
        final boolean isEdit = (args != null
                && args.containsKey(ARG_CITY)
                && args.containsKey(ARG_POS));

        int editPosition = -1;
        if (isEdit) {
            City c = (City) args.getSerializable(ARG_CITY);
            editPosition = args.getInt(ARG_POS, -1);
            if (c != null) {
                editCityName.setText(c.getName());
                editProvinceName.setText(c.getProvince());
            }
        }

        String title = isEdit ? "Edit city" : "Add a city"; // if isEdit, then Edit City, else Add a city
        String positive = isEdit ? "Save" : "Add"; // if isEdit, then Save, else Add
        final int finalEditPosition = editPosition;

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positive, (dialog, which) -> {
                    String name = editCityName.getText().toString();
                    String province = editProvinceName.getText().toString();

                    if (isEdit && finalEditPosition >= 0) {
                        listener.updateCity(finalEditPosition, name, province);
                    } else {
                        listener.addCity(new City(name, province));
                    }
                })
                .create();
    }
}