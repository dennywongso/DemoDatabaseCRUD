package com.example.a15017096.demodatabasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etContent;
    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    ArrayList<Note> al;
    ListView lv;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent = (EditText)findViewById(R.id.etContent);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnRetrieve = (Button)findViewById(R.id.btnRetrieve);
        tvDBContent = (TextView)findViewById(R.id.tvDBContent);
        al = new ArrayList<Note>();
        lv = (ListView)findViewById(R.id.lv);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etContent.getText().toString();
                DBHelper db = new DBHelper(MainActivity.this);
                long row = db.insertNote(data);
                db.close();
                if(row != -1){
                    Toast.makeText(MainActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes("a"));
                dbh.close();

                String txt = "";
                for (int i = 0; i< al.size(); i++){
                    String tmp = al.get(i).toString();
                    txt += tmp + "\n";
                }
                tvDBContent.setText(txt);

            }
        });
        lv = (ListView) findViewById(R.id.lv);
        aa = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                int selected_id = Integer.parseInt(etContent.getText().toString());

                String data = al.get(selected_id-1).toString();
                String id = data.split(",")[0].split(":")[1];
                String content = data.split(",")[1].trim();

                Note target = new Note(Integer.parseInt(id), content);
                i.putExtra("data", target);
                startActivityForResult(i, 9);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                String data = al.get(position).toString();
                String id = data.split(",")[0].split(":")[1];
                String content = data.split(",")[1].trim();

                Note target = new Note(Integer.parseInt(id), content);
                i.putExtra("data", target);
                startActivityForResult(i, 9);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            btnRetrieve.performClick();
        }
    }

}
