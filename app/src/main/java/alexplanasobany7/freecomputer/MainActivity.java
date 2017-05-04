package alexplanasobany7.freecomputer;


import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static String[] EstatsSales = PantallaEsperaPrincipalActivity.sales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapaPrincipal mapaPrincipal = new MapaPrincipal(this);
        ScrollView scrollView = new ScrollView(this);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);

        horizontalScrollView.addView(scrollView);
        scrollView.addView(mapaPrincipal);
        setContentView(horizontalScrollView);

    }
}


