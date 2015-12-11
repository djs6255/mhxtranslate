package brodude.mhxtranslate;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static brodude.mhxtranslate.MainActivity.CHAR_LENGTH;
import static brodude.mhxtranslate.MainActivity.SPINNER_COUNT;

/**
 * Created by Darrell on 12/9/2015.
 * Implements a listener for spinners
 */
public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
    DataBaseHelper db;


    @Override
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        db = new DataBaseHelper(parent.getContext());
        db.getReadableDatabase();
        Toast.makeText(parent.getContext(), "Selected Character : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        View v = (View) view.getRootView();
        char[] selectedChars = new char[MainActivity.SPINNER_COUNT];
        if (v != null) {
            String emptyCheck = "";
            for (int i = 0; i < MainActivity.SPINNER_COUNT; i++) {
                selectedChars[i] = DisplayCharList.spinners[i].getSelectedItem().toString().charAt(0);
                emptyCheck += " ";
            }
            //selectedChars[0] = DisplayCharList.spinner1.getSelectedItem().toString().charAt(0);
            //selectedChars[1] = DisplayCharList.spinner2.getSelectedItem().toString().charAt(0);
            String selected = new String(selectedChars);
            char charVal = parent.getItemAtPosition(pos).toString().charAt(0);
            String newText = "";
            String titleText = "";
            switch (DisplayCharList.intent.getStringExtra(MainActivity.SCREEN_MESSAGE)) {
                case "Character skills":
                    titleText = DisplayCharList.intent.getStringExtra(MainActivity.CHAR_LENGTH) + " " + DisplayCharList.intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                    break;
                case "Items list":
                    titleText = DisplayCharList.intent.getStringExtra(MainActivity.SCREEN_MESSAGE);
                    break;
            }
            if (selected.equals(emptyCheck)) {
                newText = titleText;
            }
            else {
                String skillQuery = selected.replaceFirst("\\s+$", "") + "%";
                skillQuery = skillQuery.replace(" ", "_");
                Toast.makeText(parent.getContext(), "Selected Character : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                ArrayList<String> remList = new ArrayList<String>();
                HashMap<String, String> shortList = new HashMap<String, String>();
                switch (DisplayCharList.intent.getStringExtra(MainActivity.SCREEN_MESSAGE)) {
                    case "Character skills":
                        shortList = db.getSkillsByLike(skillQuery);
                        break;
                    case "Items list":
                        shortList = db.getItemsByLike(skillQuery);
                        break;
                }
                newText = titleText + " with :";
                for (Integer j = 0; j < MainActivity.SPINNER_COUNT; j++) {
                    if (selectedChars[j] != ' ') {
                        newText += "\n" + selectedChars[j] + " at position " + (j + 1) + ".";
                        //for (Map.Entry<String, String> entry : shortList.entrySet()) {
                        //    if (entry.getKey().charAt(j) != selectedChars[j]) {
                        //        remList.add(entry.getKey());
                        //        continue;
                        //    }
                       // }
                    }
                }

                /*for (String key : remList){
                    shortList.remove(key);
                }*/
                for (Map.Entry<String, String> entry : shortList.entrySet()) {
                    newText += "\n " + entry.getKey() + "\n  - " + entry.getValue();
                }
            }
            TextView updated = (TextView) v.findViewById(R.id.textView);
            updated.setText(newText);
        }
    }

    public void onNothingSelected(AdapterView parent) {

    }
}
