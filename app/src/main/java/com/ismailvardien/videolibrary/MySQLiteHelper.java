package com.ismailvardien.videolibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "BookDB";


    private static final String TABLE_MOVIES = "movies";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRANSACTIONS = "transactions";


    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_CODE = "movieCode";
    private static final String KEY_PRICE = "price";
    private static final String KEY_FIFTEEN = "fifteen";
    private static final String KEY_SIXTEEN = "sixteen";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_NUMBER = "number";
    private static final String KEY_USERNAMETRAN = "username";
    private static final String KEY_TYPE = "type";
    private static final String KEY_RETURN = "return";
    private static final String KEY_PICKUP = "pickup";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_TITLETRAN = "title";
    private static final String KEY_COST = "cost";
    private static final String KEY_TYPENUMBER = "typenumber";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_PICKDAYYEAR = "pickdayyear";
    private static final String KEY_DROPDAYYEAR = "dropdayyear";
    private static final String KEY_RESERVATION = "reservation";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Log TAG for debugging purpose
    private static final String TAG = "SQLiteAppLog";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create a table called "movies"
        String CREATE_BOOK_TABLE = "CREATE TABLE movies ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "director TEXT, " +
                "movieCode TEXT, " +
                "price REAL, " +
                "fifteen TEXT, " +
                "sixteen TEXT)";

        String CREATE_USER_TABLE = "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, "+
                "password TEXT)";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE transactions ( " +
                "number INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, "+
                "type TEXT, "+
                "title TEXT, "+
                "pickup TEXT, " +
                "return TEXT, " +
                "cost REAL, " +
                "date TEXT, " +
                "time TEXT, " +
                "active INTEGER, " +
                "typenumber INTEGER, " +
                "pickdayyear INTEGER, " +
                "dropdayyear INTEGER, "+
                "reservation INTEGER)";

        // execute an SQL statement to create the table
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    // onUpdate() is invoked when you upgrade the database scheme.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older movies table if existed
        db.execSQL("DROP TABLE IF EXISTS movies");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        // create fresh movies and users table
        this.onCreate(db);
    }
    //******************************METHODS FOR MOVIE*******************************************

    public void addMovie(Movie movie){
        Log.d(TAG, "addMovie() - " + movie.toString());
        //  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle()); // get title
        values.put(KEY_DIRECTOR, movie.getDirector()); // get director
        values.put(KEY_CODE, movie.getMovieCode()); // get code
        values.put(KEY_PRICE, movie.getPrice());
        System.out.println("movie.getFifteenString()" + movie.getFifteenString());
        System.out.println("movie.getSixteenString()" + movie.getSixteenString());
        values.put(KEY_FIFTEEN, movie.getFifteenString());
        values.put(KEY_SIXTEEN, movie.getSixteenString());
        //  insert
        db.insert(TABLE_MOVIES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close - release the reference of writable DB
        db.close();
    }

    // Get all movies from the database
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        //  build the query
        String query = "SELECT  * FROM " + TABLE_MOVIES;

        //  get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //  go over each row, build movie and add it to list
        Movie movie = null;
        if (cursor.moveToFirst()) {
            do {
                movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                //System.out.println("TESTid: " + Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setDirector(cursor.getString(2));
                movie.setMovieCode(cursor.getString(3));
                movie.setPrice(Double.parseDouble(cursor.getString(4)));
                System.out.println("cursor.getString(5)" + cursor.getString(5));
                movie.setFifteenArray(cursor.getString(5));
                System.out.println("cursor.getString(6)" + cursor.getString(6));
                movie.setSixteenArray(cursor.getString(6));
                // Add movie to movies
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllMovies() - " + movies.toString());

        // return movies
        return movies;
    }

    // Updating single movie
    public int updateBook(Movie movie) {

        //  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", movie.getTitle()); // get title
        values.put("director", movie.getDirector()); // get author
        values.put("code", movie.getMovieCode()); // get author
        values.put("price", movie.getPrice());
        values.put("fifteen", movie.getFifteenString());
        values.put("sixteen", movie.getSixteenString());
        //  updating row
        int i = db.update(TABLE_MOVIES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(movie.getId()) }); //selection args

        //  close
        db.close();

        return i;

    }

    // Deleting single movie
    public void deleteBook(Movie movie) {

        //  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  delete
        db.delete(TABLE_MOVIES,
                KEY_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});

        //  close
        db.close();

        Log.d(TAG, "deleteMovie() - " + movie.toString());
    }

    //*****************METHODS FOR USER TABLE***********************

    public void addUser(User user){
        Log.d(TAG, "addUser() - " + user.toString());
        //  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername()); // get title
        values.put(KEY_PASSWORD, user.getPassword()); // get author

        //  insert to table
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        //  close - release the reference of writable DB
        db.close();
    }

    // Get all movies from the database
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();

        // build the query
        String query = "SELECT  * FROM " + TABLE_USERS;

        //  get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // go over each row, build book and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user= new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                //System.out.println("TESTid: " + Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));

                // Add book to movies
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllUsers() - " + users.toString());

        // return movies
        return users;
    }

    // Deleting single book
    public void deleteUser(User user) {

        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  delete
        db.delete(TABLE_USERS,
                KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});

        // close
        db.close();

        Log.d(TAG, "deleteUser() - " + user.toString());
    }

    //*****************METHODS FOR TRANSACTIONS TABLE***********************

    public void addTransaction(Transaction transaction){
        Log.d(TAG, "addTransaction() - " + transaction.toString());
        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAMETRAN, transaction.getUsername());
        System.out.println("TESTTEST: transaction get username" + transaction.getUsername());
        values.put(KEY_TYPE, transaction.getType());
        values.put(KEY_TITLETRAN, transaction.getTitle());
        values.put(KEY_PICKUP, transaction.getPickUpDate());
        values.put(KEY_RETURN, transaction.getDropOffDate());
        values.put(KEY_COST, transaction.getRentalCost());
        values.put(KEY_DATE, transaction.getDate());
        values.put(KEY_TIME, transaction.getTime());

        System.out.println("TESTTEST: transaction get active" + transaction.getActive());
        values.put(KEY_ACTIVE, transaction.getActive());

        values.put(KEY_TYPENUMBER, transaction.getTypeNumber());
        values.put(KEY_PICKDAYYEAR, transaction.getPickDayYear());
        values.put(KEY_DROPDAYYEAR, transaction.getDropDayYear());
        values.put(KEY_RESERVATION, transaction.getReservation());


        // insert to table
        db.insert(TABLE_TRANSACTIONS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // close - release the reference of writable DB
        db.close();
    }

    // Get all transactions from the database
    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        //  build the query
        String query = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        //  get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //  go over each row, build book and add it to list
        Transaction transaction = null;
        if (cursor.moveToFirst()) {
            do {
                transaction= new Transaction();

                transaction.setId(Integer.parseInt(cursor.getString(0)));
                System.out.println("cursor0: " + cursor.getString(0));
                transaction.setUsername(cursor.getString(1));
                System.out.println("acursor1: " + cursor.getString(1));

                System.out.println("acursor2: " + cursor.getString(2));
                System.out.println("acursor3: " + cursor.getString(3));
                System.out.println("acursor4: " + cursor.getString(4));
                System.out.println("acursor5: " + cursor.getString(5));
                System.out.println("acursor6: " + cursor.getString(6));
                System.out.println("acursor7: " + cursor.getString(7));
                System.out.println("acursor8: " + cursor.getString(8));
                System.out.println("acursor9: " + cursor.getString(9));
                System.out.println("acursor10: " + cursor.getString(10));



                transaction.setType(cursor.getString(2));
                transaction.setTitle(cursor.getString(3));
                transaction.setPickUpDate(cursor.getString(4));
                transaction.setDropOffDate(cursor.getString(5));
                transaction.setRentalCost(Double.parseDouble(cursor.getString(6)));
                transaction.setDate(cursor.getString(7));
                transaction.setTime(cursor.getString(8));
                transaction.setActive(Integer.parseInt(cursor.getString(9)));
                transaction.setTypeNumber(Integer.parseInt(cursor.getString(10)));
                transaction.setPickDayYear(Integer.parseInt(cursor.getString(11)));
                transaction.setDropDayYear(Integer.parseInt(cursor.getString(12)));
                transaction.setReservation(Integer.parseInt(cursor.getString(13)));

                // Add book to movies
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllTransactions() - " + transactions.toString());

        // return movies
        return transactions;
    }

    //update transaction
    public int updateTransaction(Transaction transaction) {

        //  get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //  create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAMETRAN, transaction.getUsername());
        System.out.println("TESTTEST: transaction get username" + transaction.getUsername());
        values.put(KEY_TYPE, transaction.getType());
        values.put(KEY_TITLETRAN, transaction.getTitle());
        values.put(KEY_PICKUP, transaction.getPickUpDate());
        values.put(KEY_RETURN, transaction.getDropOffDate());
        values.put(KEY_COST, transaction.getRentalCost());
        values.put(KEY_DATE, transaction.getDate());
        values.put(KEY_TIME, transaction.getTime());

        System.out.println("TESTTEST: transaction get active" + transaction.getActive());
        values.put(KEY_ACTIVE, transaction.getActive());

        values.put(KEY_TYPENUMBER, transaction.getTypeNumber());
        values.put(KEY_PICKDAYYEAR, transaction.getPickDayYear());
        values.put(KEY_DROPDAYYEAR, transaction.getDropDayYear());
        values.put(KEY_RESERVATION, transaction.getReservation());

        // updating row
        int i = db.update(TABLE_TRANSACTIONS, //table
                values, // column/value
                KEY_NUMBER+" = ?", // selections
                new String[] { String.valueOf(transaction.getId()) }); //selection args

        // close
        db.close();

        return i;

    }



}