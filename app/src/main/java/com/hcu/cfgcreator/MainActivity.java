package com.hcu.cfgcreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnVirgulEkle,btnOkEkle,btnAyracEkle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            alttaki
        */
        final EditText etCFGinput = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.lw1);
        Button btnGetTree = (Button) findViewById(R.id.btn1);
        btnVirgulEkle = (Button) findViewById(R.id.btnVirgulEkle);
        btnOkEkle = (Button) findViewById(R.id.btnOkEkle);
        btnAyracEkle = (Button) findViewById(R.id.btnAyracEkle);

        btnAyracEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCFGinput.setText(etCFGinput.getText()+"|");
                etCFGinput.setSelection(etCFGinput.getText().length());
            }
        });
        btnOkEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCFGinput.setText(etCFGinput.getText()+"-->");
                etCFGinput.setSelection(etCFGinput.getText().length());
            }
        });
        btnVirgulEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCFGinput.setText(etCFGinput.getText()+",");
                etCFGinput.setSelection(etCFGinput.getText().length());
            }
        });
        btnGetTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CFGinput = etCFGinput.getText().toString();
                String[] CFGList = CFGinput.split(",");
                ArrayList<String>[] arrayList = (ArrayList<String>[])new ArrayList[CFGList.length];

                /*
                    Alttaki döngüde
                */
                for (int i = 0; i <CFGList.length; i++) {
                    String[] CFGElementList = CFGList[i].split("-->");
                    arrayList[i] = new ArrayList<>();
                    arrayList[i].add(CFGElementList[0]);
                    String[] CFGItemList = CFGElementList[1].split("\\|");
                    for (int j = 0; j < CFGItemList.length; j++) {
                        arrayList[i].add(CFGItemList[j]);
                    }
                }

                Node nodeRoot = new Node();
                nodeRoot.value=arrayList[0].get(0);

                addToTree(arrayList,nodeRoot);
                ArrayList<String> resultList = new ArrayList<>();
                travelOnTree(nodeRoot,resultList);
                displayOnList(resultList);
            }
        });

    }

    private void displayOnList(ArrayList<String> resultList) {
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, resultList);
        listView.setAdapter(veriAdaptoru);
    }

    private static void addToTree(ArrayList<String>[] arrayList, Node nodeRoot) {
        for (int i = 0; i < nodeRoot.value.length() ; i++) {
            for (int j = 0; j < arrayList.length; j++) {
                if(arrayList[j].get(0).equals(nodeRoot.value.substring(i,i+1))){
                    if(nodeRoot.nodelist==null)
                        nodeRoot.nodelist = new ArrayList<Node>();
                    for (int k = 1; k < arrayList[j].size(); k++) {
                        Node temp = new Node();
                        temp.value = replaceCharAt(nodeRoot.value, i, arrayList[j].get(k));
                        nodeRoot.addNodeListItem(temp);
                        addToTree(arrayList, temp);
                    }

                }
            }
        }
    }

    public static String replaceCharAt(String s, int pos, String c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    private static void travelOnTree(Node nodeRoot, ArrayList<String> resultList) {
        if (nodeRoot.nodelist==null) {
            if(!resultList.contains(nodeRoot.value)){
                resultList.add(nodeRoot.value);
            }

        }else{
            for (int i = 0; i < nodeRoot.nodelist.size(); i++) {
                travelOnTree(nodeRoot.nodelist.get(i),resultList);
            }
        }
    }
}
