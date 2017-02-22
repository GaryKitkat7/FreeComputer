package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReservarPC extends AppCompatActivity {
    private Ordenadors itemDetallat;
    private ImageView imatgePC;
    private TextView textImatge;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_pc);
        pos = getIntent().getExtras().getInt("pos");

        imatgePC = (ImageView)findViewById(R.id.imagen_extendida);
        textImatge = (TextView)findViewById(R.id.titoltext);

        itemDetallat = Ordenadors.ITEMS[pos];
        imatgePC.setImageResource(itemDetallat.getIdDrawable());
        textImatge.setText(itemDetallat.getNombre());
    }

    public void reservar(View v) {
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
    }
}
