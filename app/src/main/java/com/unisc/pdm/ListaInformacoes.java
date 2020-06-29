package com.unisc.pdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaInformacoes extends AppCompatActivity {

    String query = "SELECT * FROM cores";
    List<Map<String, Object>> lista;
    private DataBaseHelper2 helper;
    ListView listView;
    public ArrayList<String> rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacoes);
        rs = new ArrayList<>();
        loadLista();

    }

    public void cliwueLimpar(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long res = db.delete("cores",null,null);
        if(res != -1){
            //Deletou
            loadLista();
            Toast.makeText(this,"Dados deletados",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Não foi possível deleta os dados",Toast.LENGTH_SHORT).show();
        }
    }
    protected  void onDestroy() {
        helper.close();
        super.onDestroy();
    }
    public void loadLista(){
        listView = findViewById(R.id.lista);

        lista = new ArrayList<>();
        helper = new DataBaseHelper2(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String,Object> map = new HashMap<>();
            String id = c.getString(0);
            String R = c.getString(1);
            String G = c.getString(2);
            String B = c.getString(3);
            String nome = c.getString(4);
            String teste = c.getString(c.getColumnIndex("R"));
            rs.add(teste);
            map.put("id",id);
            map.put("R",R);
            map.put("G",G);
            map.put("B",B);
            map.put("nome",nome);
            lista.add(map);
            c.moveToNext();
        }
        c.close();
        SimpleAdapter adapter = new CoresAdapter(this,lista,R.layout.listagem2,new String[] {"nome"},
                new int[] {R.id.tvNome});
        listView.setAdapter(adapter);
    }

    private class CoresAdapter extends SimpleAdapter {
        public CoresAdapter(Context ctx, List<Map<String, Object>> lista, int listagem2, String[] strings, int[] ints) {
            super(ctx,lista,listagem2,strings,ints);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            String r = "";
            if(position!= -1){
                for(int i = 0 ; i < rs.size(); i++){
                    if(i == position){
                        r = rs.get(i);
                    }
                }
                Log.d("testeinfo",r);
                view.setBackgroundColor(Color.YELLOW);
            }
            return view;
        }
    }
}
