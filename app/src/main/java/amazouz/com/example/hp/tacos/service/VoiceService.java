package amazouz.com.example.hp.tacos.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;


import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

/**
 * Created by Igor Khrupin www.hrupin.com on 6/3/16.
 */
public class VoiceService extends Service implements
        RecognitionListener {

    private static final String LOG_TAG = VoiceService.class.getSimpleName();
    private static final String ACTION_STRING_ACTIVITY = "ToActivity";

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "nextstep";
    private static final String KWS_SEARCH2 = "preview";
    private static final String KWS_SEARCH3 = "ok";

    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "next step";
    private static final String KEYPHRASE2 = "preview step";
    private static final String KEYPHRASE3 = "ok google";



    private SpeechRecognizer recognizer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            runRecognizerSetup();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    // envoyer le message à l'activity
    private void sendBroadcast(String text) {
        Intent intentS = new Intent();
        intentS.setAction(ACTION_STRING_ACTIVITY);
        intentS.putExtra("value",text);
        sendBroadcast(intentS);
    }

    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(VoiceService.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Log.i(LOG_TAG, "Failed to init recognizer ");
                } else {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();

        if (text.equalsIgnoreCase(KEYPHRASE)) {
            switchSearch(KWS_SEARCH);
            sendBroadcast(text);
        }
        else if(text.equalsIgnoreCase(KEYPHRASE2)){
            switchSearch(KWS_SEARCH2);
            sendBroadcast(text);
        }

        else if(text.equalsIgnoreCase(KEYPHRASE3)){
            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
            switchSearch(KWS_SEARCH3);
            sendBroadcast(text);
        }else{

        }

        Log.i(LOG_TAG, "onPartialResult text=" +text);
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Log.i(LOG_TAG, "onResult text=" +text);

        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {

        if (!recognizer.getSearchName().equalsIgnoreCase(KWS_SEARCH))
            switchSearch(KWS_SEARCH);

        else if(!recognizer.getSearchName().equalsIgnoreCase(KWS_SEARCH2)){
            switchSearch(KWS_SEARCH2);
        }

        else if(!recognizer.getSearchName().equalsIgnoreCase(KWS_SEARCH3)){
            switchSearch(KWS_SEARCH3);
        }
        // ici
        Log.i(LOG_TAG, "onEndOfSpeech");
    }

    private void switchSearch(String searchName) {
        Log.i(LOG_TAG, "switchSearch searchName = " + searchName);
        recognizer.stop();

        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else if(searchName.equals(KWS_SEARCH2)){
            recognizer.startListening(searchName);
        } else if (searchName.equals(KWS_SEARCH3)) {
            recognizer.startListening(searchName);
        } else {
        }
            recognizer.startListening(searchName, 10000);

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        //recognizer.startListening(searchName, 7000);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false alarms and misses
                .setBoolean("-allphone_ci", true)  // Use context-independent phonetic search, context-dependent is too slow for mobile

                .getRecognizer();
        recognizer.addListener(this);



        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
        recognizer.addKeyphraseSearch(KWS_SEARCH2, KEYPHRASE2);
        recognizer.addKeyphraseSearch(KWS_SEARCH3, KEYPHRASE3);
       // recognizer.addGrammarSearch("command",new File(assetsDir,"command.gram"));

    }

    @Override
    public void onError(Exception error) {
        Log.i(LOG_TAG, "onError " + error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
        Log.i(LOG_TAG, "onTimeout");
    }
}

