package anam.com.listview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity {

    ArrayList<MyRecipeListItem> arrayList;
    CustomAdapter customAdapter;
    final Context context = this;
    String typee;
    int userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        final String type = intent.getStringExtra("type");
        typee = type;
        OfflineDB db = new OfflineDB(this);
        if(type.equals("category"))
        {
            setTitle(Html.fromHtml("<b>Categories</b>"));

                arrayList = db.getAllCategories();
                ArrayList arrayList2 = arrayList;
        }

        else if(type.equals("specificCategory"))
        {
            //Intent in = getIntent();
            setTitle(Html.fromHtml("<b>" + intent.getStringExtra("catName") + "</b>"));
            int id =0;
            id=intent.getIntExtra("ID", id);
            arrayList = db.getRecipesByCategory(id);
        }

        else if(type.equals("allRecipes"))
        {
            setTitle(Html.fromHtml("<b>All Recipes</b>"));
            arrayList = db.getAllRecipes();
        }

        else if(type.equals("myRecipes"))
        {
            setTitle(Html.fromHtml("<b>My Recipes</b>"));
           // Intent in = getIntent();
            userId=intent.getIntExtra("userId", userId);
            arrayList = db.getRecipesByUser(userId);
        }

        for(int i=0; i<arrayList.size(); i++)
        {
            arrayList.get(i).setRes(getResources());
        }

        customAdapter = new CustomAdapter(this, R.layout.list_item_layout, arrayList, type);
        ListView lv = (ListView) findViewById(R.id.listView);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int pos, long id) {
                Intent intent = null;
                if (type.equals("category")) {
                    intent = new Intent(context, listActivity.class);
                    CustomAdapter adp = (CustomAdapter) parent.getAdapter();
                    intent.putExtra("type", "specificCategory");
                    intent.putExtra("ID", adp.getData().get(pos).getID());
                    intent.putExtra("catName", adp.getData().get(pos).getName());
                } else {
                    intent = new Intent(context, RecipeDetailActivity.class);

                    CustomAdapter adp = (CustomAdapter) parent.getAdapter();
                    MyRecipeListItem r = adp.getData().get(pos);
                    String rTitle = r.getName();
                    int rID = r.getID();
                    String rImage = r.getImgURL()+"";
                    if(type.equals("myRecipes"))
                    {
                        rImage="user"+rImage;
                    }
                    String category = "" + r.getCatId();
                    intent.putExtra("cat", category);
                    intent.putExtra("recTitle", rTitle);
                    intent.putExtra("recID", rID);
                    intent.putExtra("imUrl", rImage);
                    if (type.equals("specificCategory") || type.equals("allRecipes")) {
                        intent.putExtra("typeOfDetail", "app");  //we want to show detail of app-added recipe in next screen
                    } else {
                        intent.putExtra("typeOfDetail", "user");  //we want to show detail of uer-added recipe in next screen
                    }


                }
                startActivity(intent);
            }
        });

         lv.setAdapter(customAdapter);


        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search..");
        SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String text) {
                customAdapter.getFilter().filter(text);
                return true;
            }

            public boolean onQueryTextSubmit(String text) {
                customAdapter.getFilter().filter(text);
                return true;
            }

        };

        searchView.setOnQueryTextListener(searchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (typee.equals("myRecipes")) {
            menuInflater.inflate(R.menu.menu_for_myrecipes, menu);
        } else {
            menuInflater.inflate(R.menu.menu_with_search, menu);
        }
/*
       MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String text) {
                    customAdapter.getFilter().filter(text);
                    return true;
                }

                public boolean onQueryTextSubmit(String text) {
                    customAdapter.getFilter().filter(text);
                    return true;
                }

            };

            searchView.setOnQueryTextListener(searchListener);

        }
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch(item.getItemId())
        {

            case R.id.home:
                intent = new Intent(this,MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case R.id.about:
                intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                intent = new Intent(this,LoginToRecipes.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.addRecipe:
                intent = new Intent(this,AddNewRecipe.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
