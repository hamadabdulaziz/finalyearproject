package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileActivity extends AppCompatActivity {

    EditText editText;
    SpeechRecognizer mspeechRecognizaerIntent;
    Intent MsppechRecognizaerIntent;
    TextToSpeech t1;
    Button btn;
    Button button;
    Button button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        checkPermission();

        final EditText editText = findViewById(R.id.editText);
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);


        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    editText.setText(matches.get(0));

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask(){
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run(){
                        String  str = editText.getText().toString();
                        if(str.equals("hello")){
                            editText.setText("Hello, how can i help you");
                            t1.speak("Hello, how can i help you", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what is your name")){
                            editText.setText("My name is jarvis");
                            t1.speak("My name is jarvis", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("how are you")){
                            editText.setText("I m a robot, i am never tired");
                            t1.speak("I m a robot, i am never tired", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what can I ask you")){
                            editText.setText("you can ask me anything to help you");
                            t1.speak("you can ask me anything to help you", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("tell me a joke")){
                            editText.setText("do you know what tomato said to her friend? catch up");
                            t1.speak("do you know what tomato said to her friend? catch up", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("do you like Trump")){
                            editText.setText("i will never consider him as a role model");
                            t1.speak("i will never consider him as a role model", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what is the best country in the world")){
                            editText.setText("Kuwait");
                            t1.speak("Kuwait", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if (str.equals("send message")|| str.equals("opening messages")){
                            t1.speak("opening messages",TextToSpeech.QUEUE_FLUSH, null,null);
                            Intent i = new Intent(getApplicationContext(), SendMessage.class);
                            startActivity(i);
                        }
                        if (str.equals("open call")|| str.equals("opening call")){
                            t1.speak("opening call",TextToSpeech.QUEUE_FLUSH, null,null);
                            Intent i = new Intent(getApplicationContext(), call.class);
                            startActivity(i);
                        }

                        if (str.equals("share location")|| str.equals("opening share location")){
                            t1.speak("opening share location",TextToSpeech.QUEUE_FLUSH, null,null);
                            Intent i = new Intent(getApplicationContext(),ShareLocation.class);
                            startActivity(i);
                        }


                    }
                },1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    t1.setLanguage(Locale.US);
                }
            }
        });

        findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("you will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editText.setText("");
                        editText.setHint("listening........");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }

                return false;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCommands();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOffline();
            }
        });


    }

    private void openCommands() {
        Intent intent = new Intent(this,Commands.class);
        startActivity(intent);
    }

    private void openOffline() {
                Intent intent = new Intent(this,SendMessage.class);
                startActivity(intent);

    }




    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

}