package com.naskogeorgiev.simpleshoppinglist.services;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.naskogeorgiev.simpleshoppinglist.interfaces.IProductsAPICallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IShoppingListsAPICallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.ShoppingListAPI;
import com.naskogeorgiev.simpleshoppinglist.models.Product;
import com.naskogeorgiev.simpleshoppinglist.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private final ShoppingListAPI api = ShoppingListAPI.retrofit.create(ShoppingListAPI.class);
    private List<ShoppingList> mShoppingLists = new ArrayList<>();
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public APIService getService() {
            // Return this instance of LocalService so clients can call public methods
            return APIService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // methods for clients

    /**
     * Methods for shopping lists
     */
    public void getShoppingLists(final IShoppingListsAPICallback callback){
        mShoppingLists.clear();

        Call<List<ShoppingList>> call = api.getShoppingLists();
        call.enqueue(new Callback<List<ShoppingList>>() {
            @Override
            public void onResponse(Call<List<ShoppingList>> call, Response<List<ShoppingList>> response) {
                mShoppingLists = response.body();
                callback.onGetShoppingListsPositiveResponse(mShoppingLists);
            }

            @Override
            public void onFailure(Call<List<ShoppingList>> call, Throwable t) {
                callback.onGetShoppingListsNegativeResponse();
            }
        });
    }

    public void editShoppingList(final IShoppingListsAPICallback callback, int apiItemId, final int datasetIndex, ShoppingList newList){
        final Call<ShoppingList> call = api.updateShoppingList(apiItemId, newList);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                callback.onEditShoppingListPositiveResponse(datasetIndex);
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                callback.onEditShoppingListNegativeResponse();
            }
        });
    }

    public void deleteShoppingList(final IShoppingListsAPICallback callback, int index){
        Call<ShoppingList> call = api.deleteShoppingList(index);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                callback.onDeleteShoppingListNegativeResponse();
            }
        });
    }

    public void createShoppingList(final IShoppingListsAPICallback callback, ShoppingList shoppingList) {
        Call<ShoppingList> call = api.createShoppingList(shoppingList);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                callback.onCreateShoppingListPositiveResponse(response.body());
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                callback.onCreateShoppingListNegativeResponse();
            }
        });
    }

    /**
     * Methods for products
     */
    public void getProducts(final IProductsAPICallback callback, int listId){
        Call<List<Product>> call = api.getProducts(listId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                callback.onGetProductsPositiveResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onGetProductsNegativeResponse(t.getMessage());
            }
        });
    }

    public void deleteProduct(final IProductsAPICallback callback, int index){
        Call<Product> call = api.deleteProduct(index);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onDeleteProductNegativeResponse(t.getMessage());
            }
        });
    }

    public void updateProduct(final IProductsAPICallback callback, Product product, int apiItemId, final int datasetIndex){
        Call<Product> call = api.updateProduct(apiItemId, product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                callback.onUpdateProductPositiveResponse(response.body(), datasetIndex);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onUpdateProductNegativeResponse();
            }
        });
    }

    public void createProduct(final IProductsAPICallback callback, Product product){
        Call<Product> call = api.createProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                callback.onCreateProductPositiveResponse(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onCreateProductNegativeResponse(t.getMessage());
            }
        });
    }
}
