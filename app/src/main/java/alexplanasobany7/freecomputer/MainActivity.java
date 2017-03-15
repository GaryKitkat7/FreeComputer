package alexplanasobany7.freecomputer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button Sala008;
    private TextView texte;
    private int Prova;
    private String sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Prova = R.integer.nCol;
        Log.d("AlexPLANA", String.valueOf(Prova));
        //texte = (TextView)findViewById(R.id.textView2);
        Sala008 = (Button)findViewById(R.id.Classe008);
        sala = Sala008.getText().toString();

    }

    public void EntraClasse (View view){
        Intent intent = new Intent(this, InteriorClasses.class);
        intent.putExtra("sala", (Sala008.getText().toString()));
        startActivity(intent);
    }

}


