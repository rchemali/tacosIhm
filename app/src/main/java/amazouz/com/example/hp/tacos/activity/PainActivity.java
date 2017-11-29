package amazouz.com.example.hp.tacos.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.UnicodeSetSpanner;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import amazouz.com.example.hp.tacos.fragment.PainFragment;
import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.service.VoiceService;
import amazouz.com.example.hp.tacos.util.Util;

import android.speech.tts.TextToSpeech;



public class PainActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PainFragment pain;

    private SensorManager mSensorManager;
    private Sensor mProximity;

    private Date date;
    private int startTime = 0, endTime = 0, numberOfSlidesPerSecond = 0;
    private boolean firstLaunch = false;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int pg=0;
    private ImageView gauche;
    private ImageView droite;

    private static final String ACTION_STRING_ACTIVITY = "ToActivity";
    BroadcastReceiver activityReceiver;

    String voiceCommand = "";
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    TextToSpeech t1 ;

    LinearLayout linearimages ;

    private int dotscount;
    private ImageView[] dots;

    ImageView imageMini , imageDouble , imageSimple , imageMaxi, imageMega ;

    ImageView btnsound ;

    // mode son
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        linearimages = (LinearLayout)findViewById(R.id.imagecontenair);


        imageMini = (ImageView)findViewById(R.id.mini);
        imageDouble = (ImageView)findViewById(R.id.Sdouble);
        imageSimple = (ImageView)findViewById(R.id.simple);
        imageMaxi = (ImageView)findViewById(R.id.maxi);
        imageMega = (ImageView)findViewById(R.id.mega);
        btnsound = (ImageView) findViewById(R.id.sound);


        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speakOut("bienvenu");
            }
        });

        // activate sound
        Util.paramsSound(getApplicationContext(),true);

        initUi();

        btnsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Util.getCurrentParams(getApplicationContext())){

                    Util.paramsSound(getApplicationContext(),false);
                    btnsound.setImageResource(R.drawable.off);

                }else{

                    Util.paramsSound(getApplicationContext(),true);
                    btnsound.setImageResource(R.drawable.on);

                }

            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        startService(new Intent(PainActivity.this, VoiceService.class));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (mProximity != null) {
            mSensorManager.registerListener(proximitySensorEventListener,
                    mProximity,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }

        activityReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                voiceCommand =intent.getStringExtra("value");

                if(voiceCommand.equalsIgnoreCase("next step") || voiceCommand.equalsIgnoreCase("next")){


                    pg=mViewPager.getCurrentItem();
                    if(pg<mSectionsPagerAdapter.getCount()-1) {
                        pg=pg + 1;
                        mViewPager.setCurrentItem(pg);
                        speechChoice(pg);
                        changeBackgroundImage(pg);

                    }

                }else if(voiceCommand.equalsIgnoreCase("preview step") || voiceCommand.equalsIgnoreCase("preview")){


                    pg=mViewPager.getCurrentItem();
                    if(pg>0){
                        pg=pg-1;
                        mViewPager.setCurrentItem(pg);
                        speechChoice(pg);
                        changeBackgroundImage(pg);


                    }

                }else if(voiceCommand.equalsIgnoreCase("finish")){

                    Toast.makeText(getApplication(),"validate",Toast.LENGTH_LONG).show();

                }


            }
        };


        if (activityReceiver != null) {

            IntentFilter intentFilter = new IntentFilter(ACTION_STRING_ACTIVITY);
            registerReceiver(activityReceiver, intentFilter);

        }

        gauche=(ImageView)findViewById(R.id.gc1);
        gauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pg=mViewPager.getCurrentItem();
                if(pg>0){
                    pg=pg-1;
                mViewPager.setCurrentItem(pg);
                    speechChoice(pg);
                    Toast.makeText(getApplicationContext(),String.valueOf(pg),Toast.LENGTH_LONG).show();
                    changeBackgroundImage(pg);


                }
            }
        });
        droite=(ImageView)findViewById(R.id.dr1);
        droite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg=mViewPager.getCurrentItem();
                if(pg<mSectionsPagerAdapter.getCount()-1) {
                        pg=pg + 1;
                    mViewPager.setCurrentItem(pg);
                    speechChoice(pg);
                    Toast.makeText(getApplicationContext(),String.valueOf(pg),Toast.LENGTH_LONG).show();
                    changeBackgroundImage(pg);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           // return PlaceholderFragment.newInstance(position + 1);

            pain=new PainFragment();

            pain.fragmentexchange = new PainFragment.fragmentexchange() {
                @Override
                public void onclick() {

                    try{
                        unregisterReceiver(activityReceiver);
                        mSensorManager.unregisterListener(proximitySensorEventListener);
                    }catch (Exception e){

                    }

                }
            };
            switch(position){
                case 0:
                    pain.affichage(0);
                    return pain;
                case 1:
                    pain.affichage(1);
                    return pain;
                case 2:
                    pain.affichage(2);
                    return pain;
                case 3:

                    pain.affichage(3);
                    return pain;
                case 4:
                    pain.affichage(4);
                    return pain;
                case 5:
                    pain.affichage(5);
                    return pain;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }


    }

    private void speakOut(String voice) {
        t1.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onDestroy();
        stopService(new Intent(PainActivity.this, VoiceService.class));
    }

    public void speechChoice(int choice){

        boolean soundIsActivate = Util.getCurrentParams(getApplicationContext());

        if(soundIsActivate) {

            switch (choice) {

                case 0:
                    speakOut("Le Mini");
                    break;

                case 1:
                    speakOut("Le Simple");
                    break;

                case 2:
                    speakOut("Le Double");
                    break;

                case 3:
                    speakOut("Le Maxi");
                    break;

                case 4:
                    speakOut("Le Méga");
                    break;
            }
        }else {

        }

    }

    public void changeBackgroundImage(int position){

        switch (position){

            case 0:

                imageMini.setAlpha((float) 1.0);
                imageSimple.setAlpha((float) 0.5);
                imageDouble.setAlpha((float) 0.5);
                imageMaxi.setAlpha((float) 0.5);
                imageMega.setAlpha((float) 0.5);


                break;

            case 1:

                imageMini.setAlpha((float) 0.5);
                imageSimple.setAlpha((float) 1.0);
                imageDouble.setAlpha((float) 0.5);
                imageMaxi.setAlpha((float) 0.5);
                imageMega.setAlpha((float) 0.5);

                break;

            case 2:

                imageMini.setAlpha((float) 0.5);
                imageSimple.setAlpha((float) 0.5);
                imageDouble.setAlpha((float) 1.0);
                imageMaxi.setAlpha((float) 0.5);
                imageMega.setAlpha((float) 0.5);

                break;

            case 3:
                imageMini.setAlpha((float) 0.5);
                imageDouble.setAlpha((float) 0.5);
                imageSimple.setAlpha((float) 0.5);
                imageMaxi.setAlpha((float) 1.0);
                imageMega.setAlpha((float) 0.5);

                break;

            case 4:
                imageMini.setAlpha((float) 0.5);
                imageDouble.setAlpha((float) 0.5);
                imageSimple.setAlpha((float) 0.5);
                imageMaxi.setAlpha((float) 0.5);
                imageMega.setAlpha((float) 1.0);


                break;

        }


    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub

            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] == 0.0) {
                    date = new Date();
                    startTime = date.getSeconds();

                }

                if (event.values[0] == 1.0) {

                    numberOfSlidesPerSecond++;

                    //Test after 500ms
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            date = new Date();
                            endTime = date.getSeconds();


                            if (endTime > startTime + 2) {
                                // ====== Validate action ======

                     //           if(firstLaunch != false)


                            } else if (numberOfSlidesPerSecond > 1) {
                                // ====== Slice twice action =======
                                pg=mViewPager.getCurrentItem();
                                if(pg>0){
                                    pg=pg-1;
                                    mViewPager.setCurrentItem(pg); // rak dayro hna !!! att atla3lfou9kamel
                                    speechChoice(pg);
                                    changeBackgroundImage(pg);
                                }

                            } else {
                                // ====== Slice once action =======
                                pg=mViewPager.getCurrentItem();
                                if(pg<mSectionsPagerAdapter.getCount()-1) {
                                    pg=pg + 1;
                                    mViewPager.setCurrentItem(pg);
                                    speechChoice(pg);
                                    changeBackgroundImage(pg);

                                }
                            }
                        }
                    }, 500);

                    Handler handlerNumberOfSlides = new Handler();
                    handlerNumberOfSlides.postDelayed(new Runnable() {
                        public void run() {
                            numberOfSlidesPerSecond = 0;
                            firstLaunch = true;
                        }
                    }, 1500);


                }

            }

        }

    };

    @Override
    protected void onResume() {

        if (activityReceiver != null) {

            IntentFilter intentFilter = new IntentFilter(ACTION_STRING_ACTIVITY);
            registerReceiver(activityReceiver, intentFilter);

        }

        if (mProximity != null) {
            mSensorManager.registerListener(proximitySensorEventListener,
                    mProximity,
                    SensorManager.SENSOR_DELAY_NORMAL
            );
        }


        super.onResume();
    }

    public void initUi(){
        boolean soundIsActivate = Util.getCurrentParams(getApplicationContext());

        if(soundIsActivate) {

            btnsound.setImageResource(R.drawable.on);

        }else{

            btnsound.setImageResource(R.drawable.off);


        }

    }

    @Override
    protected void onStop() {
        try{
            unregisterReceiver(activityReceiver);
            mSensorManager.unregisterListener(proximitySensorEventListener);  // new add
        }catch (Exception e){

        }
        super.onStop();
    }
}
