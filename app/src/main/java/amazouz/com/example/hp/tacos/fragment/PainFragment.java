package amazouz.com.example.hp.tacos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.activity.SauceActivity;

/**
 * Created by HP on 08/11/2017.
 */

public class PainFragment extends Fragment{

    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;
    private String pain="";

    public static String choix(int a){
        switch(a){
            case 0:
                return"pain normal";
            case 1:
                return"libanais";
            case 2:
                return"galette";

        }
        return "";
    }
    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "pain normal":
                iv.setImageResource(R.drawable.painnormal);
                return ;
            case "libanais":
                iv.setImageResource(R.drawable.libanais);
                return ;
            case "galette":
                iv.setImageResource(R.drawable.galette);
                return ;

        }

    }

    public void affichage(int a){
       pain=choix(a);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pain1,container,false);

        but1=(LinearLayout) view.findViewById(R.id.butt1);
        tx=(TextView)view.findViewById(R.id.txtv) ;
        iv=(ImageView)view.findViewById(R.id.imageView);
        choiximg(iv,pain);
        tx.setText(pain);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy=new Intent(getContext(),SauceActivity.class);
                toy.putExtra("pain",pain);
                startActivity(toy);
            }
        });

        return view ;
    }


}
