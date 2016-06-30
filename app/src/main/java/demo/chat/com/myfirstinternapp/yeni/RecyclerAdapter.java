package demo.chat.com.myfirstinternapp.yeni;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.chat.com.myfirstinternapp.R;
import demo.chat.com.myfirstinternapp.StringWithTag;

/**
 * Created by engineering on 20.06.2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  implements Filterable {

    private List<StringWithTag> friends;
    private Activity activity;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    CustomFilter filter;
    ArrayList<StringWithTag> filterList;




    public RecyclerAdapter(Activity activity, List<StringWithTag> friends) {
        this.friends = friends;
        this.activity = activity;
    this.filterList= (ArrayList<StringWithTag>) friends;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        preferences= PreferenceManager.getDefaultSharedPreferences(activity);
        editor   = preferences.edit();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
        int []i=new int[]{R.color.blue_normal,R.color.colorAccent,R.color.colorPrimaryDark,R.color.green_complete
        ,R.color.cardview_shadow_start_color,R.color.holo_orange_light,R.color.purple_progress};
        Random r = new Random();

        viewHolder.imageView.setImageResource(i[r.nextInt(i.length)]);
        viewHolder.name.setText(friends.get(position).name);

        viewHolder.title.setText(friends.get(position).id);
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu=new PopupMenu(activity,viewHolder.imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.poupup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.one)
                    {   Presenteter p=(Presenteter)activity;
                        StringWithTag d=friends.get(position);
                        editor.putFloat("LATX",d.x);
                        editor.putFloat("LATY",d.y);
                        editor.commit();
                         p.goToMap(v);
                    }
                        else
                    {

                    }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        //set on click listener for each element
        viewHolder.container.setOnClickListener(onClickListener(position));
    }



    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenteter p=(Presenteter)activity;

                InputMethodManager im=(InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(p.searchView.getWindowToken(),0);
                p.searchView.setQueryHint("Ara");
                p.searchView.setQuery("",false);
                p.searchView.setIconified(true);
                StringWithTag name= friends.get(position);
                int sayfa= preferences.getInt("SAYFA",0);


                 if(sayfa+1==1){


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
                p.goToNextPage(v);

                /*
                */
            }
        };
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
                        StringWithTag model=new StringWithTag(filterList.get(i).name,filterList.get(i).id,filterList.get(i).x,filterList.get(i).y);
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
            friends= (ArrayList<StringWithTag>) results.values;
            notifyDataSetChanged();
        }
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView name;
        private TextView title;
        private ImageButton imageButton;
        private View container;

        public ViewHolder(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            title = (TextView) view.findViewById(R.id.title);
            imageButton=(ImageButton)view.findViewById(R.id.imgButton);
            container = view.findViewById(R.id.card_view);
        }
    }
}