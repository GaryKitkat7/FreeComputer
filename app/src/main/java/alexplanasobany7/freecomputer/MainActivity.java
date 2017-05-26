package alexplanasobany7.freecomputer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends AppCompatActivity {

    public String[] sales;
    public int KEY_MAPA = 1;
    ProgressDialog progressDialog;
    public String[] Sales = PantallaEsperaPrincipalActivity.Sales;
    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sales = getIntent().getExtras().getStringArray("Sales");


        //TODO: NO FUNCIONA, ES SUPERPOSA EL MAPA PRINCIPAL
        /*TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("MAPA"));
        tabLayout.addTab(tabLayout.newTab().setText("LLISTA"));*/

        final MapaPrincipal mapaPrincipal = new MapaPrincipal(this,sales);
        ScrollView scrollView = new ScrollView(this);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);

        horizontalScrollView.addView(scrollView);
        scrollView.addView(mapaPrincipal);
        setContentView(horizontalScrollView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_opcions_2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if (id == R.id.Configuracio){
            Intent intent = new Intent(this, UserSettingsActivity.class);
            startActivity(intent);
        }else if (id == R.id.Refresca){
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Actualitzant...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            sales= new String[152];

            for(int z = 0; z < 3; z++){
                i = 0;
                String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
                new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                        "&sala2="+Sala2);
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask,1500);
        }else{
            Intent intent = new Intent(this, LlistaClassesActivity.class);
            intent.putExtra("Sales", sales);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(menuItem);

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.Mapa) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Sales", sales);
            startActivity(intent);
            return true;
        }else if(id == R.id.Llista){
            Intent intent = new Intent(this, LlistaClassesActivity.class);
            intent.putExtra("Sales", sales);
            startActivity(intent);

        }else {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Actualitzant...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            sales= new String[151];

            for(int z = 0; z < 3; z++){
                String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
                new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                        "&sala2="+Sala2);
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask,1500);
        }
        return super.onOptionsItemSelected(item);
    }*/

    private class ConsultarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "URL incorrecta";
            }
        }

        protected void onPostExecute(String result) {
            JSONArray ja;
            try {
                ja = new JSONArray(result);
                Log.d("JASONNNN", String.valueOf(ja.length()));
                for(int j = 0; j < ja.length(); j++){
                    String substring = ja.getString(j);
                    sales[i] = substring.substring(2,3);
                    i++;
                }

                Log.d("ALEXPLANA1", Arrays.toString(sales));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("reposta", "No ENtRAAA");
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        myurl = myurl.replace(" ", "%20");
        InputStream stream = null;
        int len = 500;
        try {
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d("reposta", "La resposta es: " + responseCode);
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();

            //Convertir el InputString a String
            String ContentAsString = readIt(stream, len);
            return ContentAsString;

        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}


