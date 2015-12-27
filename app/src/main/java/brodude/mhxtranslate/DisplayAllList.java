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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DisplayAllList extends AppCompatActivity {
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
    public TextView textView;
    public TableLayout tblLayout;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_list);
        intent = getIntent();
        String title = "";
        String allSkills = "";
        String type = "";
        String search = "";
        switch (intent.getStringExtra(MainActivity.SCREEN_MESSAGE)) {
            case "Character skills":
                title = intent.getStringExtra(MainActivity.CHAR_LENGTH) + " " + intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                type = "skill";
                search = "char";
                break;
            case "Items list":
                title = intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                type = "item";
                search = "char";
                break;
            case "PSkill list":
                title = "Palico Skills List";
                type = "pskill";
                search = "char";
                break;
            case "HArt list":
                title = "Hunter Arts List";
                type = "hart";
                search = "char";
                break;
            case "KSkill list":
                title = "Kitchen Skill List";
                type = "kskill";
                search = "char";
                break;
            case "KQuest list":
                title = "Key Quest List";
                type = "kquest";
                search = "all";
                break;
            case "MonWeak list":
                title = "Monster Weakness List";
                type = "monweak";
                search = "all";
                break;
            case "PArt list":
                title = "Palico Arts List";
                type = "part";
                search = "all";
                break;
        }
        skillList = (HashMap<String, String>)intent.getSerializableExtra(MainActivity.SKILLS_HASH);
        for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
            int resID = getResources().getIdentifier("spin" + (i+1), "id", "brodude.mhxtranslate");
            spinners[i] = (Spinner) findViewById(resID);
            charArrayLists[i] = new ArrayList<Character>();
            spinners[i].setVisibility(View.INVISIBLE);
        }
        TableLayout tblUpdated = (TableLayout) findViewById(R.id.tblLayout2);
        tblUpdated.removeAllViews();
        TreeMap<String, ArrayList<String>> sorted = new TreeMap<>();
        for (Map.Entry<String, String> entry : skillList.entrySet()) {
            if (sorted.containsKey(entry.getValue())) {
                ArrayList<String> temp = new ArrayList<>(sorted.get(entry.getValue()));
                temp.add(entry.getKey());
                Collections.sort(temp);
                sorted.put(entry.getValue(), temp);

            }
            else {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(entry.getKey());
                sorted.put(entry.getValue(), temp);
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : sorted.entrySet()) {
            TableRow tblRow = new TableRow(tblUpdated.getContext());
            tblRow.setClickable(true);
            tblRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView myTextview = new TextView(tblUpdated.getContext());
            myTextview.setText(entry.getKey());
            myTextview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tblRow.addView(myTextview);
            tblUpdated.addView(tblRow);

            for (String list : entry.getValue()){
                TableRow tblRowtmp = new TableRow(tblUpdated.getContext());
                tblRowtmp.setClickable(true);
                tblRowtmp.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TextView myTextviewtmp = new TextView(tblUpdated.getContext());
                myTextviewtmp.setText(list);
                myTextviewtmp.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tblRowtmp.addView(myTextviewtmp);
                tblUpdated.addView(tblRowtmp);
            }
        }
        /*//spinner1 = (Spinner) findViewById(R.id.spin1);
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
                if (allSkills.equals("")){
                    allSkills = entry.getKey() + " - " + entry.getValue();
                }
                else {
                    allSkills += "\n " + entry.getKey() + " - " + entry.getValue();
                }
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
        //spinner2.setAdapter(adp2);*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        //textView = (TextView) findViewById(R.id.textView);
        //textView.setTextSize(22);
        //textView.setText(allSkills);
        //textView.setMovementMethod(new ScrollingMovementMethod());
        //onSelect();
    }

    /*protected void onSelect() {
        for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
            spinners[i].setAdapter(adps[i]);
            spinners[i].setOnItemSelectedListener(new MyOnItemSelectedListener());
        }
        //spinner1.setAdapter(adp1);
        //spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //spinner2.setAdapter(adp2);
        //spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }*/
}
