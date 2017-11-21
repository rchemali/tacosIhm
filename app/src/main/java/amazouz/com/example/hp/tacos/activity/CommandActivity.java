package amazouz.com.example.hp.tacos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import amazouz.com.example.hp.tacos.R;
import amazouz.com.example.hp.tacos.model.Commande;

public class CommandActivity extends AppCompatActivity {
    private Commande c;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private static void recap(Commande c, ImageView iv1,ImageView iv2,ImageView iv3,ImageView iv4){
        switch(c.getPain()){

            case "Mini":
                iv1.setImageResource(R.drawable.mini);
                break;
            case "Simple":
                iv1.setImageResource(R.drawable.simple);
                break ;
            case "Double":
                iv1.setImageResource(R.drawable.doublee);
                break;
            case "Maxi":
                iv1.setImageResource(R.drawable.maxi);
                break;
            case "Mega":
                iv1.setImageResource(R.drawable.mega);
                break;
            case "Giga":
                iv1.setImageResource(R.drawable.gega);
                break;
        }
        switch(c.getSauce()){
            case "ALGERIENNE":
                iv2.setImageResource(R.drawable.algerienne);
                break  ;
            case "CHEEZY":
                iv2.setImageResource(R.drawable.cheezy);
                break  ;
            case "MAYONAISE":
                iv2.setImageResource(R.drawable.mayonaise);
                break  ;
            case "HARISSA":
                iv2.setImageResource(R.drawable.harissa);
                break  ;
            case "KETCHUP":
                iv2.setImageResource(R.drawable.ketchup);
                break ;

        }
        switch(c.getViande()){
            case "POULET":
                iv3.setImageResource(R.drawable.poulet);
                break ;
            case "MERGEZ":
                iv3.setImageResource(R.drawable.merguez);
                break ;
            case "ESCALOPE":
                iv3.setImageResource(R.drawable.escalope);
                break ;
            case "CORDONBLEU":
                iv3.setImageResource(R.drawable.cordonbleu);
                break ;
            case "VIANDE-HACHE":
                iv3.setImageResource(R.drawable.viande_hachee);
                break;


        }
        switch(c.getBoisson()){
            case "COCA-COLA":
                iv4.setImageResource(R.drawable.coca);
                break;
            case "OASIS-TROPICAL":
                iv4.setImageResource(R.drawable.oasis_tropical);
                break ;

            case "COCA-CHERY":
                iv4.setImageResource(R.drawable.cocacherry);
                break;
            case "7UP-MOJITO":
                iv4.setImageResource(R.drawable.upmo);
                break;

        }
        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        { c=new Commande((String) b.get("pain"),(String) b.get("sauce"),(String) b.get("viande"),(String) b.get("boisson"));


        }
        iv1=(ImageView)findViewById(R.id.imageView4);
        iv2=(ImageView)findViewById(R.id.imageView5);
        iv3=(ImageView)findViewById(R.id.imageView2);
        iv4=(ImageView)findViewById(R.id.imageView3);
        TextView tv=(TextView)findViewById(R.id.textView2);
        tv.setTextSize(15);
        tv.setText(tv.getText()+" "+c.getPain()+" "+c.getViande()+" "+c.getSauce()+" "+c.getBoisson());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action ", null).show();
            }
        });
        recap(c,iv1,iv2,iv3,iv4);

    }
}
