package com.example.cj.running;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.cj.tigerrun.R;

import java.util.ArrayList;

public class TrackList extends AppCompatActivity {

    public tHelper tr;
    public String[] names;
    public String[] difs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
        Toolbar tb = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(tb);

        ListView lv = (ListView) findViewById(R.id.traks);

        customAdapter customAdapter = new customAdapter();

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TrackList.this, Map.class);
                startActivity(intent);
            }
        });
    }

    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customrow, null);

            TextView tv = (TextView) view.findViewById(R.id.tv);
            TextView tv2= (TextView) view.findViewById(R.id.tv2);

            tr = new tHelper();
            names = new String[20];
            difs = new String[20];

            for(int f = 0; f < tr.t.size(); f++){
                names[i] = tr.t.get(i).getName();
                difs[i] = Integer.toString(tr.t.get(i).getDif());
            }

            tv.setText(names[i]);
            tv2.setText(difs[i]);

            return view;
        }
    }

}
