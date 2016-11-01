package com.naskogeorgiev.simpleshoppinglist.interfaces;

import com.naskogeorgiev.simpleshoppinglist.models.ShoppingList;

import java.util.List;

public interface IShoppingListsAPICallback {
    void onGetShoppingListsPositiveResponse(List<ShoppingList> shoppingLists);
    void onGetShoppingListsNegativeResponse();
    void onEditShoppingListPositiveResponse(int index);
    void onEditShoppingListNegativeResponse();
    void onDeleteShoppingListNegativeResponse();
    void onCreateShoppingListPositiveResponse(ShoppingList shoppingList);
    void onCreateShoppingListNegativeResponse();
}
