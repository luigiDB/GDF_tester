package it.luigidb.gdftester;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper my_db;
    private Random rng;
    private int max_question;
    private int counter = 0;
    private TextView CounterTextView;
    private List<Integer> fragmentIds = new ArrayList<Integer>();

    //to handle special_question
    private boolean storedQuestion = false;
    private int currentIndex = 0;
    private int maxIndex = 0;
    private String temporalStorageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.deleteDatabase("question.db");

        /*set up database*/
        Log.v("MAIN", "before db constructor");
        my_db = new DataBaseHelper(this);
        my_db.openDataBase();
        max_question = my_db.get_num_question();

        CounterTextView = (TextView)findViewById(R.id.counter_textView);
        //t.setText(String.format("%d", temp));

        //for testing this seed generate the first question as a special question
        //rng = new Random(max_question);
        rng = new Random();

        final Button start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MAIN", "OnClick function");

                //remove old fragment
                Log.v("MAIN[results]", "remove old fragments");
                for (int temp : fragmentIds) {
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(temp)).commit();
                }
                fragmentIds.clear();

                if (storedQuestion) {
                    Log.v("onClick", "stored");
                    currentIndex += 1;
                    if (currentIndex < (maxIndex-1)) {
                        special_question(temporalStorageId, currentIndex);
                    } else {
                        storedQuestion = false;
                        currentIndex = maxIndex = 0;
                        temporalStorageId = null;
                    }
                } else {
                    Log.v("onClick", "NOT stored");

                    int next_question = rng.nextInt(max_question + 1);
                    int type = my_db.get_type(next_question);
                    Log.v("onClick", "tipo: " + String.format("%d", type));
                    String id = my_db.get_id(next_question);
                    Log.v("onClick", "id: " + id);
                    switch (type) {
                        case 1:
                        case 2:
                            Log.v("onClick", "normal question");
                            normal_question(id);
                            break;
                        case 3:
                        case 4:
                            Log.v("onClick", "special handling");
                            storedQuestion = true;
                            currentIndex = 0;
                            temporalStorageId = id;
                            special_question(id, currentIndex);
                            break;
                    }
                }
            }
        });
    }

    private void normal_question(String id) {
        Log.v("MAIN", "normal_question");
        Cursor response = my_db.get_normal_question(id);
        QuestionFragment qf = QuestionFragment.newInstance(response);
        getSupportFragmentManager().beginTransaction().add(R.id.linear_layout, qf).commit();
        fragmentIds.add(R.id.linear_layout);
    }

    private void special_question(String id, int index) {
        Log.v("MAIN", "special_question");
        Cursor response = my_db.get_comprensione(id);
        maxIndex = response.getCount();
        Log.v("MAIN[special_question]", String.format("maxIndex: %d", maxIndex));
        ComprensioneFragment cf = ComprensioneFragment.newInstance(response, index);
        getSupportFragmentManager().beginTransaction().add(R.id.linear_layout, cf).commit();
        fragmentIds.add(R.id.linear_layout);


    }

    public void results(int response) {
        Log.v("MAIN[results]", "start");

        //disable radio group
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        for(int i = 0; i < rg.getChildCount(); i++){
            ((RadioButton)rg.getChildAt(i)).setEnabled(false);
        }

        if (response == 1) {
            Log.v("MAIN[results]", "giusto");
            counter += 1;
        } else {
            Log.v("MAIN[results]", "sbagliato");
            counter = 0;
        }
        Log.v("MAIN[results]", "setup counter");
        ((TextView)findViewById(R.id.counter_textView)).setText(String.format("%d", counter));


    }

    public void onRadioButtonClicked(View view) {
        Log.v("MAIN[onRadioButton]", "start");
        TextView responseTextView = (TextView)findViewById(R.id.response);
        String response = (String)responseTextView.getText();
        Log.v("MAIN[onRadioButton]", "response: " + response);

        switch(view.getId()) {
            case R.id.opt_a:
                if (response.equals("a"))  {
                    results(1);
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    results(0);
                }
                break;
            case R.id.opt_b:
                if (response.equals("b"))  {
                    results(1);
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    results(0);
                }
                break;
            case R.id.opt_c:
                if (response.equals("c"))  {
                    results(1);
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    results(0);
                }
                break;
            case R.id.opt_d:
                if (response.equals("d"))  {
                    results(1);
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    results(0);
                }
                break;
        }
    }
}
