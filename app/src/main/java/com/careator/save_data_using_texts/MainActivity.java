package com.careator.save_data_using_texts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button clear_data_btn;
    Button show_data_btn;
    EditText etname;
    EditText etsurname;
    TextView textview,textview1;
    ArrayList<Values> person_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview1=findViewById(R.id.textview1);
        show_data_btn=findViewById(R.id.show_data_btn);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        etname = findViewById(R.id.etname);
        etsurname = findViewById(R.id.etsurname);
        textview = findViewById(R.id.textview);
        clear_data_btn = findViewById(R.id.clear_data_btn);
        person_val = new ArrayList<>();

        textview1.setVisibility(View.VISIBLE);
        textview.setVisibility(View.GONE);

        loadData();

        show_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setVisibility(View.VISIBLE);
                textview1.setVisibility(View.GONE);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddData();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveData();
            }
        });

        clear_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });


    }

    private void clearData() {
        try {
            FileOutputStream file = openFileOutput("Data4.txt", MODE_PRIVATE);
            if (file != null) {
                OutputStreamWriter outputfile = new OutputStreamWriter(file);
                outputfile.write("");
                outputfile.close();
                Toast.makeText(this, "Cleared Successfully", Toast.LENGTH_SHORT).show();
            }
            person_val.clear();
            textview.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btnAddData() {

        String name = etname.getText().toString().trim();
        String surname = etsurname.getText().toString().trim();

        Values values = new Values(name, surname);

        person_val.add(values);

        textview1.setVisibility(View.GONE);
        textview.setVisibility(View.VISIBLE);

        setTextToTextView();
    }

    private void setTextToTextView() {
        int i;
        String text = "";
        if (person_val != null && person_val.size() > 0) {
            for (i = 0; i < person_val.size(); i++) {
                text = text + person_val.get(i).getName() + " " + person_val.get(i).getSurname() + "\n";

            }
        }
        textview.setText(text);
    }

    public void loadData() {
        //person_val.clear();
        File file = getApplicationContext().getFileStreamPath("Data4.txt");
        String lineFromFile;

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data4.txt")));

                while ((lineFromFile = reader.readLine()) != null) {
                    StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");
                    Values value = new Values(tokens.nextToken(), tokens.nextToken());
                    person_val.add(value);
                }
                reader.close();

                setTextToTextView();

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void btnSaveData() {

        try {
            FileOutputStream file = openFileOutput("Data4.txt", MODE_PRIVATE);
            OutputStreamWriter outputfile = new OutputStreamWriter(file);
            if (person_val != null && person_val.size() > 0) {
                for (int i = 0; i < person_val.size(); i++) {
                    outputfile.write(person_val.get(i).getName() + "," + person_val.get(i).getSurname() + "\n");
                    outputfile.flush();
                }
            }
            outputfile.close();
            textview.setVisibility(View.GONE);
            textview1.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



}

