package com.example.shelflife;

import java.sql.Date;

public class Item {
    int _id;
    String _itemDateAdded;
    String _itemName;
    String _itemExpiry;
    String _itemDesc;
    String _itemType;

    public Item(String _itemName, String _itemDateAdded, String _itemExpiry, String _itemDesc, String _itemType) {
        this._itemName = _itemName;
        this._itemDateAdded = _itemDateAdded;
        this._itemExpiry = _itemExpiry;
        this._itemDesc = _itemDesc;
        this._itemType = _itemType;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_itemDateAdded() {
        return _itemDateAdded;
    }

    public void set_itemDateAdded(String _itemDateAdded) {
        this._itemDateAdded = _itemDateAdded;
    }

    public String get_itemName() {
        return _itemName;
    }

    public void set_itemName(String _itemName) {
        this._itemName = _itemName;
    }

    public String get_itemExpiry() {
        return _itemExpiry;
    }

    public void set_itemExpiry(String _itemExpiry) {
        this._itemExpiry = _itemExpiry;
    }

    public String get_itemDesc() {
        return _itemDesc;
    }

    public void set_itemDesc(String _itemDesc) {
        this._itemDesc = _itemDesc;
    }

    public String get_itemType() {
        return _itemType;
    }

    public void set_itemType(String _itemType) {
        this._itemType = _itemType;
    }
}
