package demo.chat.com.myfirstinternapp.yeni;

 import android.app.SearchManager;
 import android.content.Context;
 import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
 import android.support.annotation.IdRes;
 import android.support.design.widget.CollapsingToolbarLayout;
 import android.support.design.widget.Snackbar;
 import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
 import android.support.v4.content.ContextCompat;
 import android.support.v7.app.ActionBarActivity;
 import android.support.v7.app.AppCompatActivity;
  import android.support.v7.widget.Toolbar;
 import android.view.Menu;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.SearchView;
 import android.widget.TextView;


 import com.roughike.bottombar.BottomBar;
 import com.roughike.bottombar.OnMenuTabSelectedListener;

 import java.util.ArrayList;

 import demo.chat.com.myfirstinternapp.Home;
import demo.chat.com.myfirstinternapp.R;

/**
 * Created by engineering on 16.06.2016.
 */
public class Presenteter extends AppCompatActivity  {
    private final Interceptor question1Fragment = new Interceptor();
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    BottomBar bottomBar;
      Toolbar toolbar;

    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.present);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor   = preferences.edit();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        bottomBar=BottomBar.attach(this,savedInstanceState);
         bottomBar.setItemsFromMenu(R.menu.alt,null);
          toolbar = (Toolbar) findViewById(R.id.MyToolBar);
        setSupportActionBar(toolbar);
          //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /// CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        //collapsingToolbarLayout.setTitle("Ulkeler");
        searchView=(SearchView)findViewById(R.id.search_view);
        Context context = this;
        //collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorAccent));
        ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left);

         ft.replace(R.id.flPersonalization, question1Fragment);
         editor.putInt("SAYFA",0);
        editor.commit();

        ft.commit();




    }


    @Override
    public void onDestroy() {
         PreferenceManager.getDefaultSharedPreferences(getBaseContext()).
                edit().clear().apply();
        super.onDestroy();
    }


    FragmentTransaction ft;
    public void goToMap(View view){
        Intent intent=new Intent(Presenteter.this,Home.class);
        float lat = preferences.getFloat("LATX",1);
        float lng = preferences.getFloat("LATY",1);
        editor.remove("LATX");
        editor.remove("LATY");

        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        startActivityForResult(intent, 1);

    }
    public void goToNextPage(View view) {

        int sayfa=preferences.getInt("SAYFA",0);


System.out.println("KADIRGA:"+sayfa);
        if(sayfa!=5){
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);
            ft=getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left);

            Interceptor questions=new Interceptor();
            ft.replace(R.id.flPersonalization, questions);
         editor.putInt("SAYFA",sayfa+1);

        editor.commit();
        ft.addToBackStack(null);

        ft.commit();

        }else{
            Intent intent=new Intent(Presenteter.this,Home.class);
            float lat = preferences.getFloat("latP",1);
            float lng = preferences.getFloat("lngP",1);
            editor.remove("latP");
            editor.remove("lngP");

            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            startActivityForResult(intent, 1);

        }
       /* switch (view.getId())
        {
            case 1://illr
                Questions questions=new Questions();
                ft.replace(R.id.flPersonalization, questions);
                 bundle.putInt("clickCnt", 1);
                questions.setArguments(bundle);

                ft.addToBackStack(null);

                ft.commit();
                break;
             case 2://ilceler
                Questions questions2=new Questions();
                ft.replace(R.id.flPersonalization, questions2);
                 bundle.putInt("clickCnt",2);
                questions2.setArguments(bundle);

                ft.addToBackStack(null);

                ft.commit();
                break;
            case 3://mhalller
                Questions questions3=new Questions();
                ft.replace(R.id.flPersonalization, questions3);
                 bundle.putInt("clickCnt", 3);
                questions3.setArguments(bundle);

                ft.addToBackStack(null);

                ft.commit();
                break;
            case 4://yollar
                Questions questions4=new Questions();
                ft.replace(R.id.flPersonalization, questions4);
                 bundle.putInt("clickCnt", 4);
                questions4.setArguments(bundle);

                ft.addToBackStack(null);

                ft.commit();
                break;
            case 5://numara
                Questions questions5=new Questions();
                ft.replace(R.id.flPersonalization, questions5);
                 bundle.putInt("clickCnt", 5);
                questions5.setArguments(bundle);

                ft.addToBackStack(null);

                ft.commit();
                break;
            case 6://harita

               Intent intent=new Intent(Presenteter.this,Home.class);
                //intent.putExtra("lat",lat);
                //intent.putExtra("lng",lng);
                startActivityForResult(intent, 1);

                break;


            default:


        }*/
    }

    public void goToBackPage(View view,int position) {

        System.out.println("SOKAK"+position);
        int sayfa=preferences.getInt("SAYFA",0);
        if(sayfa>0) {
            if (sayfa != 7) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                Interceptor questions = new Interceptor();
                ft.replace(R.id.flPersonalization, questions);
                //bundle.putInt("clickCnt", 1);
                int git=0;
                 git=sayfa-1;
                if(position>-1)
                    git=position;

                editor.putInt("SAYFA", git);

                editor.commit();
                ft.addToBackStack(null);

                ft.commit();

            } else {
                Intent intent = new Intent(Presenteter.this, Home.class);
                //intent.putExtra("lat",lat);
                //intent.putExtra("lng",lng);
                startActivityForResult(intent, 1);

            }
        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       int sayfa= preferences.getInt("SAYFA",0);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);
        ft=getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);

        Questions questions=new Questions();
        ft.replace(R.id.flPersonalization, questions);
        //bundle.putInt("clickCnt", 1);
        editor.putInt("SAYFA6:",sayfa);

        editor.commit();
        ft.addToBackStack(null);

        ft.commit();
         if (requestCode == 1) {
            // istek başarılı mı diye bakalım
            if (resultCode == RESULT_OK) {
                // Kullanıcımız bir kontakı başarıyla seçmiş.
                // 'Intent data' parametresiyle seçili kontak hakkında daha fazla bilgi alabilirsiniz
                // Kontak ile ilgili bir şeyler yapabilirsiniz (aşağıda daha geniş bir örnek bulabilirsiniz)
//                System.out.println("DONUSID="+donusId);

                // sorgula("NO", "sorguKAPINO",donusId, "");//bkbmah daki yollar için
            }
        }
    }*/


/*
    @Override
    protected void onPostResume() {
        System.out.println("HADOOP3:");
        int sayfa= preferences.getInt("SAYFA",0);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);
        ft=getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);

        Questions questions=new Questions();
        ft.replace(R.id.flPersonalization, questions);
        //bundle.putInt("clickCnt", 1);
        editor.putInt("SAYFA6:",sayfa);

        editor.commit();
        ft.addToBackStack(null);

        ft.commit();
        super.onPostResume();
    }
*/
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flPersonalization);

        //silme
        goToBackPage(currentFragment.getView(),-1);
    }



    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("DONUSID="+donusId);

        sorgula("NO", "sorguKAPINO",donusId, "");
        if (requestCode == 1) {
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
*/


}
