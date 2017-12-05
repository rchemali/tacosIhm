package amazouz.com.example.hp.tacos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import amazouz.com.example.hp.tacos.R;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread myThread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent intent=new Intent(getApplicationContext(),PainActivity.class);
                    startActivity(intent);
                    finish();

                }catch(InterruptedException e){
                    e.printStackTrace();

                }
            }
        };
        myThread.start();
    }
}
