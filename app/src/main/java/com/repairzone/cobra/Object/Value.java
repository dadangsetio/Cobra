package com.repairzone.cobra.Object;

import java.util.List;

public class Value {
    private String value;
    private String message;
    private List<Stock> result;
    private List<Item> itemList;


    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Stock> getStockList(){
        return result;
    }

    public  List<Item> getItemList(){
        return itemList;
    }
}
