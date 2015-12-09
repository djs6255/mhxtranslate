package brodude.mhxtranslate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DisplayCharList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String chars = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] skillList = intent.getStringArrayExtra(MainActivity.VALUE_LIST);
        for (String skill : skillList) {
            chars += "\n " + skill;
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(chars);
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
