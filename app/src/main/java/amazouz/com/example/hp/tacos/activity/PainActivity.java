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

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import amazouz.com.example.hp.tacos.fragment.PainFragment;
import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.service.VoiceService;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        linearimages = (LinearLayout)findViewById(R.id.imagecontenair);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speakOut("bienvenu");
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

                    }

                }else if(voiceCommand.equalsIgnoreCase("preview step") || voiceCommand.equalsIgnoreCase("preview")){


                    pg=mViewPager.getCurrentItem();
                    if(pg>0){
                        pg=pg-1;
                        mViewPager.setCurrentItem(pg);
                        speechChoice(pg);

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


        dotscount=mSectionsPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for(int i=0;i<dotscount;i++){
            dots[i]=new ImageView(this);
            switch(i){
                case 0:

                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));

                    break ;}
                case 1:
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.simple));

                    break;}
                case 2:
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.doublee));

                    break;}
                case 3:

                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.maxi));

                    break;}
                case 4:
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mega));

                    break;}

            }

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            linearimages.addView(dots[i],params);
            dots[i].getLayoutParams().height=150;
            dots[i].getLayoutParams().width=150;


        }
      // dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
     // dots[0].getLayoutParams().height=250;
     //  dots[0].getLayoutParams().width=250;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for(int i =0;i<dotscount;i++){
                     //dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                    switch(i){
                        case 0:

                        {       dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                            dots[i].getLayoutParams().height=150;
                            dots[i].getLayoutParams().width=150;

                            break ;}
                        case 1:
                        {       dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                            dots[i].getLayoutParams().height=250;
                            dots[i].getLayoutParams().width=250;
                            break;}
                        case 2:
                        {  dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                            dots[i].getLayoutParams().height=150;
                            dots[i].getLayoutParams().width=150;
                            break;}
                        case 3:

                        {   dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                            dots[i].getLayoutParams().height=150;
                            dots[i].getLayoutParams().width=150;
                            break;}
                        case 4:
                        {  dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                            dots[i].getLayoutParams().height=150;
                            dots[i].getLayoutParams().width=150;
                            break;
                        }

                    }

                }
                //dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini+position+'1'));
                //dots[position].getLayoutParams().height=250;
               // dots[position].getLayoutParams().width=250;
               /* switch(position){
                    case 0:

                    {       dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mini));
                        dots[position].getLayoutParams().height=250;
                        dots[position].getLayoutParams().width=250;

                        break ;}
                    case 1:
                    {        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.simple));
                        dots[position].getLayoutParams().height=250;
                        dots[position].getLayoutParams().width=250;
                        break;}
                    case 2:
                    {  dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.doublee));
                        dots[position].getLayoutParams().height=250;
                        dots[position].getLayoutParams().width=250;
                        break;}
                    case 3:

                    {   dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.maxi));
                        dots[position].getLayoutParams().height=250;
                        dots[position].getLayoutParams().width=250;
                        break;}
                    case 4:
                    {   dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.mega));
                        dots[position].getLayoutParams().height=250;
                        dots[position].getLayoutParams().width=250;
                        break;}

                }*/



            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

                    unregisterReceiver(activityReceiver);
                    mSensorManager.unregisterListener(proximitySensorEventListener);  // new add


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

        switch (choice){

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

                                if(firstLaunch != false)
                                    pain.click(activityReceiver);
                                  // mSensorManager.unregisterListener(proximitySensorEventListener);  // new add

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
}
