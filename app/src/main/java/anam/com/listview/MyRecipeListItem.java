package anam.com.listview;

import android.content.res.Resources;

/**
 * Created by Anam Sadiq on 11-03-2016.
 */
public class MyRecipeListItem {

    private String name;
    private int ID;
    private int imgUrl;
    private int categoryId;
    private Resources res;

    public MyRecipeListItem(int id, int img, String name,int cateId, Resources r)
    {
        imgUrl=img;
        this.name =name;
        ID = id;
        categoryId=cateId;
        res = r;
    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources r) {
        res=r;
    }
    public int getImgURL(){return imgUrl;}

    public int getID(){return ID;}

    public int getCatId(){return categoryId;}



    public String getName(){return name;}


    public void setName(String name){ this.name =name;}

    public void setImgURL(int img){ imgUrl=img;}


}
