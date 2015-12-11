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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayCharList extends AppCompatActivity {
    public static Intent intent;
    public static Spinner[] spinners = new Spinner[MainActivity.SPINNER_COUNT];
    //public static Spinner spinner1;
    //public static Spinner spinner2;
    ArrayList<Character>[] charArrayLists = new ArrayList[MainActivity.SPINNER_COUNT];
    //ArrayList<Character> charArrayList1;
    //ArrayList<Character> charArrayList2;
    ArrayAdapter<Character>[] adps = new ArrayAdapter[MainActivity.SPINNER_COUNT];
    //ArrayAdapter<Character> adp1;
    //ArrayAdapter<Character> adp2;
    public static HashMap<String, String> skillList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_char_list);
        intent = getIntent();
        String allSkills = "";
        String type = "";
        switch (intent.getStringExtra(MainActivity.SCREEN_MESSAGE)) {
            case "Character skills":
                allSkills = intent.getStringExtra(MainActivity.CHAR_LENGTH) + " " + intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                type = "skill";
                break;
            case "Items list":
                allSkills = intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                type = "item";
                break;
        }
        skillList = (HashMap<String, String>)intent.getSerializableExtra(MainActivity.SKILLS_HASH);
        for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
            int resID = getResources().getIdentifier("spin" + (i+1), "id", "brodude.mhxtranslate");
            spinners[i] = (Spinner) findViewById(resID);
            charArrayLists[i] = new ArrayList<Character>();
        }
        //spinner1 = (Spinner) findViewById(R.id.spin1);
        //spinner2 = (Spinner) findViewById(R.id.spin2);
        //charArrayList1 = new ArrayList<Character>();
        //charArrayList2 = new ArrayList<Character>();
        if (skillList.isEmpty()) {
            allSkills += "\nNo matches found!";
        }
        else {
            for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
                charArrayLists[i].add(' ');
            }
            for (Map.Entry<String, String> entry : skillList.entrySet()) {
                allSkills += "\n " + entry.getKey() + " - " + entry.getValue();
                for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
                    if (i < entry.getKey().length()) {
                        if (!charArrayLists[i].contains(entry.getKey().charAt(i))) {
                            charArrayLists[i].add(entry.getKey().charAt(i));
                        }
                    }
                    else {
                        if (type.equals("skill")) {
                            spinners[i].setVisibility(View.INVISIBLE);
                        }
                    }
                }
                //if (!charArrayList1.contains(entry.getKey().charAt(0))) {
                //    charArrayList1.add(entry.getKey().charAt(0));
                //}
                //if (!charArrayList2.contains(entry.getKey().charAt(1))) {
                //    charArrayList2.add(entry.getKey().charAt(1));
               // }
            }
        }
        for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
            adps[i] = new ArrayAdapter<Character>(this,R.layout.spinner_item, charArrayLists[i]);
            spinners[i].setAdapter(adps[i]);
        }
        //adp1 = new ArrayAdapter<Character>(this,android.R.layout.simple_spinner_item, charArrayList1);
        //adp2 = new ArrayAdapter<Character>(this,android.R.layout.simple_spinner_item, charArrayList2);
        //spinner1.setAdapter(adp1);
        //spinner2.setAdapter(adp2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(22);
        textView.setText(allSkills);
        textView.setMovementMethod(new ScrollingMovementMethod());
        onSelect();
    }

    protected void onSelect() {
        for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
            spinners[i].setAdapter(adps[i]);
            spinners[i].setOnItemSelectedListener(new MyOnItemSelectedListener());
        }
        //spinner1.setAdapter(adp1);
        //spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //spinner2.setAdapter(adp2);
        //spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }
}
