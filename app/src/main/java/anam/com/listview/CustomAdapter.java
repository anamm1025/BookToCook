package anam.com.listview;

/**
 * Created by Anam Sadiq on 29-01-2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Anam Sadiq on 28-01-2016.
 */



public class CustomAdapter extends ArrayAdapter<MyRecipeListItem> {
    Context c;
    int layoutFile;
    Filter valueFilter;
    ArrayList<MyRecipeListItem> data;
    ArrayList<MyRecipeListItem> dataCopy;
    String type;

    public CustomAdapter(Context context, int resource, ArrayList<MyRecipeListItem> objects,String typ) {

        super(context, resource, objects);
        c = context;
        valueFilter = null;
        layoutFile = resource;
        data = objects;
        dataCopy = objects;
        type=typ;
    }

    public ArrayList<MyRecipeListItem> getData()
    {
        return data;
    }
    public ArrayList<MyRecipeListItem> getCopyData()
    {
        return dataCopy;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View v, row;
        row=null;
        final int pos = position;
        if(pos>=data.size())
            return row;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) c).getLayoutInflater();
            row = inflater.inflate(layoutFile, parent, false);
        } else {
            row = (View) convertView;
        }

        String imgName = Integer.toString(data.get(position).getImgURL());

        if(type.equals("myRecipes"))
        {
            imgName="user"+imgName;
        }

        if (data != null) {


            String name = data.get(position).getName();

            TextView txt = (TextView) row.findViewById(R.id.recipeName);
            txt.setText(name);

            ImageView imageView = (ImageView) row.findViewById(R.id.recipeImg);
                       if (ImageStorage.checkifImageExists (imgName))
            {
                File file = ImageStorage.getImage("/" +imgName + ".jpg");
                String path = file.getAbsolutePath();
                if (path != null) {
                    Bitmap b = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(b);
                }
            }
        }
        return row;

    }


    @Override
    public int getCount()
    {
        return data.size();
    }

    private class myFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<MyRecipeListItem> filterList= new ArrayList<MyRecipeListItem>();



                for (int i = 0; i <dataCopy.size(); i++)
                {

                    String rowData = dataCopy.get(i).getName().toUpperCase();
                    if ( rowData.contains(constraint.toString().toUpperCase())) {
                         MyRecipeListItem newObj = new MyRecipeListItem(dataCopy.get(i).getID(), dataCopy.get(i).getImgURL(),dataCopy.get(i).getName(),dataCopy.get(i).getCatId(),dataCopy.get(i).getRes());
                        filterList.add(newObj);

                    }
                }

                results.count = filterList.size();
                results.values = filterList;


            } else {

                results.count = dataCopy.size();
                results.values = dataCopy;

            }


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            data= (ArrayList<MyRecipeListItem>) results.values;
            notifyDataSetChanged();

        }

    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null)
            valueFilter = new myFilter();
        return valueFilter;
    }



}



