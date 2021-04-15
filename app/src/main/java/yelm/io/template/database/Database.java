package yelm.io.template.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import yelm.io.template.database.address.UserAddressesDao;
import yelm.io.template.database.address.UserAddress;
import yelm.io.template.database.cart.BasketCart;
import yelm.io.template.database.cart.BasketCartDao;
import yelm.io.template.database.converters.ModifiersConverter;


@androidx.room.Database(entities =
        {UserAddress.class, BasketCart.class},
        version = 1,
        exportSchema = false)
@TypeConverters({ModifiersConverter.class})

public abstract class Database extends RoomDatabase {

    public abstract BasketCartDao basketCartDao();

    public abstract UserAddressesDao userAddressesDao();

    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, Database.class, "DataBase").
                    fallbackToDestructiveMigration().
                    allowMainThreadQueries().
                    //.addMigrations(MIGRATION_1_2)
                    build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NotNull SupportSQLiteDatabase database) {
            // Поскольку мы не изменяли таблицу, здесь больше ничего не нужно делать.
        }
    };
}