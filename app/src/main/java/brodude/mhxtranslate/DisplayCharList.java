package brodude.mhxtranslate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class DisplayCharList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String chars = intent.getStringExtra(MainActivity.CHAR_LENGTH) + " Character skills";
        HashMap<String, String> skillList = (HashMap<String, String>)intent.getSerializableExtra(MainActivity.SKILLS_HASH);
        if (skillList.isEmpty()) {
            chars += "\nNo skills found!";
        }
        else {
            for (Map.Entry<String, String> entry : skillList.entrySet()) {
                chars += "\n " + entry.getKey() + " - " + entry.getValue();
            }
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = new TextView(this);
        textView.setTextSize(22);
        textView.setText(chars);
        textView.setMovementMethod(new ScrollingMovementMethod());
        setContentView(textView);


        //setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
