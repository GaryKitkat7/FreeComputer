package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: NO FUNCIONA, ES SUPERPOSA EL MAPA PRINCIPAL
        /*TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("MAPA"));
        tabLayout.addTab(tabLayout.newTab().setText("LLISTA"));*/

        final MapaPrincipal mapaPrincipal = new MapaPrincipal(this);
        ScrollView scrollView = new ScrollView(this);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);

        horizontalScrollView.addView(scrollView);
        scrollView.addView(mapaPrincipal);
        setContentView(horizontalScrollView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_opcions,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.Mapa) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }else{
            Intent intent = new Intent(this, LlistaClassesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}


