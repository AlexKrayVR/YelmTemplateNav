package yelm.io.template.database;


import yelm.io.template.database.address.UserAddressesDao;
import yelm.io.template.database.cart.BasketCartDao;

public class Common {
    public static Database database;

    public static BasketCartDao basketCartDao;

    public static UserAddressesDao userAddressesDao;
}