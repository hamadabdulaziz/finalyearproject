package com.example.helpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SendMessage extends AppCompatActivity {

    EditText txt_pNumber , txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        txt_message =(EditText) findViewById(R.id.txt_message);
        txt_pNumber = (EditText)findViewById(R.id.txt_phone_number);
    }
    //new activity
    public void getSpeechInput (View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Input", Toast.LENGTH_SHORT).show();
        }


    }
    //new activity for speech
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data !=null){

                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt_message.setText(result.get(0));
                }
                break;
        }
    }

    public void btn_send(View view) {

        int permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (permissionCheck== PackageManager.PERMISSION_GRANTED) {

            MyMessage();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},
                    0 );


        }

    }

    private void MyMessage() {

        String phoneNumber = txt_pNumber.getText().toString().trim();
        String Message = txt_message.getText().toString().trim();


        if (!txt_pNumber.getText().toString().equals("")||txt_message.getText().toString().equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,Message,null,null);

            Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this,"Please Enter number or Message",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:

                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    MyMessage();


                }

                else{
                    Toast.makeText(this,"You do not have request premission to access",Toast.LENGTH_SHORT).show();
                }

        }

    }
}