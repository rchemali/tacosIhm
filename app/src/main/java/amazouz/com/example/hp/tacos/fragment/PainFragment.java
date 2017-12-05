package amazouz.com.example.hp.tacos.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.activity.ViandeActivity;

/**
 * Created by HP on 08/11/2017.
 */

public class PainFragment extends Fragment{
    public LinearLayout but1;
    private TextView tx;
    private ImageView iv;

    public fragmentexchange fragmentexchange = null;

    private String pain="";
    public static String choix(int a){
        switch(a){
            case 0:
                return"Mini";
            case 1:
                return"Simple";
            case 2:
                return"Double";
            case 3:
                return"Maxi";
            case 4:
                return"Mega";
            case 5:
                return"Giga";

        }
        return "";
    }
    public static void choiximg(ImageView iv,String a){
        switch(a){
            case "Mini":
                iv.setImageResource(R.drawable.mini);
                return ;
            case "Simple":
                iv.setImageResource(R.drawable.simple);
                return ;
            case "Double":
                iv.setImageResource(R.drawable.doublee);
                return ;
            case "Maxi":
                iv.setImageResource(R.drawable.maxi);
                return ;
            case "Mega":
                iv.setImageResource(R.drawable.mega);
                return ;
            case "Giga":
                iv.setImageResource(R.drawable.gega);
                return ;

        }

    }

public void click(){
    Intent toy=new Intent(getContext(),ViandeActivity.class);
    toy.putExtra("pain",pain);
    startActivity(toy);

}

    public void affichage(int a){
        pain=choix(a);
    }

    public void nextFragment() {
        Intent toy = new Intent(getContext(), ViandeActivity.class);
        toy.putExtra("pain", pain);
        startActivity(toy);

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

                Intent toy=new Intent(getContext(),ViandeActivity.class);
                toy.putExtra("pain",pain);
                startActivity(toy);

                if (fragmentexchange != null) {

                    Toast.makeText(getActivity(),"ok",Toast.LENGTH_LONG).show();
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
