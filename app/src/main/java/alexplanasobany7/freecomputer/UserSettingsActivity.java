package alexplanasobany7.freecomputer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        /*SharedPreferences sharedPreferences = getSharedPreferences("Configuracio", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PrefTipusPantalla","1");
        editor.putBoolean("PrefNotificacions", false);
        editor.commit();*/
    }
}
