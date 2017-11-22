package amazouz.com.example.hp.tacos.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.fragment.PainFragment;
import amazouz.com.example.hp.tacos.fragment.ViandeFragment;
import amazouz.com.example.hp.tacos.service.VoiceService;

public class ViandeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Date date;
    private int startTime = 0, endTime = 0, numberOfSlidesPerSecond = 0;
    private boolean firstLaunch = false;
    private SectionsPagerAdapter mSectionsPagerAdapter;

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
    private String pain;

    public String getPain() {
        return pain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            pain =(String) b.get("pain");

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speakOut("Menu viande");
            }
        });

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

                Toast.makeText(getApplication(),voiceCommand,Toast.LENGTH_LONG).show();

                if(voiceCommand.equalsIgnoreCase("next step")){


                    pg=mViewPager.getCurrentItem();
                    if(pg<mSectionsPagerAdapter.getCount()-1) {
                        pg=pg + 1;
                        mViewPager.setCurrentItem(pg);
                        speechChoice(pg);

                    }

                }else if(voiceCommand.equalsIgnoreCase("preview step")){


                    pg=mViewPager.getCurrentItem();
                    if(pg>0){
                        pg=pg-1;
                        mViewPager.setCurrentItem(pg);
                        speechChoice(pg);

                    }

                }else if(voiceCommand.equalsIgnoreCase("please validate")){

                    speakOut("valider");

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

                }
            }
        });

        speakOut("poulet");

    }

    private void speakOut(String voice) {
        t1.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void speechChoice(int choice){

        switch (choice){

            case 0:
                speakOut("Poulet");
                break;


            case 1:
                speakOut("Merguez");
                break;

            case 2:
                speakOut("Escalope");
                break;

            case 3:
                speakOut("CordonBleu");
                break;

            case 4:
                speakOut("Viande haché");
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

                                if(firstLaunch != false){

                                    unregisterReceiver(activityReceiver);
                                    mSensorManager.unregisterListener(proximitySensorEventListener);

                                }

                            } else if (numberOfSlidesPerSecond > 1) {
                                // ====== Slice twice action =======
                                pg=mViewPager.getCurrentItem();
                                if(pg>0){
                                    pg=pg-1;
                                    mViewPager.setCurrentItem(pg); // rak dayro hna !!! att atla3lfou9kamel
                                    speechChoice(pg);
                                }

                            } else {
                                // ====== Slice once action =======
                                pg=mViewPager.getCurrentItem();
                                if(pg<mSectionsPagerAdapter.getCount()-1) {
                                    pg=pg + 1;
                                    mViewPager.setCurrentItem(pg);
                                    speechChoice(pg);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);


            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            ViandeFragment viande=new ViandeFragment();
            viande.fragmentexchange = new ViandeFragment.fragmentexchange() {
                @Override
                public void onclick() {
                    unregisterReceiver(activityReceiver);
                    mSensorManager.unregisterListener(proximitySensorEventListener);
                }
            };

            switch(position){
                case 0:

                    viande.affichage(0,pain);
                    return viande;
                case 1:
                    viande.affichage(1,pain);
                    return viande;
                case 2:
                    viande.affichage(2,pain);
                    return viande;
                case 3:
                    viande.affichage(3,pain);
                    return viande;
                case 4:
                    viande.affichage(4,pain);
                    return viande;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }
    }


}
