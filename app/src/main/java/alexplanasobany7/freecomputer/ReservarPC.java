package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReservarPC extends AppCompatActivity {
    private Ordenadors itemOrdenador;
    private ImageView imatgePC;
    private TextView textImatge;
    private int pos, fila, columna;
    private Button Reserva;
    private String nomPC, sala;
    private boolean estat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_pc);
        pos = getIntent().getExtras().getInt("pos");
        fila = getIntent().getExtras().getInt("fila");
        columna = getIntent().getExtras().getInt("columna");
        nomPC = getIntent().getExtras().getString("nomPC");
        sala = getIntent().getExtras().getString("sala");
        estat = getIntent().getExtras().getBoolean("estat");


        imatgePC = (ImageView)findViewById(R.id.imagen_extendida);
        textImatge = (TextView)findViewById(R.id.titoltext);
        Reserva = (Button)findViewById(R.id.reserva);

        //itemOrdenador = Ordenadors.ITEMS[pos];

        imatgePC.setImageResource(itemOrdenador.getIdDrawable());
        textImatge.setText(nomPC);
    }

    /*public void reservar(View v) {
        if (!Ordenadors.ITEMS[pos].getReserva()) {
            Ordenadors.ITEMS[pos].PosarFotoReserva(pos);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("pos", pos);
            startActivity(intent);
        } else if (Ordenadors.ITEMS[pos].getReserva()) {
            Ordenadors.ITEMS[pos].PosarFotoNoReserva(pos);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("pos", pos);
            startActivity(intent);
        }
    }*/
}
