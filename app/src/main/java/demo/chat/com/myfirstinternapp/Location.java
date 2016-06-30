package demo.chat.com.myfirstinternapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by engineering on 14.06.2016.
 */
/*
public class Location extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final int PICK_CONTACT_REQUEST = 1;  // istek kodu

    RequestQueue requestQueue;
    LinearLayout linear,linear_ulke,linear_il,linear_ilce,linear_mah_bkb,linear_yollar,linear_kapi;
    SearchableSpinner spinner_ulke;
    SearchableSpinner spinner_il;
    SearchableSpinner spinner_ilce;
    SearchableSpinner spinner_mah;
    SearchableSpinner spinner_bkb;
    SearchableSpinner spinner_bkbMah;
    SearchableSpinner spinner_yol;
    SearchableSpinner spinner_kapi;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofloc);
        //
          preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
          editor   = preferences.edit();
         linear=(LinearLayout)findViewById(R.id.linear1);
         linear_ulke=(LinearLayout)findViewById(R.id.linear_ulke);
         linear_il=(LinearLayout)findViewById(R.id.linear_il);
         linear_ilce=(LinearLayout)findViewById(R.id.linear_ilce);
          linear_yollar=(LinearLayout)findViewById(R.id.linear_yollar);
         linear_kapi=(LinearLayout)findViewById(R.id.linear_kapi);

         //
         spinner_ulke=(SearchableSpinner)findViewById(R.id.spinner1);
         spinner_il=(SearchableSpinner)findViewById(R.id.spinner2);
         spinner_ilce=(SearchableSpinner)findViewById(R.id.spinner3);
         spinner_mah=(SearchableSpinner)findViewById(R.id.spinner4);
         spinner_bkb=(SearchableSpinner)findViewById(R.id.spinner5);
         spinner_bkbMah=(SearchableSpinner)findViewById(R.id.spinner6);
         spinner_yol=(SearchableSpinner)findViewById(R.id.spinner7);
         spinner_kapi=(SearchableSpinner)findViewById(R.id.spinner8);

          spinner_ulke.setTitle("Ara");
          spinner_ulke.setPositiveButton("OK");
          spinner_il.setTitle("Ara");
          spinner_il.setPositiveButton("OK");
          spinner_ilce.setTitle("Ara");
          spinner_ilce.setPositiveButton("OK");
          spinner_mah.setTitle("Ara");
          spinner_mah.setPositiveButton("OK");
          spinner_bkb.setTitle("Ara");
          spinner_bkb.setPositiveButton("OK");
          spinner_bkbMah.setTitle("Ara");
          spinner_bkbMah.setPositiveButton("OK");
          spinner_yol.setTitle("Ara");
          spinner_yol.setPositiveButton("OK");
          spinner_kapi.setTitle("Ara");
          spinner_kapi.setPositiveButton("OK");
         //
         spinner_ulke.setOnItemSelectedListener(this);
         spinner_il.setOnItemSelectedListener(this);
         spinner_ilce.setOnItemSelectedListener(this);
         spinner_mah.setOnItemSelectedListener(this);
         spinner_bkb.setOnItemSelectedListener(this);
         spinner_bkbMah.setOnItemSelectedListener(this);
         spinner_yol.setOnItemSelectedListener(this);
         spinner_kapi.setOnItemSelectedListener(this);

        // ulkeSorgu(0);
         sorgula("ULKE_ID","sorguULKE","0","0");
     }
    public ArrayAdapter<StringWithTag> iciBosalt(String key)
    {
        List<StringWithTag> boslist = new ArrayList<StringWithTag>();
                boslist.add(new StringWithTag(key,key));
        ArrayAdapter<StringWithTag> bos = new ArrayAdapter<StringWithTag>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item,  boslist);

        return bos;
    }
    public float fal;
      public void listeGetir(final String key,String sorgu)
    {
        fal=0;
         requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest job=new JsonObjectRequest(Request.Method.GET, sorgu,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            System.out.println("HATA:"+response.toString());

                            JSONArray objArray = response.getJSONArray("results");
                               List<StringWithTag> list = new ArrayList<StringWithTag>();
                            list.add(new StringWithTag("-- Seciniz --","Seciniz"));
                            System.out.println("HATA:"+objArray.length());

                            for(int i=0;i<objArray.length();i++)
                            {    JSONObject obj = objArray.getJSONObject(i);
                                String ad;
                                String id=obj.getString(key);

                                if(key.equals("NO")==false)
                                    ad=obj.getString("ADI");
                                else {
                                    ad = id;
                                    JSONObject objSon=obj.getJSONObject("GEO");
                                    float lat=Float.parseFloat(objSon.getJSONArray("coordinates").get(1).toString());
                                    float lng=Float.parseFloat( objSon.getJSONArray("coordinates").get(0).toString());
                                fal=lat;
                                    editor.putFloat("latP", lat);
                                    editor.putFloat("lngP", lng);
                                    editor.commit();



                                 }
                                 list.add(new StringWithTag(ad,id));
                            }
                            ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item,  list);

                            if(key.equals("ULKE_ID")) {

                                spinner_ulke.setAdapter(adapter);


                            }else if(key.equals("IL_ID")){
                                 spinner_il.setAdapter(adapter);


                            }else if(key.equals("ILCE_ID")){
                                 spinner_ilce.setAdapter(adapter);
                               spinner_mah.setAdapter(iciBosalt("Mahalleler"));

                                spinner_bkbMah.setAdapter(iciBosalt("BKB Mahalle"));
                                spinner_yol.setAdapter(iciBosalt("Yollar"));
                                spinner_bkb.setAdapter(iciBosalt("BKB"));

                                spinner_kapi.setAdapter(iciBosalt("Kapı No"));
                            }
                            else if(key.equals("MAH_ID")){
                                 spinner_mah.setAdapter(adapter);
                                spinner_kapi.setAdapter(iciBosalt("Kapı No"));

                            }
                            else if(key.equals("BKB_ID")){
                                 spinner_bkb.setAdapter(adapter);
                                spinner_kapi.setAdapter(iciBosalt("Kapı No"));
                                spinner_yol.setAdapter(iciBosalt("Yollar"));
                                spinner_bkbMah.setAdapter(iciBosalt("BKB Mahalle"));

                            }
                            else if(key.equals("BKB_MAH_ID")){
                               if(list.size()>0) {
                                System.out.println("SAMET:"+list.size());
                                    linear.setVisibility(View.VISIBLE);
                              }else
                                   linear.setVisibility(View.GONE);

                                 spinner_bkbMah.setAdapter(adapter);
                                spinner_kapi.setAdapter(iciBosalt("Kapı No"));
                                spinner_yol.setAdapter(iciBosalt("Yollar"));

                            } else if(key.equals("ILCS_ID")){
                                 spinner_yol.setAdapter(adapter);
                                spinner_kapi.setAdapter(iciBosalt("Kapı No"));

                            }
                            else if(key.equals("NO")){
                                 spinner_kapi.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("HATA:"+error.toString());
                        System.out.println("HATA:"+error.fillInStackTrace());
                        System.out.println("HATA:"+error.getMessage());
                        System.out.println("HATA:"+error.getStackTrace());
                        Log.e("Volley", "Error");
                    }
                }
        );


        requestQueue.add(job);

     }

    public void sorgula(String key,String sorguIndex,String id,String tip)
    {
        String idSon="&ID="+id;
        String son="";

        if(sorguIndex.equals("sorguULKE"))
         son="";
        else
        son=idSon;

        if(sorguIndex.equals("sorguyollar"))
            son=son+"&Type="+tip+"&uk=false";
        if(sorguIndex.equals("sorguKAPINO"))
            son=son+"&uk=false";

        String denemeUrl="http://bms.basarsoft.com.tr/Service/api/v1/adresIndex/"+sorguIndex+"?accId=iZJ6Uh1yB0qjgY2eYYkcaA&appCode=TzLwX2sMG0ePSMwLQDcB9A"+son;
         listeGetir(key,denemeUrl);

    }
    String donusId;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
         StringWithTag name = (StringWithTag) spinner.getSelectedItem();

        if(spinner.getSelectedItemPosition()>0) {
            Snackbar snackbar = Snackbar.make(view, "Seçtiniz: "+name.name, Snackbar.LENGTH_SHORT);
            view = snackbar.getView();
            TextView snbTv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            snbTv.setTextColor(Color.GREEN);
            snackbar.show();

            if (spinner.getId() == R.id.spinner1) {//illeri getir
                sorgula("IL_ID", "sorguIl", name.id, "");
                editor.putString("ulke",name.name);

            } else if (spinner.getId() == R.id.spinner2) {//ilceleri getir
                sorgula("ILCE_ID", "sorguIlce", name.id, "");
                editor.putString("il",name.name);

                //  ilceSorgu(name.id);
            } else if (spinner.getId() == R.id.spinner3) {
                editor.putString("ilce",name.name);

                sorgula("MAH_ID", "sorguMAH", name.id, "");
                sorgula("BKB_ID", "sorguBKB", name.id, "");

            } else if (spinner.getId() == R.id.spinner5) {        //bkb seçilmisse bkbMah yüklencek

                sorgula("BKB_MAH_ID", "sorguBKB_MAH", name.id, "");
                editor.putString("bkb",name.name);


            } else if (spinner.getId() == R.id.spinner4) {
                editor.putString("mahalle",name.name);

                sorgula("ILCS_ID", "sorguyollar", name.id, "1");
                linear.setVisibility(View.GONE);

            } else if (spinner.getId() == R.id.spinner6) {
                editor.putString("bkb_mah",name.name);

                sorgula("ILCS_ID", "sorguyollar", name.id, "2");//bkbmah daki yollar için

            } else if (spinner.getId() == R.id.spinner7) {
                editor.putString("yol",name.name);
                donusId=name.id;
                System.out.println("DONUSID3");
                sorgula("NO", "sorguKAPINO", name.id, "");//bkbmah daki yollar için
            }else if(spinner.getId()==R.id.spinner8)
            {
                editor.putString("no",name.name);

                float lat = preferences.getFloat("latP",1);
                float lng = preferences.getFloat("lngP",1);
                editor.remove("latP");
                editor.remove("lngP");

                editor.commit();
                 Intent intent=new Intent(Location.this,Home.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                 startActivityForResult(intent, PICK_CONTACT_REQUEST);

            }
            editor.commit();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("DONUSID="+donusId);

        sorgula("NO", "sorguKAPINO",donusId, "");
        if (requestCode == PICK_CONTACT_REQUEST) {
            // istek başarılı mı diye bakalım
            if (resultCode == RESULT_OK) {
                // Kullanıcımız bir kontakı başarıyla seçmiş.
                // 'Intent data' parametresiyle seçili kontak hakkında daha fazla bilgi alabilirsiniz
                // Kontak ile ilgili bir şeyler yapabilirsiniz (aşağıda daha geniş bir örnek bulabilirsiniz)
//                System.out.println("DONUSID="+donusId);

               // sorgula("NO", "sorguKAPINO",donusId, "");//bkbmah daki yollar için
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
*/