package e.arturo.nuevomedummasperron4kfullhd1link.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import e.arturo.nuevomedummasperron4kfullhd1link.Model.Favorites;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Order;

//Clase de la BD que actualmente es para agregar a favoritos las casas
public class Database extends SQLiteAssetHelper {

    private final static String DB_NAME="cibusDB.db";
    private final static int DB_VERSION=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public List<Order> getCarts(){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"HouseId","HouseName","Phone","HouseAddress","Email"};

        String sqlTable ="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("HouseId")),
                        c.getString(c.getColumnIndex("HouseName")),
                        c.getString(c.getColumnIndex("Phone")),
                        c.getString(c.getColumnIndex("HouseAddress")),
                        c.getString(c.getColumnIndex("Email"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (HouseId,HouseName,Phone,HouseAddress,Email) VALUES('%s','%s','%s','%s','%s');",
                order.getHouseid(),
                order.getHouseName(),
                order.getPhone(),
                order.getHouseAddress(),
                order.getEmail());
        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void addToFavorites(Favorites house){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(HouseId,userPhone,HouseTitle,HouseAddress,HouseImage,HouseDescription,HousePrice,userID,HouseLatLng,HouseCurrency,HouseDate,HouseBedroom,HouseBathroom,HouseSize) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                house.getHouseId(),
                house.getPhone(),
                house.getTitle(),
                house.getAddress(),
                house.getImage(),
                house.getDescription(),
                house.getPrice(),
                house.getUserID(),
                house.getLatLng(),
                house.getCurrency(),
                house.getDate(),house.getBedroom(),house.getBathroom(),house.getSize());
        db.execSQL(query);
    }

    public void removeFromFavorites(String houseId){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE HouseId='%s';",houseId);
        db.execSQL(query);
    }

    public boolean isFavorites(String houseId){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE HouseId='%s';",houseId);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Favorites> getFavorites(String userPhone){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"HouseId","userPhone","HouseTitle","HouseAddress","HouseImage","HouseDescription","HousePrice","userID","HouseLatLng","HouseCurrency","HouseDate","HouseBedroom","HouseBathroom","HouseSize"};
        String sqlTable ="Favorites";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Favorites> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Favorites(c.getString(c.getColumnIndex("HouseId")),
                        c.getString(c.getColumnIndex("HouseTitle")),
                        c.getString(c.getColumnIndex("HouseAddress")),
                        c.getString(c.getColumnIndex("HouseLatLng")),
                        c.getString(c.getColumnIndex("HouseImage")),
                        c.getString(c.getColumnIndex("HouseDescription")),
                        c.getString(c.getColumnIndex("HousePrice")),
                        c.getString(c.getColumnIndex("HouseCurrency")),
                        null,
                        c.getString(c.getColumnIndex("userPhone")),
                        c.getString(c.getColumnIndex("HouseDate")),
                        c.getString(c.getColumnIndex("HouseBedroom")),
                        c.getString(c.getColumnIndex("HouseBathroom")),
                        c.getString(c.getColumnIndex("HouseSize")),
                        c.getString(c.getColumnIndex("userID"))));
            }while (c.moveToNext());
        }
        return result;
    }
}
