package alexplanasobany7.freecomputer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button Sala008;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sala008 = (Button)findViewById(R.id.Classe008);
    }


    public void EntraClasse (View view){
        Intent intent = new Intent(this, InteriorClasses.class);
        intent.putExtra("sala", (Sala008.getText().toString())); //TODO: S'ha de cambiar, el 008 l'ha de llegir del MySQL
        startActivity(intent);
    }


}

