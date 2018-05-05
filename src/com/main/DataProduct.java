package com.main;

public class DataProduct {
    public int _id;
    private String _name;
    private int _purchasePrice;
    private int _sellingPrice;
    private int _availability;
    private int _soldForAMonth;
    private int _oldMonth;
    private String _difference;

    public DataProduct(int _id, String _name, int _purchasePrice, int _sellingPrice,
                       int _availability, int _soldForAMonth, String  _difference, int _oldMonth) {
        this._id = _id;
        this._name = _name;
        this._purchasePrice = _purchasePrice;
        this._sellingPrice = _sellingPrice;
        this._availability = _availability;
        this._soldForAMonth = _soldForAMonth;
        this._difference = _difference;
        this._oldMonth = _oldMonth;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public int get_purchasePrice() {
        return _purchasePrice;
    }

    public int get_sellingPrice() {
        return _sellingPrice;
    }

    public int get_availability() {
        return _availability;
    }

    public int get_soldForAMonth() {
        return _soldForAMonth;
    }

    public String get_difference() {
        return _difference;
    }

    public int get_oldMonth() {
        return _oldMonth;
    }
    public DataProduct GetClone(){
        return new DataProduct(_id, _name,_purchasePrice,_sellingPrice,_availability,_soldForAMonth,_difference,_oldMonth);
    }
}
