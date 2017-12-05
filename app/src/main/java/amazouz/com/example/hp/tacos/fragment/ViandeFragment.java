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
import amazouz.com.example.hp.tacos.activity.SauceActivity;
import amazouz.com.example.hp.tacos.activity.ViandeActivity;

/**
 * Created by HP on 10/11/2017.
 */

public class ViandeFragment extends android.support.v4.app.Fragment{
    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;
    private String viande="";
    private String pain;

    public fragmentexchange fragmentexchange = null ;

    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "POULET":
                iv.setImageResource(R.drawable.poulet);
                return ;
            case "MERGEZ":
                iv.setImageResource(R.drawable.merguez);
                return ;
            case "ESCALOPE":
                iv.setImageResource(R.drawable.escalope);
                return ;
            case "CORDONBLEU":
                iv.setImageResource(R.drawable.cordonbleu);
                return ;
            case "VIANDE-HACHE":
                iv.setImageResource(R.drawable.viande_hachee);
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
                return"ESCALOPE";
            case 3:
                return"CORDONBLEU";
            case 4:
                return"VIANDE-HACHE";

        }
        return "";
    }

    public void click(){
        Intent toy=new Intent(getContext(),SauceActivity.class);
        toy.putExtra("pain",pain);
        toy.putExtra("viande",viande);
        startActivity(toy);

    }

    public void affichage(int a,String pain){
        viande=choix(a);
        this.pain=pain;


    }

    public void nextFragment() {
        Intent toy = new Intent(getContext(), SauceActivity.class);
        toy.putExtra("pain", pain);
        toy.putExtra("viande", viande);
        startActivity(toy);
        if (fragmentexchange != null) {
            fragmentexchange.onclick();
        }
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
                Intent toy=new Intent(getContext(),SauceActivity.class);
                toy.putExtra("pain",pain);
                toy.putExtra("viande",viande);
                startActivity(toy);
                if (fragmentexchange != null) {

                    fragmentexchange.onclick();

                }
            }
        });

        return view ;
    }

    public interface fragmentexchange{

        public void onclick();

    }

}
