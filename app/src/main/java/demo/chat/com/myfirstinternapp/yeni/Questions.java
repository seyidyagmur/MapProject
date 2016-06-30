package demo.chat.com.myfirstinternapp.yeni;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
 import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demo.chat.com.myfirstinternapp.Home;
import demo.chat.com.myfirstinternapp.R;
import demo.chat.com.myfirstinternapp.StringWithTag;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by engineering on 16.06.2016.
 */
/*
public class Questions extends Fragment  {
   // private SearchableSpinner spinner;
    RequestQueue requestQueue;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    TextView tBaslik;
     int clickcnt;
    int sayfa;
    GifImageView img1,img2,img3,img4,img5,img6;
     EditText editText;
    ListView listView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question, container, false);
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor   = preferences.edit();
        ///////////////
         img1=(GifImageView)view.findViewById(R.id.img_1);
        img2=(GifImageView)view.findViewById(R.id.img_2);
        img3=(GifImageView)view.findViewById(R.id.img_3);
        img4=(GifImageView)view.findViewById(R.id.img_4);
        img5=(GifImageView)view.findViewById(R.id.img_5);
        img6=(GifImageView)view.findViewById(R.id.img_6);

        //////////////////////////////////////
         LinearLayout ln=(LinearLayout)view.findViewById(R.id.listLayout);
        //  spinner=(SearchableSpinner)view.findViewById(R.id.spinner1);
        //spinner.setOnItemSelectedListener(this);
        tBaslik=(TextView)view.findViewById(R.id.tvbaslik);
         editText=(EditText)view.findViewById(R.id.edit1);
        progressBar=(ProgressBar)view.findViewById(R.id.progres1);

        sayfa= preferences.getInt("SAYFA",0);

        String idUlke=preferences.getString("IDULKE","");
        String idIl=preferences.getString("IDILL","");
        String idIlce=preferences.getString("IDILCEE","");
        String idMah=preferences.getString("IDMAH","");
        String idYol=preferences.getString("IDYOL","");
        String idNo=preferences.getString("IDNO","");


        listView=(ListView)view.findViewById(R.id.list_bolge);
         listViewDoldur("");
        System.out.println("KADIRGA:"+sayfa);

        switch (sayfa)
        {
            case 0://ulkeleri listele
                gifAyarla(sayfa);
                tBaslik.setText("Ülkeler");
                    sorgula("ULKE_ID","sorguULKE","0","");

                 break;
            case 1://il

                gifAyarla(sayfa);
                tBaslik.setText("İller");

                sorgula("IL_ID", "sorguIl", idUlke, "");//ÜLKE İDSİNE GÖRE ŞEHİR ARIYOR,/ASAGIDAKİLERDE AYNI SEKİLDE
                    break;
             case 2://ilce

                 tBaslik.setText("İlçeler");
                 gifAyarla(sayfa);

                sorgula("ILCE_ID", "sorguIlce", idIl, "");


                break;
            case 3://mahalle

                tBaslik.setText("Mahalleler");
                gifAyarla(sayfa);

                sorgula("MAH_ID", "sorguMAH", idIlce, "");

                break;
            case 4://yol

                tBaslik.setText("Yollar");
                gifAyarla(sayfa);

                sorgula("ILCS_ID", "sorguyollar",idMah, "1");

                break;
            case 5:

                tBaslik.setText("Nolar");
                gifAyarla(sayfa);

                sorgula("NO", "sorguKAPINO", idYol, "");//bkbmah daki yollar için
                 break;
            default:

        }

         return view;

    }

    private void listViewDoldur(String s) {

        List<StringWithTag> adapter = new ArrayList<StringWithTag>();

    }

    private void gifAyarla(int sayfa) {
        Picasso []pc=new Picasso[6];

        GifImageView [] img=new GifImageView[]{img1,img2,img3,img4,img5,img6};
        for(int i=0;i<6;i++)
        {
            if(i==sayfa)
            pc[i].with(getContext()).load(R.drawable.res1).into(img[i]);
            else
            pc[i].with(getContext()).load(R.drawable.res2).into(img[i]);


        }

    }

    public static void displaySnackMessage(View view,String message)
    {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Kapat", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }

                );
        snackbar.setActionTextColor(Color.YELLOW);
        view = snackbar.getView();
        TextView snbTv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        snbTv.setTextColor(Color.GREEN);
        snackbar.show();

    }

    public void listeGetir(final String key,String sorgu)
    {
         requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest job=new JsonObjectRequest(Request.Method.GET, sorgu,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray objArray = response.getJSONArray("results");
                            ArrayList<StringWithTag> list = new ArrayList<StringWithTag>();

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
                                     editor.putFloat("latP", lat);
                                    editor.putFloat("lngP", lng);
                                    editor.commit();



                                }
                                list.add(new StringWithTag(ad,id));
                            }



                          //  ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag>(getContext(), android.R.layout.simple_spinner_dropdown_item,  list);

                             final OzelAdapter adap=new OzelAdapter(list);
                            listView.setAdapter(adap);
                            editText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    adap.getFilter().filter(s);

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    InputMethodManager im=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    im.hideSoftInputFromWindow(editText.getWindowToken(),0);
                                    ListView lv= (ListView) parent;
                                    StringWithTag name= (StringWithTag)  lv.getItemAtPosition(position);
                                    Presenteter p = (Presenteter) getActivity();
                                    int adet=lv.getAdapter().getCount();
                                     if(sayfa+1==1){

                                        tBaslik.setText("Iller");

                                        editor.putString("IDULKE",name.id);
                                        editor.putString("ulke",name.name);

                                    }else if(sayfa+1==2)
                                    {


                                        editor.putString("IDILL",name.id);

                                        editor.putString("il",name.name);

                                    }else if(sayfa+1==3)
                                    {


                                        editor.putString("IDILCEE",name.id);

                                        editor.putString("il",name.name);

                                    }else if(sayfa+1==4)
                                    {


                                        editor.putString("IDMAH",name.id);
                                        editor.putString("mah",name.name);


                                    }else if(sayfa+1==5)
                                    {


                                        editor.putString("IDYOL",name.id);

                                        editor.putString("yol",name.name);

                                    }else if(sayfa+1==6)
                                    {
                                        editor.putString("IDNO",name.id);

                                        editor.putString("no",name.name);

                                    }
                                    editor.commit();
                                    p.goToNextPage(view);

                                    // System.out.println("KANADA:"+dd.id+"::"+lv.getAdapter().getCount());
                                }
                            });
                            int adet=adap.getCount();
                            if(adet<=0) {
                                    displaySnackMessage(getView(),"Belirtilen bilgi yoktur!");
                            }
                               // spinner.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Volley", "Error");
                        displaySnackMessage(getView(),"Internet Baglantisini Kontrol Ediniz!");

                    }
                }
        );


        requestQueue.add(job);

    }

    public void sorgula(String key,String sorguIndex,String id,String tip)
    {
        System.out.println("LISTE:"+sorguIndex);
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

        String denemeUrl="";
        listeGetir(key,denemeUrl);

    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        Presenteter p = (Presenteter) getActivity();
        p.goToNextPage(view);

        System.out.println("KAPLUMBA");
        StringWithTag name = (StringWithTag) spinner.getSelectedItem();
        int adet=spinner.getAdapter().getCount();

        if(sayfa+1==1){


             editor.putString("IDULKE",name.id);
        editor.putString("ulke",name.name);

        }else if(sayfa+1==2)
        {


            editor.putString("IDIL",name.id);

            editor.putString("il",name.name);

        }else if(sayfa+1==3)
        {


            editor.putString("IDILCE",name.id);

            editor.putString("il",name.name);

        }else if(sayfa+1==4)
        {


            editor.putString("IDMAH",name.id);
            editor.putString("mah",name.name);


        }else if(sayfa+1==5)
        {


            editor.putString("IDYOL",name.id);

            editor.putString("yol",name.name);

        }else if(sayfa+1==6)
        {
            editor.putString("IDNO",name.id);

            editor.putString("no",name.name);

        }
        editor.commit();
    }
*//*
    public class OzelAdapter extends BaseAdapter implements Filterable{

        private LayoutInflater mInflater;
        private ArrayList<StringWithTag>     mKisiListesi;
        CustomFilter filter;
        ArrayList<StringWithTag> filterList;


        public OzelAdapter( ArrayList<StringWithTag> kisiler) {
            //XML'i alıp View'a çevirecek inflater'ı örnekleyelim

            //gösterilecek listeyi de alalım
            mKisiListesi = kisiler;
            this.filterList=kisiler;
        }
        @Override
        public int getCount() {
            return mKisiListesi.size();
        }

        @Override
        public StringWithTag getItem(int position) {


             return mKisiListesi.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mKisiListesi.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_row, null);
            TextView textView = (TextView) convertView.findViewById(R.id.baslik);


            StringWithTag kisi = mKisiListesi.get(position);

            textView.setText(kisi.name);



            return convertView;

        }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {
                constraint=constraint.toString().toUpperCase();
                ArrayList<StringWithTag> filters=new ArrayList<StringWithTag>();
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).name.toUpperCase().contains(constraint))
                    {
                        StringWithTag model=new StringWithTag(filterList.get(i).name,filterList.get(i).id);
                        filters.add(model);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        mKisiListesi= (ArrayList<StringWithTag>) results.values;
        notifyDataSetChanged();
        }
    }

}


}
*/
