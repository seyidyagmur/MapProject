package demo.chat.com.myfirstinternapp.yeni;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roughike.bottombar.OnMenuTabSelectedListener;
import com.roughike.bottombar.OnTabSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import demo.chat.com.myfirstinternapp.R;
import demo.chat.com.myfirstinternapp.StringWithTag;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by engineering on 20.06.2016.
 */
public class Interceptor extends Fragment {
    RequestQueue requestQueue;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<StringWithTag> friendArrayList;
    int sayfa;
    ProgressBar progressBar;
   // EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("ATAOL");

        final View view = inflater.inflate(R.layout.interceotor, container, false);
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor   = preferences.edit();

        friendArrayList = new ArrayList<>();

        //editText=(EditText)view.findViewById(R.id.edit1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        progressBar=(ProgressBar)view.findViewById(R.id.progres1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

         adapter = new RecyclerAdapter(getActivity(), friendArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor   = preferences.edit();
        ///////////////


        //////////////////////////////////////
        LinearLayout ln=(LinearLayout)view.findViewById(R.id.listLayout);
        //  spinner=(SearchableSpinner)view.findViewById(R.id.spinner1);
        //spinner.setOnItemSelectedListener(this);


        sayfa= preferences.getInt("SAYFA",0);

        String idUlke=preferences.getString("IDULKE","");
        String idIl=preferences.getString("IDILL","");
        String idIlce=preferences.getString("IDILCEE","");
        String idMah=preferences.getString("IDMAH","");
        String idYol=preferences.getString("IDYOL","");
        String idNo=preferences.getString("IDNO","");


         final Presenteter pp=(Presenteter)getActivity();

        pp.bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                if(position>sayfa)
                pp.bottomBar.selectTabAtPosition(sayfa,true);
                else if(position<sayfa) {
                 pp.goToBackPage(view,position);
                    pp.bottomBar.selectTabAtPosition(position, true);
                }
            }
        });
        pp.bottomBar.selectTabAtPosition(sayfa,true);


 
        /*pp.bottomBar.setItemsFromMenu(R.menu.alt, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.item1:
                        System.out.println("HAYAT:"+menuItemId);
                        break;
                    case R.id.item2:

                        System.out.println("HAYAT:"+menuItemId);
                        break;
                    case R.id.item3:

                        System.out.println("HAYAT:"+menuItemId);
                        break;
                }
            }
        });*/
        switch (sayfa)
        {
            case 0://ulkeleri listele
                //gifAyarla(sayfa);
              pp.toolbar.setTitle("Ülkeler");
                 sorgula("ULKE_ID","sorguULKE","0","");

                break;
            case 1://il
                pp.toolbar.setTitle("İller");

                //gifAyarla(sayfa);

                sorgula("IL_ID", "sorguIl", idUlke, "");//ÜLKE İDSİNE GÖRE ŞEHİR ARIYOR,/ASAGIDAKİLERDE AYNI SEKİLDE
                break;
            case 2://ilce

                 //gifAyarla(sayfa);
                pp.toolbar.setTitle("İlçeler");

                sorgula("ILCE_ID", "sorguIlce", idIl, "");


                break;
            case 3://mahalle

                 //gifAyarla(sayfa);
                pp.toolbar.setTitle("Mahalleler");

                sorgula("MAH_ID", "sorguMAH", idIlce, "");

                break;
            case 4://yol

 //                gifAyarla(sayfa);
                pp.toolbar.setTitle("Yollar");

                sorgula("ILCS_ID", "sorguyollar",idMah, "1");

                break;
            case 5:

 //                gifAyarla(sayfa);
                pp.toolbar.setTitle("Nolar");

                sorgula("NO", "sorguKAPINO", idYol, "");//bkbmah daki yollar için
                break;
            default:

        }



        return view;
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
                                String x;
                                String y;

                                float x_=0;
                                float y_=0;
                                if(key.equals("NO")==true) {

                                    ad = id;
                                    JSONObject objSon=obj.getJSONObject("GEO");
                                    float lat=Float.parseFloat(objSon.getJSONArray("coordinates").get(1).toString());
                                    float lng=Float.parseFloat( objSon.getJSONArray("coordinates").get(0).toString());
                                    editor.putFloat("latP", lat);
                                    editor.putFloat("lngP", lng);
                                    editor.commit();
                                    x_=lat;
                                    y_=lng;

                                    }else if(key.equals("ILCS_ID"))
                                            {
                                                JSONObject objYol=obj.getJSONObject("CENT");
                                                ad = obj.getString("ADI");
                                                x=objYol.getString("LongX");
                                                y=objYol.getString("LatY");
                                                x_ = Float.parseFloat(y);
                                                y_= Float.parseFloat(x);


                                            }
                                else {
                                    ad = obj.getString("ADI");
                                    x=obj.getString("LONGX");
                                    y=obj.getString("LATY");
                                    x_ = Float.parseFloat(y);
                                    y_= Float.parseFloat(x);

                                            }


                                 list.add(new StringWithTag(ad,id,x_,y_));
                                friendArrayList.add(new StringWithTag(ad,id,x_,y_));


                            }
                           final RecyclerAdapter rv=new RecyclerAdapter(getActivity(),friendArrayList);
                            recyclerView.setAdapter( rv);
                            final Presenteter pp=(Presenteter)getActivity();

                            pp.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    rv.getFilter().filter(newText);

                                    return false;
                                }
                            });

                            int adet=list.size();
                                System.out.println("BOYUT:"+adet);
                            if(adet<=0) {
                                displaySnackMessage(getView(),"Belirtilen bilgi yoktur!");
                            }

                         } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            Presenteter pp=(Presenteter)getActivity();
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



}
