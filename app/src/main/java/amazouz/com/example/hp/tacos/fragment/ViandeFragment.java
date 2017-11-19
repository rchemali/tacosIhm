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
import amazouz.com.example.hp.tacos.activity.DrinkActivity;

/**
 * Created by HP on 10/11/2017.
 */

public class ViandeFragment extends android.support.v4.app.Fragment{
    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;
    private String viande="";
    private String pain;
    private String sauce;
    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "POULET":
                iv.setImageResource(R.drawable.painnormal);
                return ;
            case "MERGEZ":
                iv.setImageResource(R.drawable.painnormal);
                return ;
            case "TUNDERS":
                iv.setImageResource(R.drawable.painnormal);
                return ;
            case "NEGETS":
                iv.setImageResource(R.drawable.painnormal);
                return ;
            case "POULET-CURRY":
                iv.setImageResource(R.drawable.painnormal);
                return ;

        }

    }
    public static String choix(int a){
        switch(a){
            case 0:
                return"POULET";
            case 1:
                return"MERGEZ";
            case 2:
                return"TUNDERS";
            case 3:
                return"NEGETS";
            case 4:
                return"POULET-CURRY";

        }
        return "";
    }
    public void affichage(int a,String pain,String sauce){
        viande=choix(a);
        this.pain=pain;
        this.sauce=sauce;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pain1,container,false);

        but1=(LinearLayout) view.findViewById(R.id.butt1);
        tx=(TextView)view.findViewById(R.id.txtv) ;
        iv=(ImageView)view.findViewById(R.id.imageView);
        choiximg(iv,viande);
        tx.setText(viande);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy=new Intent(getContext(),DrinkActivity.class);
                toy.putExtra("pain",pain);
                toy.putExtra("sauce",sauce);
                toy.putExtra("viande",viande);
                startActivity(toy);
            }
        });

        return view ;
    }

}
