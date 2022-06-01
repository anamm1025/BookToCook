package anam.com.listview;

/**
 * Created by Anam Sadiq on 08-04-2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by Anam Sadiq on 23-03-2016.
 */
public class OfflineDB extends SQLiteOpenHelper {

    boolean isFirstTime=false;
    OfflineDB(Context context)
    {
        super(context,"MyRecipesDatabase",null, 14);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE User (ID INTEGER PRIMARY KEY,Email TEXT, Password TEXT);");
        db.execSQL("CREATE TABLE UserRecipes (ID INTEGER PRIMARY KEY, UserId INTEGER, RecipeName TEXT, ImgId INTEGER, Ingredients TEXT, Directions TEXT);");
        db.execSQL("CREATE TABLE RecipeCategories (ID INTEGER PRIMARY KEY, CategoryName TEXT, ImgId INTEGER);");
        db.execSQL("CREATE TABLE AllRecipes (ID INTEGER PRIMARY KEY, CategoryId INTEGER, RecipeName TEXT, ImgId INTEGER, Ingredients TEXT, Directions TEXT);");
        isFirstTime=true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS UserRecipes");
        db.execSQL("DROP TABLE IF EXISTS RecipeCategories");
        db.execSQL("DROP TABLE IF EXISTS AllRecipes");
        onCreate(db);
    }

    public void insertCategory(int id, String name, int imgId)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("CategoryName", name);
        contentValues.put("ImgId", imgId);
        db.insert("RecipeCategories", null, contentValues);

    }

    public void insertRecipe(int id,int catId, String name, int imgId, String ing, String dir)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("CategoryId", catId);
        contentValues.put("RecipeName", name);
        contentValues.put("Ingredients", ing);
        contentValues.put("Directions", dir);
        contentValues.put("ImgId",imgId);
        db.insert("AllRecipes", null, contentValues);

    }

    public void insertData()
    {

        SQLiteDatabase db = getWritableDatabase();

        if(!isFirstTime)
        {
            return;
        }

        isFirstTime=false;
        //categories
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("CategoryName", "Rice");
        contentValues.put("ImgId",R.drawable.cat1);
        db.insert("RecipeCategories", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("ID", 2);
        contentValues.put("CategoryName", "Broast");
        contentValues.put("ImgId", R.drawable.cat2);
        db.insert("RecipeCategories", null, contentValues);


        contentValues = new ContentValues();
        contentValues.put("ID", 3);
        contentValues.put("CategoryName", "Karahi");
        contentValues.put("ImgId", R.drawable.cat3);
        db.insert("RecipeCategories", null, contentValues);


        //recipes
        contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("CategoryId", 1);
        contentValues.put("RecipeName", "Chicken Biryani");
        contentValues.put("Ingredients", "Rice 1 KG\nOil 1/4 KG\nChicken 1 KG\nOnions 3");
        contentValues.put("Directions", "Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...");
        contentValues.put("ImgId",R.drawable.rec2);
        db.insert("AllRecipes", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("ID", 2);
        contentValues.put("CategoryId", 2);
        contentValues.put("RecipeName", "Chicken Tikka");
        contentValues.put("Ingredients", "Rice 1 KG\nOil 1/4 KG\nChicken 1 KG\nOnions 3");
        contentValues.put("Directions", "Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...");
        contentValues.put("ImgId",R.drawable.rec3);
        db.insert("AllRecipes", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("ID", 3);
        contentValues.put("CategoryId", 3);
        contentValues.put("RecipeName", "Chicken Masala Karahi");
        contentValues.put("Ingredients", "Rice 1 KG\nOil 1/4 KG\nChicken 1 KG\nOnions 3");
        contentValues.put("Directions", "Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...Put oil in the pan. Add chopped onions in the oil and fry them to golden, and then...");
        contentValues.put("ImgId",R.drawable.rec4);
        db.insert("AllRecipes", null, contentValues);


    }






    public ArrayList<MyRecipeListItem> getRecipesByCategory(int catID) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, RecipeName, ImgId from AllRecipes WHERE CategoryId="+catID, null);
        ArrayList<MyRecipeListItem> recipeList = new ArrayList<MyRecipeListItem>();
        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex("ID");
            int id = cursor.getInt(index);
            index = cursor.getColumnIndex("RecipeName");
            String name = cursor.getString(index);
            index = cursor.getColumnIndex("ImgId");
            int imgId = cursor.getInt(index);

            MyRecipeListItem item = new MyRecipeListItem(id, imgId, name, catID, null);
            recipeList.add(item);
        }

        return recipeList;
    }

    public ArrayList<MyRecipeListItem> getAllRecipes() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, RecipeName, ImgId, CategoryId from AllRecipes", null);
        ArrayList<MyRecipeListItem> recipeList = new ArrayList<MyRecipeListItem>();
        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex("ID");
            int id = cursor.getInt(index);
            index = cursor.getColumnIndex("RecipeName");
            String name = cursor.getString(index);
            index = cursor.getColumnIndex("ImgId");
            int imgId = cursor.getInt(index);
            index = cursor.getColumnIndex("CategoryId");
            int catId = cursor.getInt(index);

            MyRecipeListItem item = new MyRecipeListItem(id, imgId, name,catId, null);
            recipeList.add(item);
        }

        return recipeList;
    }

    public ArrayList<MyRecipeListItem> getRecipesByUser(int userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, RecipeName, ImgId from UserRecipes WHERE UserId="+userId, null);
        ArrayList<MyRecipeListItem> recipeList = new ArrayList<MyRecipeListItem>();
        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex("ID");
            int id = cursor.getInt(index);
            index = cursor.getColumnIndex("RecipeName");
            String name = cursor.getString(index);
            index = cursor.getColumnIndex("ImgId");
            int imgId = cursor.getInt(index);

            MyRecipeListItem item = new MyRecipeListItem(id, imgId, name,0, null);
            recipeList.add(item);
        }

        return recipeList;
    }

    public ArrayList<MyRecipeListItem> getAllCategories() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, CategoryName, ImgId from RecipeCategories", null);
        ArrayList<MyRecipeListItem> recipeList = new ArrayList<MyRecipeListItem>();
        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex("ID");
            int id = cursor.getInt(index);
            index = cursor.getColumnIndex("CategoryName");
            String name = cursor.getString(index);
            index = cursor.getColumnIndex("ImgId");
            int imgId = cursor.getInt(index);

            MyRecipeListItem item = new MyRecipeListItem(id, imgId, name,0, null);
            recipeList.add(item);
        }

        return recipeList;
    }

    public DetailObj getDetail(int id, String type)
    {
        DetailObj obj = null;
        SQLiteDatabase db = this.getReadableDatabase();

        if(type.equals("app"))
        {
            Cursor cursor = db.rawQuery("SELECT Ingredients, Directions from AllRecipes WHERE ID=" + id, null);
            if (cursor.moveToFirst()) {

                int index = cursor.getColumnIndex("Ingredients");
                String ing = cursor.getString(index);
                index = cursor.getColumnIndex("Directions");
                String dir = cursor.getString(index);

                obj = new DetailObj(ing,dir);

            }


        }

        else  if(type.equals("user"))
        {
            Cursor cursor = db.rawQuery("SELECT Ingredients, Directions from UserRecipes WHERE ID="+id, null);

            if (cursor.moveToFirst()) {

                int index = cursor.getColumnIndex("Ingredients");
                String ing = cursor.getString(index);
                index = cursor.getColumnIndex("Directions");
                String dir = cursor.getString(index);

                obj = new DetailObj(ing,dir);

            }

        }

        return obj;
    }

    public int getUserId(String email, String password)
    {
        int id=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID FROM User WHERE Email='" + email + "' AND Password='" + password + "'", null);
        if(!cursor.moveToFirst())
        {
            //validate email with regEX
            db.close();
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email", email);
            contentValues.put("Password", password);
            db.insert("User", null, contentValues);
            cursor = db.rawQuery("SELECT ID FROM User WHERE Email='" + email+"' AND Password='"+password+"'",null);
        }

        if(cursor.moveToFirst())
        {
            int index = cursor.getColumnIndex("ID");
            id = cursor.getInt(index);
        }
        return id;
    }

    public int addNewRecipe(String name, String ing, String dir, int userID)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RecipeName", name);
        contentValues.put("Ingredients", ing);
        contentValues.put("Directions", dir);
        contentValues.put("ImgId", 0);
        contentValues.put("userId", userID);
        db.insert("UserRecipes", null, contentValues);


        String q = "SELECT ID FROM UserRecipes WHERE UserId="+userID+" AND RecipeName='"+name +"' AND Ingredients='"+ing+"' AND Directions='"+dir+"'";
        Cursor cursor = db.rawQuery(q,null);


        int imgId=0;
        if(cursor.moveToFirst())
        {
            int index = cursor.getColumnIndex("ID");
            imgId = cursor.getInt(index);
        }

        q = "UPDATE UserRecipes SET ImgId="+imgId+" WHERE ID="+imgId;
        db.execSQL(q);
        return imgId;
    }

    public void emptyTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM AllRecipes");
        db.execSQL("DELETE FROM RecipeCategories");
    }

}

