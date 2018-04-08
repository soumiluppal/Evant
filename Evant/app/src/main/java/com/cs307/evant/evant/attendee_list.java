package com.cs307.evant.evant;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by Soun Kim on 2018-04-08.
 */

public class attendee_list extends AppCompatActivity {
   public void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.attendee_list);

       TextView tvTitle = findViewById(R.id.eventTitle);
       TextView tvList = findViewById(R.id.attendeeList);
       String namesStr = "";

       String title = getIntent().getStringExtra("title");
       ArrayList<String> list = getIntent().getStringArrayListExtra("list");


       for(int a=0; a < list.size(); a++){
           namesStr = namesStr + db.getName(list.get(a)) + "\n";
       }


       tvTitle.setText(title);
       tvList.setText(namesStr);
   }
}
