package com.naskogeorgiev.simpleshoppinglist.interfaces;

import com.naskogeorgiev.simpleshoppinglist.models.Product;

import java.util.List;

public interface IProductsAPICallback {
    void onGetProductsPositiveResponse(List<Product> products);
    void onGetProductsNegativeResponse(String message);
    void onDeleteProductNegativeResponse(String message);
    void onUpdateProductPositiveResponse(Product updatedProduct, int index);
    void onUpdateProductNegativeResponse();
    void onCreateProductPositiveResponse(Product product);
    void onCreateProductNegativeResponse(String message);
}
