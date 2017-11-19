package amazouz.com.example.hp.tacos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.activity.ViandeActivity;

/**
 * Created by HP on 09/11/2017.
 */

public class SauceFragment extends android.support.v4.app.Fragment {
    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;
    private String sauce="";
    private String pain="";
    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "SAMURAI":
                iv.setImageResource(R.drawable.sam);
                return ;
            case "ALGERIENNE":
                iv.setImageResource(R.drawable.alg);
                return ;
            case "BLANCHE":
                iv.setImageResource(R.drawable.bla);
                return ;
            case "CURRY":
                iv.setImageResource(R.drawable.cur);
                return ;
            case "HARISSA":
                iv.setImageResource(R.drawable.har);
                return ;

        }

    }
    public static String choix(int a){
        switch(a){
            case 0:
                return"SAMURAI";

            case 1:
                return"ALGERIENNE";

            case 2:
                return"BLANCHE";
            case 3:
                return"CURRY";
            case 4:
                return"HARISSA";

        }
        return "";
    }
    public void affichage(int a,String pain){
        sauce=choix(a);
        this.pain=pain;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pain1,container,false);

        but1=(LinearLayout) view.findViewById(R.id.butt1);
        tx=(TextView)view.findViewById(R.id.txtv) ;
        iv=(ImageView)view.findViewById(R.id.imageView);
        choiximg(iv,sauce);
        tx.setText(sauce);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy=new Intent(getContext(),ViandeActivity.class);

                toy.putExtra("sauce",sauce);
                toy.putExtra("pain",pain);
                startActivity(toy);
            }
        });

        return view ;
    }
}
