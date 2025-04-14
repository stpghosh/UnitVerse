package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner categorySpinner, fromUnit, toUnit;
    EditText inputValue;
    TextView result;

    String[] categories = {"Length", "Weight", "Temperature"};
    String[] lengthUnits = {"Meter", "Kilometer", "Centimeter", "Millimeter"};
    String[] weightUnits = {"Gram", "Kilogram", "Pound"};
    String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        inputValue = findViewById(R.id.inputValue);
        result = findViewById(R.id.result);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

        updateUnitSpinners(lengthUnits);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = categories[position];
                if (selected.equals("Length")) {
                    updateUnitSpinners(lengthUnits);
                } else if (selected.equals("Weight")) {
                    updateUnitSpinners(weightUnits);
                } else if (selected.equals("Temperature")) {
                    updateUnitSpinners(tempUnits);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateUnitSpinners(String[] units) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);
    }

    public void convert(View view) {
        String inputStr = inputValue.getText().toString().trim();

        if (inputStr.isEmpty()) {
            result.setText("Please enter a value");
            return;
        }

        double input;
        try {
            input = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            result.setText("Invalid number format");
            return;
        }

        String category = categorySpinner.getSelectedItem().toString();
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();

        double output;

        switch (category) {
            case "Length":
                output = convertLength(input, from, to);
                break;
            case "Weight":
                output = convertWeight(input, from, to);
                break;
            case "Temperature":
                output = convertTemperature(input, from, to);
                break;
            default:
                result.setText("Invalid category");
                return;
        }

        result.setText(String.format("%.2f %s", output, to));
    }

    private double convertLength(double input, String from, String to) {
        double inMeters = input;

        if (from.equals("Kilometer")) {
            inMeters = input * 1000;
        } else if (from.equals("Centimeter")) {
            inMeters = input / 100;
        } else if (from.equals("Millimeter")) {
            inMeters = input / 1000;
        }

        if (to.equals("Kilometer")) {
            return inMeters / 1000;
        } else if (to.equals("Centimeter")) {
            return inMeters * 100;
        } else if (to.equals("Millimeter")) {
            return inMeters * 1000;
        } else {
            return inMeters;
        }
    }

    private double convertWeight(double input, String from, String to) {
        double inGrams = input;

        if (from.equals("Kilogram")) {
            inGrams = input * 1000;
        } else if (from.equals("Pound")) {
            inGrams = input * 453.592;
        }

        if (to.equals("Kilogram")) {
            return inGrams / 1000;
        } else if (to.equals("Pound")) {
            return inGrams / 453.592;
        } else {
            return inGrams;
        }
    }

    private double convertTemperature(double input, String from, String to) {
        double celsius = input;

        if (from.equals("Fahrenheit")) {
            celsius = (input - 32) * 5 / 9;
        } else if (from.equals("Kelvin")) {
            celsius = input - 273.15;
        }

        if (to.equals("Fahrenheit")) {
            return (celsius * 9 / 5) + 32;
        } else if (to.equals("Kelvin")) {
            return celsius + 273.15;
        } else {
            return celsius;
        }
    }
}
