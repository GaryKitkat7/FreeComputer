package alexplanasobany7.freecomputer;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {// implements AdapterView.OnItemClickListener {

    private Button b4x5, b4x4, b5x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b4x4 = (Button)findViewById(R.id.Classe4x4);
        b4x5 = (Button)findViewById(R.id.Classe4x5);
        b5x4 = (Button)findViewById(R.id.Classe5x4);
    }

    public void EntraClasse (View view){
        Intent intent = new Intent(this, InteriorClasses.class);
        startActivity(intent);
    }
}

