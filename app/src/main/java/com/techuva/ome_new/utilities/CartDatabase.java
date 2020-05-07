package com.techuva.ome_new.utilities;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.techuva.ome_new.domain.AddressDO;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.object_models.AccountListResultObject;
import com.techuva.ome_new.object_models.SupplierConsumerHistoryProductObject;
import com.techuva.ome_new.object_models.SupplierConsumerOrderProduct;
import com.techuva.ome_new.object_models.SupplierShopperOrderResultObject;

import java.util.ArrayList;

public class CartDatabase {

    public static final boolean DEBUG = true;

    /******************** Logcat TAG ************/
    public static final String LOG_TAG = "CARTDATABASE";

    /***************Address Table Fields******************/
    public static final String Column_custId= "custId";
    public static final String Column_addressId= "addressId";
    public static final String Column_city = "city";
    public static final String Column_landmark= "landmark";
    public static final String Column_zipcode= "zipcode";
    public static final String Column_houseNo= "houseNo";
    public static final String Column_address= "address";
    public static final String Column_addressType= "addressType";


    /*******************OrderList Table*************************/

    public static final String Column_itemid = "itemId";
    public static final String Column_Ordertype = "Ordertype";
    public static final String Column_MealTime = "MealTime";
    public static final String Column_MealType = "MealType";
    public static final String Column_PackageType_SC = "PackageType_SC";
    public static final String Column_PackageTypeID = "PackageTypeID";
    public static final String Column_PackageName_SGD= "PackageName_SGD";
    public static final String Column_Quantity= "Quantity";
    public static final String Column_PackagePrice= "PackagePrice";
    public static final String Column_PackageDesc= "PackageDesc";
    public static final String Column_PackageName= "PackageName";


    /*********************Cart Item Columns*********************/
   /* public static final String Column_orderId= "orderId";
    public static final String Column_dealerId = "dealerId";
    public static final String Column_retailerId = "retailerId";
    public static final String Column_productId = "productId";
    public static final String Column_quantity = "quantity";
    public static final String Column_statusId = "statusId";
    public static final String Column_receivedQuantity = "receivedQuantity";
    public static final String Column_dealerNames= "dealerNames";
    public static final String Column_productName= "productName";
    public static final String Column_productDesc= "productDesc";
    public static final String Column_imageUrl= "imageUrl";
    public static final String Column_dealersName= "dealersName";*/

    public static final String Column_CompanyId= "company_id";
    public static final String Column_ProductId= "product_id";
    public static final String Column_CartQuantity= "quantity";
    public static final String Column_TotalAmount= "total_amount";
    public static final String Column_StatusId= "status_id";
    public static final String Column_ImageURL = "image_url";

    /*********************Ordered Products***********************/

    public static final String Column_orderId = "order_id";
    public static final String Column_orderOn = "ordered_on";
    public static final String Column_quantity = "quantity";
    public static final String Column_totalAmount = "total_amount";
    public static final String Column_productId = "product_id";
    public static final String Column_productImage = "product_image";
    public static final String Column_productDescription = "product_description";
    public static final String Column_categoryName = "category_name";
    public static final String Column_subCategoryName = "sub_category_name";
    public static final String Column_type = "type";
    public static final String Column_orderStatus = "order_status";
    public static final String Column_statusShortCode = "status_short_code";
    public static final String Column_orderedBy = "ordered_by";
    public static final String Column_userName = "user_name";
    public static final String Column_mobile = "mobile_no";
    public static final String Column_cityName = "city_name";
    public static final String Column_stateName = "state_name";
    public static final String Column_orderAddress = "address";
    public static final String Column_pincode = "pin_code";
    public static final String Column_checked = "itemChecked";
    public static final String Column_statusId = "statusId";


    /*************Supplier Shopper Columns*/

    public static final String Column_Demand_Order_Id ="demand_order_id";
    public static final String Column_Order_Num ="order_number";
    public static final String Column_SH_Quantity ="quantity";
    public static final String Column_SH_Total_Amt ="total_amount";
    public static final String Column_SH_Rec_Qty ="received_quantity";
    public static final String Column_SH_Product_Id="product_id";
    public static final String Column_SH_Image_Url ="image_url";
    public static final String Column_SH_Product_Desc ="product_description";
    public static final String Column_SH_Status_SC ="status_short_code";
    public static final String Column_SH_Status_Desc ="status_description";
    public static final String Column_SH_Type="type";
    public static final String Column_SH_Sub_Category="sub_category_name";
    public static final String Column_SH_Category="category_name";
    public static final String Column_SH_CompanyName="company_name";
    public static final String Column_SH_UserName="user_name";
    public static final String Column_SH_Mobile="mobile_no";
    public static final String Column_SH_City="city_name";
    public static final String Column_SH_State="state_name";
    public static final String Column_SH_Address="address";
    public static final String Column_SH_Pin="pin_code";
    public static final String Column_SH_Checked="is_checked";
    public static final String Column_SH_Status_ID="statusId";
    public static final String Column_SH_DISPATCHED_QTY="dispatchedQty";

    /*********************User Roles Column*********************/
    public static final String Column_typeId= "type_id";
    public static final String Column_typeCode = "type_code";
    public static final String Column_typeDescription = "type_description";
    /*********************Installation Check*******************/
    public static final String Column_id = "id";
    public static final String Column_state = "state";

    /******************** Database Name ************/
    public static final String DATABASE_NAME = "OME 0.1";

    /******************** Database Version (Increase one if want to also upgrade your database) ************/
    public static final int DATABASE_VERSION = 1;// started at 1

    /** Table names */
    public static final String Table_Cartlist = "cartlist";
    public static final String Table_UserAddress ="userAddress";
    public static final String Table_InstallFlag ="install_Flag_Table";
    public static final String Table_ProductCart ="product_cart";
    public static final String Table_UserRoles ="user_roles";
    public static final String Table_OrderProducts ="ordered_products";
    public static final String Table_ShoppersOrders ="shoppers_orders";


    /******************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ************/
    private static final String[] ALL_TABLES = { Table_UserAddress, Table_InstallFlag, Table_ProductCart, Table_UserRoles, Table_OrderProducts, Table_ShoppersOrders};

    /** Create table syntax */
    private static final String USER_Address = "create table userAddress(custId INTEGER, addressId TEXT, houseNo TEXT, address TEXT, landmark TEXT, city TEXT, zipcode TEXT, addressType TEXT);";

    private static final String APP_INSTALL = "create table install_Flag_Table(id INTEGER, state INTEGER);";

    private static final String USER_PRODUCT_CART = "create table product_cart(company_id Text, product_id Text, quantity INTEGER, total_amount Text, statusId INTEGER, image_url Text, cartItemId INTEGER PRIMARY KEY AUTOINCREMENT);";

    private static final String USER_ROLES = "create table user_roles(type_id INTEGER, type_code TEXT, type_description TEXT);";

    private static final String ORDERED_PRODUCTS = "create table ordered_products" +
            "(order_id INTEGER,ordered_on TEXT, quantity INTEGER, total_amount DOUBLE, product_id INTEGER," +
            " product_image TEXT, product_description TEXT, category_name TEXT, sub_category_name TEXT," +
            "type TEXT, order_status TEXT, status_short_code TEXT, ordered_by INTEGER, user_name TEXT," +
            "mobile_no TEXT, city_name TEXT, state_name TEXT, address TEXT, pin_code INTEGER, itemChecked TEXT, statusId INTEGER);";

    private static final String SHOPPER_ORDERS = "create table shoppers_orders(" +
            "demand_order_id INTEGER,order_number INTEGER, quantity TEXT, total_amount TEXT, received_quantity INTEGER," +
            "product_id INTEGER, image_url TEXT, product_description TEXT,status_short_code TEXT," +
            " status_description TEXT, type TEXT, sub_category_name TEXT, category_name TEXT," +
            "company_name TEXT, user_name TEXT, mobile_no TEXT, city_name TEXT, state_name TEXT," +
            "address TEXT, pin_code INTEGER, is_checked TEXT, statusId INTEGER, dispatchedQty INTEGER);";




    /******************** Used to open database in synchronized way ************/
    private static DataBaseHelper DBHelper = null;

    protected CartDatabase() {
    }
    /******************* Initialize database *************/
    public static void init(Context context) {
        if (DBHelper == null) {
            if (DEBUG)
                Log.i("DBAdapter", context.toString());
            DBHelper = new DataBaseHelper(context);
        }
    }
    /********************** Main Database creation INNER class ********************/
    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (DEBUG)
                Log.i(LOG_TAG, "new create");
            try {
              //db.execSQL(USER_CREATECARTLIST);
                db.execSQL(USER_Address);
                db.execSQL(APP_INSTALL);
                db.execSQL(USER_PRODUCT_CART);
                db.execSQL(USER_ROLES);
                db.execSQL(ORDERED_PRODUCTS);
                db.execSQL(SHOPPER_ORDERS);

            } catch (Exception exception) {
                if (DEBUG)
                    Log.i(LOG_TAG, "Exception onCreate() exception");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (DEBUG)
                Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
                        + "to" + newVersion + "...");

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }

    } // Inner class closed


    /********************** Open database for insert,update,delete in syncronized manner ********************/
    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }

    /*********************** Escape string for single quotes (Insert,Update)************/
    private static String sqlEscapeString(String aString) {
        String aReturn = "";

        if (null != aString) {
            //aReturn = aString.replace("'", "''");
            aReturn = DatabaseUtils.sqlEscapeString(aString);
            // Remove the enclosing single quotes ...
            aReturn = aReturn.substring(1, aReturn.length() - 1);
        }

        return aReturn;
    }
    /*********************** UnEscape string for single quotes (show data)************/
    private static String sqlUnEscapeString(String aString) {

        String aReturn = "";

        if (null != aString) {
            aReturn = aString.replace("''", "'");
        }

        return aReturn;
    }

    /**
     * All Operations for Cart(Create, Read, Update, Delete)
     */

    public static void addProductToOrderList(SupplierConsumerOrderProduct placeOrderDO) {
        final SQLiteDatabase db = open();
        int order_id = placeOrderDO.orderId;
        String ordered_on = placeOrderDO.orderedOn;
        String quantity = placeOrderDO.quantity;
        int total_amount = placeOrderDO.totalAmount;
        int product_id = placeOrderDO.productId;
        String product_image = placeOrderDO.productImage;
        String product_description = placeOrderDO.productDescription;
        String category_name = placeOrderDO.categoryName;
        String sub_category_name = placeOrderDO.subCategoryName;
        String order_status = placeOrderDO.orderStatus;
        String type = placeOrderDO.type;
        String status_short_code = placeOrderDO.statusShortCode;
        int ordered_by = placeOrderDO.orderedBy;
        String user_name = placeOrderDO.userName;
        String mobile_no = placeOrderDO.mobileNo;
        String city_name = placeOrderDO.cityName;
        String state_name = placeOrderDO.stateName;
        String address = placeOrderDO.address;
        int pin_code = placeOrderDO.pinCode;
        String itemChecked = placeOrderDO.checked;
        int statusId = placeOrderDO.statusId;

//========================================================================================================================
        ContentValues cv = new ContentValues();
        cv.put(Column_orderId, order_id);
        cv.put(Column_orderOn, ordered_on);
        cv.put(Column_quantity, quantity);
        cv.put(Column_totalAmount, total_amount);
        cv.put(Column_productId, product_id);
        cv.put(Column_productImage, product_image);
        cv.put(Column_productDescription, product_description);
        cv.put(Column_categoryName, category_name);
        cv.put(Column_subCategoryName, sub_category_name);
        cv.put(Column_orderStatus, order_status);
        cv.put(Column_type, type);
        cv.put(Column_statusShortCode, status_short_code);
        cv.put(Column_orderedBy, ordered_by);
        cv.put(Column_userName, user_name);
        cv.put(Column_mobile, mobile_no);
        cv.put(Column_cityName, city_name);
        cv.put(Column_stateName, state_name);
        cv.put(Column_orderAddress, address);
        cv.put(Column_pincode, pin_code);
        cv.put(Column_checked, itemChecked);
        cv.put(Column_statusId, state_name);
        cv.put(Column_statusId, statusId);

        db.insert(Table_OrderProducts, null, cv);
        db.close(); // Closing database connection
    }

    public static void addProductToOrderHistoryList(SupplierConsumerHistoryProductObject placeOrderDO) {
        final SQLiteDatabase db = open();
        int order_id = placeOrderDO.orderId;
        String ordered_on = placeOrderDO.orderedOn;
        String quantity = placeOrderDO.quantity;
        int total_amount = placeOrderDO.totalAmount;
        int product_id = placeOrderDO.productId;
        String product_image = placeOrderDO.productImage;
        String product_description = placeOrderDO.productDescription;
        String category_name = placeOrderDO.categoryName;
        String sub_category_name = placeOrderDO.subCategoryName;
        String order_status = placeOrderDO.orderStatus;
        String type = placeOrderDO.type;
        String status_short_code = placeOrderDO.statusShortCode;
        int ordered_by = 0;
        String user_name = "";
        String mobile_no = placeOrderDO.mobileNo;
        String city_name = "";
        String state_name = "";
        String address = "";
        int pin_code = 0;
        String itemChecked = "";
        int statusId = 0;

//========================================================================================================================
        ContentValues cv = new ContentValues();
        cv.put(Column_orderId, order_id);
        cv.put(Column_orderOn, ordered_on);
        cv.put(Column_quantity, quantity);
        cv.put(Column_totalAmount, total_amount);
        cv.put(Column_productId, product_id);
        cv.put(Column_productImage, product_image);
        cv.put(Column_productDescription, product_description);
        cv.put(Column_categoryName, category_name);
        cv.put(Column_subCategoryName, sub_category_name);
        cv.put(Column_orderStatus, order_status);
        cv.put(Column_type, type);
        cv.put(Column_statusShortCode, status_short_code);
        cv.put(Column_orderedBy, ordered_by);
        cv.put(Column_userName, user_name);
        cv.put(Column_mobile, mobile_no);
        cv.put(Column_cityName, city_name);
        cv.put(Column_stateName, state_name);
        cv.put(Column_orderAddress, address);
        cv.put(Column_pincode, pin_code);
        cv.put(Column_checked, itemChecked);
        cv.put(Column_statusId, state_name);
        cv.put(Column_statusId, statusId);

        db.insert(Table_OrderProducts, null, cv);
        db.close(); // Closing database connection
    }



    public static void addProductToCart(PlaceOrderDO placeOrderDO) {
        final SQLiteDatabase db = open();
        String company_id = placeOrderDO.company_id;
        String product_id = placeOrderDO.product_id;
        int quantity = placeOrderDO.quantity;
        String total_amount = placeOrderDO.total_amount;
        int status_id = placeOrderDO.status_id;
        String image_url = placeOrderDO.imageUrl;

//========================================================================================================================
        ContentValues cv = new ContentValues();
        cv.put(Column_CompanyId, company_id);
        cv.put(Column_ProductId, product_id);
        cv.put(Column_CartQuantity, quantity);
        cv.put(Column_TotalAmount, total_amount);
        cv.put(Column_StatusId, status_id);
        cv.put(Column_ImageURL, image_url);
        db.insert(Table_ProductCart, null, cv);
        db.close(); // Closing database connection
    }


 public static void addShopperProductToCart(SupplierShopperOrderResultObject placeOrderDO) {
        final SQLiteDatabase db = open();
        String demand_order_id = String.valueOf(placeOrderDO.demandOrderId);
        String order_number = String.valueOf(placeOrderDO.orderNumber);
        String quantity = String.valueOf(placeOrderDO.quantity);
        String total_amount = String.valueOf(placeOrderDO.totalAmount);
        String received_quantity = String.valueOf(placeOrderDO.receivedQuantity);
        String product_id = String.valueOf(placeOrderDO.productId);
        String image_url = String.valueOf(placeOrderDO.imageUrl);
        String product_description = String.valueOf(placeOrderDO.productDescription);
        String status_short_code = String.valueOf(placeOrderDO.statusShortCode);
        String status_description = String.valueOf(placeOrderDO.statusDescription);
        String type = String.valueOf(placeOrderDO.type);
        String sub_category_name = String.valueOf(placeOrderDO.subCategoryName);
        String category_name= String.valueOf(placeOrderDO.categoryName);
        String company_name= String.valueOf(placeOrderDO.companyName);
        String user_name= String.valueOf(placeOrderDO.userName);
        String mobile_no= String.valueOf(placeOrderDO.mobileNo);
        String city_name= String.valueOf(placeOrderDO.cityName);
        String state_name= String.valueOf(placeOrderDO.stateName);
        String address= String.valueOf(placeOrderDO.address);
        String pin_code= String.valueOf(placeOrderDO.pinCode);
        String is_checked= String.valueOf(placeOrderDO.checked);
        int statusId = placeOrderDO.statusId;
        int dispatchedQty = placeOrderDO.dispachedQty;

//========================================================================================================================
        ContentValues cv = new ContentValues();
        cv.put(Column_Demand_Order_Id, demand_order_id);
        cv.put(Column_Order_Num, order_number);
        cv.put(Column_SH_Quantity, quantity);
        cv.put(Column_SH_Total_Amt, total_amount);
        cv.put(Column_SH_Rec_Qty, received_quantity);
        cv.put(Column_SH_Product_Id, product_id);
        cv.put(Column_SH_Image_Url, image_url);
        cv.put(Column_SH_Product_Desc, product_description);
        cv.put(Column_SH_Status_SC, status_short_code);
        cv.put(Column_SH_Status_Desc, status_description);
        cv.put(Column_SH_Type, type);
        cv.put(Column_SH_Sub_Category, sub_category_name);
        cv.put(Column_SH_Category, category_name);
        cv.put(Column_SH_CompanyName, company_name);
        cv.put(Column_SH_UserName, user_name);
        cv.put(Column_SH_Mobile, mobile_no);
        cv.put(Column_SH_City, city_name);
        cv.put(Column_SH_State, state_name);
        cv.put(Column_SH_Address, address);
        cv.put(Column_SH_Pin, pin_code);
        cv.put(Column_SH_Checked, is_checked);
        cv.put(Column_SH_Status_ID, statusId);
        cv.put(Column_SH_DISPATCHED_QTY, dispatchedQty);
        db.insert(Table_ShoppersOrders, null, cv);
        db.close(); // Closing database connection
    }

//************************* Install Flag **************************************

    //*****************************************************************************
    public static void SaveInstallState(int id,int state)
    {
        final SQLiteDatabase db = open();

        ContentValues value = new ContentValues();
        value.put(Column_id, id);
        value.put(Column_state, state);
        db.insert(Table_InstallFlag, null, value);
        db.close(); // Closing database connection

    }

    public static int getInstallstatecount()
    {
        SQLiteDatabase db = open();
        Cursor cursor;
        cursor=db.rawQuery("SELECT count(*) FROM "+Table_InstallFlag,null );
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }

    public static int getInstallstate(int id)
    {
        int notificationstate=-1;

        final SQLiteDatabase db = open();
        Cursor cursor = db.query(Table_InstallFlag, new String[]{Column_id, Column_state}, Column_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                notificationstate=cursor.getInt(1);
            } while (cursor.moveToNext());
        }

        return notificationstate;
    }

    public static int updateInstallourstate(int id,int state)
    {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put(Column_state, state);

        // updating row
        return db.update(Table_InstallFlag, values, Column_id + " = ?",new String[] { String.valueOf(id) });
    }


    public static int updateCheckStatus(int order_id, String value, int quantity)
    {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put(Column_checked, value);
        values.put(Column_quantity, quantity);

        // updating row
        return db.update(Table_OrderProducts, values, Column_orderId + " = ?",new String[] {String.valueOf(order_id)});
    }


    public static int updateCheckStatusShopper(int demandOrderId, String value, String quantity)
    {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put(Column_SH_Checked, value);
        values.put(Column_SH_DISPATCHED_QTY, quantity);

        // updating row
        return db.update(Table_ShoppersOrders, values, Column_Demand_Order_Id + " = ?",new String[] {String.valueOf(demandOrderId)});
    }

    //***********************************************************************************

    //**************************Address***********************************
    public static void addUserAddress(AddressDO addressDO) {
        final SQLiteDatabase db = open();


        String addressId = sqlEscapeString(addressDO.addressId);
        String houseNo = sqlEscapeString(addressDO.houseNo);
        String address = sqlEscapeString(addressDO.address);
        String landmark = sqlEscapeString(addressDO.landmark);
        String customerId = sqlEscapeString(addressDO.customerId);
        String city = sqlEscapeString(addressDO.city);
        String zipcode = sqlEscapeString(addressDO.zipcode);
        String addressType = sqlEscapeString(addressDO.addressType);


        ContentValues cv = new ContentValues();
        cv.put(Column_custId, customerId);
        cv.put(Column_addressId, addressId);
        cv.put(Column_houseNo, houseNo);
        cv.put(Column_address, address);
        cv.put(Column_landmark, landmark);
        cv.put(Column_city, city);
        cv.put(Column_zipcode, zipcode);
        cv.put(Column_addressType, addressType);

        db.insert(Table_UserAddress, null, cv);
        db.close(); // Closing database connection

    }
    // Getting All Cartlist
    public static ArrayList<PlaceOrderDO> getAllProductCart() {
        ArrayList<PlaceOrderDO> cartList = new ArrayList<PlaceOrderDO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_ProductCart;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*dealerId  retailerId productId  quantity  statusId receivedQuantity dealerNames  productName  productDesc*/
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceOrderDO data = new PlaceOrderDO();
                data.company_id = cursor.getString(0);
                data.product_id = cursor.getString(1);
                data.quantity = cursor.getInt(2);
                data.total_amount = cursor.getString(3);
                data.status_id = cursor.getInt(5);
                data.imageUrl = cursor.getString(6);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        // return cartlist list
        return cartList;
    }

   // Getting All ProductList
    public static ArrayList<SupplierConsumerOrderProduct> getAllOrderProduct() {
        ArrayList<SupplierConsumerOrderProduct> cartList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_OrderProducts;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
          if (cursor.moveToFirst()) {
            do {
                SupplierConsumerOrderProduct data = new SupplierConsumerOrderProduct();
                data.orderId = cursor.getInt(0);
                data.orderedOn = cursor.getString(1);
                data.quantity = cursor.getString(2);
                data.totalAmount = cursor.getInt(3);
                data.productId = cursor.getInt(4);
                data.productImage = cursor.getString(5);
                data.productDescription = cursor.getString(6);
                data.categoryName = cursor.getString(7);
                data.subCategoryName = cursor.getString(8);
                data.type = cursor.getString(9);
                data.orderStatus = cursor.getString(10);
                data.statusShortCode = cursor.getString(11);
                data.orderedBy = cursor.getInt(12);
                data.userName = cursor.getString(13);
                data.mobileNo = cursor.getString(14);
                data.cityName = cursor.getString(15);
                data.stateName = cursor.getString(16);
                data.address = cursor.getString(17);
                data.pinCode = cursor.getInt(18);
                data.checked = cursor.getString(19);
                data.statusId = cursor.getInt(20);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }

    public static ArrayList<SupplierShopperOrderResultObject> getAllShopperOrderProduct() {
        ArrayList<SupplierShopperOrderResultObject> cartList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_ShoppersOrders;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
          if (cursor.moveToFirst()) {
            do {
                SupplierShopperOrderResultObject data = new SupplierShopperOrderResultObject();
                data.demandOrderId = cursor.getInt(0);
                data.orderNumber = cursor.getInt(1);
                data.quantity = cursor.getInt(2);
                data.totalAmount = cursor.getInt(3);
                data.receivedQuantity = cursor.getInt(4);
                data.productId = cursor.getInt(5);
                data.imageUrl = cursor.getString(6);
                data.productDescription = cursor.getString(7);
                data.statusShortCode = cursor.getString(8);
                data.statusDescription = cursor.getString(9);
                data.type = cursor.getString(10);
                data.subCategoryName = cursor.getString(11);
                data.categoryName = cursor.getString(12);
                data.companyName = cursor.getString(13);
                data.userName = cursor.getString(14);
                data.mobileNo = cursor.getString(15);
                data.cityName = cursor.getString(16);
                data.stateName = cursor.getString(17);
                data.address = cursor.getInt(18);
                data.pinCode = cursor.getInt(19);
                data.checked = cursor.getString(20);
                data.statusId = cursor.getInt(21);
                data.dispachedQty = cursor.getInt(22);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }


    public static ArrayList<SupplierShopperOrderResultObject> getShopperOrderProductWithOrderId(int demand_order_id) {
        ArrayList<SupplierShopperOrderResultObject> cartList = new ArrayList<>();
        // Select All Query
       // String selectQuery = "SELECT  * FROM " + Table_ShoppersOrders;
        String selectQuery="select * from shoppers_orders where demand_order_id=" +demand_order_id;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
          if (cursor.moveToFirst()) {
            do {
                SupplierShopperOrderResultObject data = new SupplierShopperOrderResultObject();
                data.demandOrderId = cursor.getInt(0);
                data.orderNumber = cursor.getInt(1);
                data.quantity = cursor.getInt(2);
                data.totalAmount = cursor.getInt(3);
                data.receivedQuantity = cursor.getInt(4);
                data.productId = cursor.getInt(5);
                data.imageUrl = cursor.getString(6);
                data.productDescription = cursor.getString(7);
                data.statusShortCode = cursor.getString(8);
                data.statusDescription = cursor.getString(9);
                data.type = cursor.getString(10);
                data.subCategoryName = cursor.getString(11);
                data.categoryName = cursor.getString(12);
                data.companyName = cursor.getString(13);
                data.userName = cursor.getString(14);
                data.mobileNo = cursor.getString(15);
                data.cityName = cursor.getString(16);
                data.stateName = cursor.getString(17);
                data.address = cursor.getInt(18);
                data.pinCode = cursor.getInt(19);
                data.checked = cursor.getString(20);
                data.statusId = cursor.getInt(21);
                data.dispachedQty = cursor.getInt(22);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }

   // Getting All ProductList
    public static ArrayList<SupplierConsumerOrderProduct> getAllConsumerOrderProduct() {
        ArrayList<SupplierConsumerOrderProduct> cartList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_OrderProducts;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
          if (cursor.moveToFirst()) {
            do {
                SupplierConsumerOrderProduct data = new SupplierConsumerOrderProduct();
                data.orderId = cursor.getInt(0);
                data.orderedOn = cursor.getString(1);
                data.quantity = cursor.getString(2);
                data.totalAmount = cursor.getInt(3);
                data.productId = cursor.getInt(4);
                data.productImage = cursor.getString(5);
                data.productDescription = cursor.getString(6);
                data.categoryName = cursor.getString(7);
                data.subCategoryName = cursor.getString(8);
                data.type = cursor.getString(9);
                data.orderStatus = cursor.getString(10);
                data.statusShortCode = cursor.getString(11);
                data.orderedBy = cursor.getInt(12);
                data.userName = cursor.getString(13);
                data.mobileNo = cursor.getString(14);
                data.cityName = cursor.getString(15);
                data.stateName = cursor.getString(16);
                data.address = cursor.getString(17);
                data.pinCode = cursor.getInt(18);
                data.checked = cursor.getString(19);
                data.statusId = cursor.getInt(20);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }

    // Getting All Cartlist
    public static ArrayList<PlaceOrderDO> getTwoProductCart(int cartSize) {
        ArrayList<PlaceOrderDO> cartList = new ArrayList<PlaceOrderDO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_ProductCart;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*dealerId  retailerId productId  quantity  statusId receivedQuantity dealerNames  productName  productDesc*/
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            for (int i= 0; i<cartSize; i++)
            {
                PlaceOrderDO data = new PlaceOrderDO();
                data.company_id = cursor.getString(0);
                data.product_id = cursor.getString(1);
                data.quantity = cursor.getInt(2);
                data.total_amount = cursor.getString(3);
                data.status_id = cursor.getInt(4);
                data.imageUrl = cursor.getString(5);
                cartList.add(data);
                cursor.moveToNext();
            }

        }
        // return cartlist list
        return cartList;
    }
    // Getting All Cartlist
    public static ArrayList<AddressDO> getAddressById(String addressType) {
        ArrayList<AddressDO> addressList = new ArrayList<>();
        final SQLiteDatabase db = open();
        String query="select * from userAddress where addressType="+"'"+addressType+"'";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddressDO data = new AddressDO();
                data.customerId =cursor.getString(0);
                data.addressId = cursor.getString(1);
                data.houseNo=cursor.getString(2);
                data.address=cursor.getString(3);
                data.landmark = cursor.getString(4);
                data.city = cursor.getString(5);
                data.zipcode=cursor.getString(6);
                data.addressType = cursor.getString(7);
                addressList.add(data);
            } while (cursor.moveToNext());
        }
        return addressList;
    }


    public static ArrayList<PlaceOrderDO> getCartListWithProductId(int productId) {
        ArrayList<PlaceOrderDO> cartList = new ArrayList<>();
        final SQLiteDatabase db = open();
        String query="select * from product_cart where product_id=" +productId;
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceOrderDO data = new PlaceOrderDO();
                data.company_id = cursor.getString(0);
                data.product_id = cursor.getString(1);
                data.quantity = cursor.getInt(2);
                data.total_amount = cursor.getString(3);
                data.status_id = cursor.getInt(4);
                data.imageUrl = cursor.getString(5);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }

    public static ArrayList<PlaceOrderDO> getCartListWithDealerID(int dealerId, int productId) {
        ArrayList<PlaceOrderDO> cartList = new ArrayList<>();
        final SQLiteDatabase db = open();
        String query="select * from product_cart where dealerId=" +dealerId +" and productId=" + productId;
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceOrderDO data = new PlaceOrderDO();
                data.company_id = cursor.getString(0);
                data.product_id = cursor.getString(1);
                data.quantity = cursor.getInt(2);
                data.total_amount = cursor.getString(3);
                data.status_id = cursor.getInt(4);
                data.imageUrl = cursor.getString(5);
                cartList.add(data);
            } while (cursor.moveToNext());
        }
        return cartList;
    }


    // Deleting single item in cartlist
    public static void deletecartlist(PlaceOrderDO data)
    {
        final SQLiteDatabase db = open();
        //   db.delete(Table_Cartlist, Column_itemid + " = ?", new String[]{String.valueOf(data.itemId)});

        String delete_blauky="DELETE  from product_cart where product_id="+ data.product_id;
        db.execSQL(delete_blauky);

        db.close();
    }


    public static void clearProductCart()
    {
        SQLiteDatabase db = open();
        db.execSQL("delete from " + Table_ProductCart);
    }

    public static void clearRoles()
    {
        SQLiteDatabase db = open();
        db.execSQL("delete from " + Table_UserRoles);
    }

    public static void clearConsumerOrderList()
    {
        SQLiteDatabase db = open();
        db.execSQL("delete from " + Table_OrderProducts);
    }

    public static void clearShopperOrderList()
    {
        SQLiteDatabase db = open();
        db.execSQL("delete from " + Table_ShoppersOrders);
    }

    // Getting cartlist Count
    public static int getCartlistCount()
    {
        SQLiteDatabase db = open();
        Cursor cursor;
        cursor=db.rawQuery("SELECT count(*) FROM "+ Table_ProductCart,null );
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }
    // Getting orderList Count
    public static int getProductListCount()
    {
        SQLiteDatabase db = open();
        Cursor cursor;
        cursor=db.rawQuery("SELECT count(*) FROM "+ Table_OrderProducts,null );
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }
    // Getting userList Count
    public static int getUserRolesListCount()
    {
        SQLiteDatabase db = open();
        Cursor cursor;
        cursor=db.rawQuery("SELECT count(*) FROM "+ Table_UserRoles,null );
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        cursor.close();
        return icount;
    }


    public static void addUserRoles(AccountListResultObject accounts) {
        final SQLiteDatabase db = open();
        int type_id = accounts.typeId;
        String type_code = accounts.typeCode;
        String type_description = accounts.typeDescription;
//========================================================================================================================
        ContentValues cv = new ContentValues();
        cv.put(Column_typeId, type_id);
        cv.put(Column_typeCode, type_code);
        cv.put(Column_typeDescription, type_description);
        db.insert(Table_UserRoles, null, cv);
        db.close(); // Closing database connection
    }

    // Getting All Cartlist
    public static ArrayList<AccountListResultObject> getAllUserRoles() {
        ArrayList<AccountListResultObject> rolesList = new ArrayList<AccountListResultObject>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + Table_UserRoles;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*dealerId  retailerId productId  quantity  statusId receivedQuantity dealerNames  productName  productDesc*/
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccountListResultObject data = new AccountListResultObject();
                data.typeId = cursor.getInt(0);
                data.typeCode = cursor.getString(1);
                data.typeDescription = cursor.getString(2);
                rolesList.add(data);
            } while (cursor.moveToNext());
        }
        // return cartlist list
        return rolesList;
    }


}



