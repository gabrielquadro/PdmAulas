package com.unisc.pdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaDeCapturas extends AppCompatActivity {
    //select no banco
    String query = "SELECT * FROM cores";
    List<Map<String, Object>> lista;
    private DataBaseHelper2 helper;
    ListView listView;
    //public ArrayList<String> rs;
    TextView tvR, tvG, tvB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacoes);
        //rs = new ArrayList<>();
        //carrega as info do banco e põe na lista
        loadLista();

    }
    //deleta o que há no banco
    public void CliqueLimpar(View view) {
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
        //leitura de dados do banco
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        //cursor percorre o banco
        for (int i = 0; i < c.getCount(); i++) {
            //adiciona em um map na hora da leitura
            Map<String,Object> map = new HashMap<>();
            String id = c.getString(0);
            String R = c.getString(1);
            String G = c.getString(2);
            String B = c.getString(3);
            String nome = c.getString(4);
            //String teste = c.getString(c.getColumnIndex("R"));

            //rs.add(teste);
            //poe as informações buscadas no map
            map.put("id",id);
            map.put("R",R);
            map.put("G",G);
            map.put("B",B);
            map.put("nome",nome);
            //adiciona o map na lista
            lista.add(map);

            c.moveToNext();
        }
        c.close();
        //adapter pra lista
        SimpleAdapter adapter = new AlteraFundo(this,lista,R.layout.listagem2,new String[] {"nome","R","G","B"},
                new int[] {R.id.tvNome, R.id.tvR, R.id.tvG, R.id.tvB});
        listView.setAdapter(adapter);
    }
    //adapter que ira fazer o layout da lista
    private class AlteraFundo extends SimpleAdapter {
        //construtor
        public AlteraFundo(Context ctx, List<Map<String, Object>> lista, int listagem2, String[] strings, int[] ints) {
            super(ctx,lista,listagem2,strings,ints);
        }
        //altera a view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //pega a view
            View view = super.getView(position, convertView, parent);
            //consulta
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c = db.rawQuery(query,null);
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                //se estiver lendo a mesma posição da lista e do banco
                if(i == position){
                    int r = c.getInt(1);
                    int g = c.getInt(2);
                    int b = c.getInt(3);
                    //altera a cor de fundo de acordo com as cores egbdo banco
                    view.setBackgroundColor(Color.rgb(r,g, b));
                    break;
                }
                c.moveToNext();
            }
            c.close();
            return view;
        }
    }
}
