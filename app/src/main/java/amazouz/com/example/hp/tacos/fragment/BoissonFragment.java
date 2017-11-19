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
import amazouz.com.example.hp.tacos.activity.CommandActivity;

/**
 * Created by HP on 10/11/2017.
 */

public class BoissonFragment extends android.support.v4.app.Fragment {
    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;
    private String boisson="";
    private String viande="";
    private String pain;
    private String sauce;
    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "EAU":
                iv.setImageResource(R.drawable.eau);
                return;
            case "COCA-COLA":
                iv.setImageResource(R.drawable.coca);
                return ;
            case "OASIS":
                iv.setImageResource(R.drawable.oasis);
                return ;
            case "FANTA":
                iv.setImageResource(R.drawable.fanta);
                return ;
            case "ORANGINA":
                iv.setImageResource(R.drawable.or);
                return ;
            case "7UP":
                iv.setImageResource(R.drawable.up);
                return ;

        }

    }
    public static String choix(int a){
        switch(a){
            case 0:
                return"EAU";
            case 1:
                return"COCA-COLA";
            case 2:
                return"OASIS";
            case 3:
                return"FANTA";
            case 4:
                return"ORANGINA";
            case 5:
                return"7UP";

        }
        return "";
    }
    public void affichage(int a,String pain,String sauce,String  viande){
        boisson=choix(a);
        this.viande=viande;
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
        choiximg(iv,boisson);
        tx.setText(boisson);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy=new Intent(getContext(),CommandActivity.class);
                toy.putExtra("pain",pain);
                toy.putExtra("sauce",sauce);
                toy.putExtra("viande",viande);
                toy.putExtra("boisson",boisson);
                startActivity(toy);
            }
        });

        return view ;
    }
}
