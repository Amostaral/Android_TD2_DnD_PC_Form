package com.example.td2_tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText pointRestantEt;
    EditText forceEt;
    EditText dexteriteEt;

    Button addStrength;
    Button removeStrength;

    Button addDex;
    Button removeDex;

    Button terminerButton;

    int strength;
    int dex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointRestantEt = findViewById(R.id.unused_points_edit_text);
        forceEt = findViewById(R.id.force_edit_text);
        dexteriteEt = findViewById(R.id.dex_edit_text);

        addStrength = findViewById(R.id.add_strength_button);
        addStrength.setOnClickListener(new AddOnePoint());
        removeStrength = findViewById(R.id.remove_strength_button);
        removeStrength.setOnClickListener(new RemoveOnePoint());

        addDex = findViewById(R.id.add_dex_button);
        addDex.setOnClickListener(new AddOnePoint());
        removeDex = findViewById(R.id.remove_dex_button);
        removeDex.setOnClickListener(new RemoveOnePoint());

        terminerButton = findViewById(R.id.finish_button);
        terminerButton.setOnClickListener(new Finish());

        Spinner genderSpinner = findViewById(R.id.gender_spinner);

        genderSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strength = getResources().getIntArray(R.array.strength)[position];
        dex = getResources().getIntArray(R.array.dex)[position];

        pointRestantEt.setText(getResources().getString(R.string.base_point));
        forceEt.setText(Integer.toString(strength));
        dexteriteEt.setText(Integer.toString(dex));

        removeDex.setEnabled(false);
        removeStrength.setEnabled(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AddOnePoint implements View.OnClickListener {

        private void updateAttribut(EditText et, Button bttRemove) {
            int valuePointsRestant = Integer.valueOf(pointRestantEt.getText().toString());
            if (valuePointsRestant > 0) {
                int newValue = valuePointsRestant - 1;
                pointRestantEt.setText(Integer.toString(newValue));

                int newStrength = Integer.parseInt(et.getText().toString()) + 1;
                et.setText(Integer.toString(newStrength));

                if (newValue == 0) {
                    addStrength.setEnabled(false);
                    addDex.setEnabled(false);
                }

                bttRemove.setEnabled(true);
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.add_strength_button) {
                updateAttribut(forceEt, removeStrength);
            } else if (id == R.id.add_dex_button) {
                updateAttribut(dexteriteEt, removeDex);
            }
        }

    }

    private class RemoveOnePoint implements View.OnClickListener {

        private void updateAttribut(EditText et, int attribut, Button btt) {
            int valuePointsRestant = Integer.valueOf(pointRestantEt.getText().toString());

            if (valuePointsRestant >= 0) {
                int newValueUnusedPoint = valuePointsRestant + 1;

                int newValueCaracPoint = Integer.parseInt(et.getText().toString()) - 1;

                if (newValueCaracPoint >= attribut) {
                    et.setText(Integer.toString(newValueCaracPoint));
                    pointRestantEt.setText(Integer.toString(newValueUnusedPoint));
                }
                addStrength.setEnabled(true);
                addDex.setEnabled(true);

                if (newValueCaracPoint == attribut) {
                    btt.setEnabled(false);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.remove_strength_button) {
                updateAttribut(forceEt, strength, removeStrength);
            } else if (id == R.id.remove_dex_button) {
                updateAttribut(dexteriteEt, dex, removeDex);
            }
        }
    }

    private class Finish implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int valuePointsRestant = Integer.valueOf(pointRestantEt.getText().toString());

            if (valuePointsRestant == 0) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.message_erreur), Toast.LENGTH_LONG).show();
            }
        }
    }
}
