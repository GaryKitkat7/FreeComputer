package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class InteriorClasses extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_classes);

        final GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new AdaptadorDeOrdenadors(this));
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ReservarPC.class);
        intent.putExtra("pos", i);
        startActivity(intent);
    }
}
