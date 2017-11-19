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
            case "pain normal":
                iv1.setImageResource(R.drawable.painnormal);
               break;
            case "libanais":
                iv1.setImageResource(R.drawable.libanais);
                break;
            case "galette":
                iv1.setImageResource(R.drawable.galette);
                break;

        }
        switch(c.getSauce()){
            case "SAMURAI":
                iv2.setImageResource(R.drawable.sam);
                break;
            case "ALGERIENNE":
                iv2.setImageResource(R.drawable.alg);
                break;
            case "BLANCHE":
                iv2.setImageResource(R.drawable.bla);
                break;
            case "CURRY":
                iv2.setImageResource(R.drawable.cur);
                break;
            case "HARISSA":
                iv2.setImageResource(R.drawable.har);
                break;

        }
        switch(c.getViande()){
            case "POULET":
                iv3.setImageResource(R.drawable.painnormal);
                break;
            case "MERGEZ":
                iv3.setImageResource(R.drawable.painnormal);
                break;
            case "TUNDERS":
                iv3.setImageResource(R.drawable.painnormal);
                break;
            case "NEGETS":
                iv3.setImageResource(R.drawable.painnormal);
                break;
            case "POULET-CURRY":
                iv3.setImageResource(R.drawable.painnormal);
                break;


        }
        switch(c.getBoisson()){
            case "EAU":
                iv4.setImageResource(R.drawable.eau);
                break;
            case "COCA-COLA":
                iv4.setImageResource(R.drawable.coca);
                break;
            case "OASIS":
                iv4.setImageResource(R.drawable.oasis);
                break;
            case "FANTA":
                iv4.setImageResource(R.drawable.fanta);
                break;
            case "ORANGINA":
                iv4.setImageResource(R.drawable.or);
                break;
            case "7UP":
                iv4.setImageResource(R.drawable.up);
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
        {
            c=new Commande((String) b.get("pain"),(String) b.get("sauce"),(String) b.get("viande"),(String) b.get("boisson"));

        }
        iv1=(ImageView)findViewById(R.id.imageView4);
        iv2=(ImageView)findViewById(R.id.imageView2);
        iv3=(ImageView)findViewById(R.id.imageView5);
        iv4=(ImageView)findViewById(R.id.imageView3);

        TextView tv=(TextView)findViewById(R.id.textView2);

        tv.setTextSize(15);
        tv.setText(tv.getText()+" "+c.getPain()+" "+c.getSauce()+" "+c.getViande()+" "+c.getBoisson());

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
