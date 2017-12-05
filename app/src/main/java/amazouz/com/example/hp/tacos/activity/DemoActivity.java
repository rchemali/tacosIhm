package amazouz.com.example.hp.tacos.activity;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class DemoActivity extends Activity implements RecognitionListener {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SpeechRecognizer speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());


        speech.setRecognitionListener(this);

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getApplication().getPackageName());

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,3);

        super.onCreate(savedInstanceState);
    }

    @Override

    public void onBeginningOfSpeech() {

    }

    @Override

    public void onBufferReceived(byte[] buffer) {

    }

    @Override

    public void onEndOfSpeech() {

    }

    @Override

    public void onError(int errorCode) {

        switch (errorCode) {

            case SpeechRecognizer.ERROR_AUDIO:


                break;

            case SpeechRecognizer.ERROR_CLIENT:


                break;

            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:


                break;

            case SpeechRecognizer.ERROR_NETWORK:


                break;

            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:


                break;

            case SpeechRecognizer.ERROR_NO_MATCH:


                break;

            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:


                break;

            case SpeechRecognizer.ERROR_SERVER:


                break;

            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:


                break;

            default:


                break;
        }
    }

    @Override

    public void onEvent(int arg0, Bundle arg1) {
    }

    @Override

    public void onPartialResults(Bundle arg0) {
    }

    @Override

    public void onReadyForSpeech(Bundle arg0) {
    }

    @Override

    public void onResults(Bundle results) {

        ArrayList<String> matches = results

                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Toast.makeText(getApplicationContext(),matches.get(0),Toast.LENGTH_LONG).show();

    }

    @Override

    public void onRmsChanged(float rmsdB) {

    }
}