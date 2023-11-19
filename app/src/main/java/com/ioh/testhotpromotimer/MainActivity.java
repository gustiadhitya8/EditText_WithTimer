package com.ioh.testhotpromotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Define component variable
    TextView textWaktu, textView2;
    EditText editTextCustomerNumber, editConfigurationSeconds;
    CountDownTimer countDownTimer;
    Button buttonHotPromo, buttonConfiguration;

    //Define time seconds variable
    int timeSeconds =5000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Match component variable with component id
        textWaktu = (TextView) findViewById(R.id.textTimer);
        textView2 = (TextView) findViewById(R.id.textStatusExecuted);
        editTextCustomerNumber = (EditText) findViewById(R.id.editTextCustomerNumber);
        editConfigurationSeconds = (EditText) findViewById(R.id.editConfigurationSeconds);
        buttonHotPromo = (Button) findViewById(R.id.button);
        buttonConfiguration = (Button) findViewById(R.id.button2);

        //Set text configuration with default timer
        editConfigurationSeconds.setText((timeSeconds/1000)+"");

        //Set up on click instruction
        buttonConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= editConfigurationSeconds.getText().toString();
                int finalValue = Integer.parseInt(value);
                timeSeconds = finalValue*1000;
            }
        });

        //Set text as 0
        textWaktu.setText("0");
        //Setup onTextChange listener for auto query hot promo
        editTextCustomerNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (countDownTimer != null){
                    countDownTimer.cancel();
                }
                //textView2.setText("Hot Promo Not Yet Called");
                countDownTimer = new CountDownTimer(timeSeconds,1000) {
                    @Override
                    public void onTick(long l) {
                        textWaktu.setText(l/1000+"");
                        if (charSequence.length() == 9){
                            textView2.setText("Timer start running");
                        }else {
                            textView2.setText("Timer reset & re-run again");
                        }
                    }

                    @Override
                    public void onFinish() {
                        textView2.setText("Hot Promo Appear & Timer Stopped (API Offer Called)");
                    }
                };

                if (charSequence.length()>= 9){
                    countDownTimer.start();
                }else{
                    if (countDownTimer != null){
                        countDownTimer.cancel();
                        if (charSequence.length()>= 0) {
                            textView2.setText("Timer not running");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonHotPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                textView2.setText("Hot Promo Appear & Timer Stopped (API Offer Called)");
            }
        });
    }
}