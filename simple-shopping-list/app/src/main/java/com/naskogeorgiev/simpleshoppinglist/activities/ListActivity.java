package com.naskogeorgiev.simpleshoppinglist.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.naskogeorgiev.simpleshoppinglist.fragments.ProductDialogFragment;
import com.naskogeorgiev.simpleshoppinglist.ShoppingApplication;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IListAdapterCallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IProductsAPICallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IRecycleViewSelectedElement;
import com.naskogeorgiev.simpleshoppinglist.R;
import com.naskogeorgiev.simpleshoppinglist.adapters.ProductAdapter;
import com.naskogeorgiev.simpleshoppinglist.models.Product;
import com.naskogeorgiev.simpleshoppinglist.services.APIService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity implements IRecycleViewSelectedElement,
        ProductDialogFragment.DialogListener, IListAdapterCallback, IProductsAPICallback {

    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Product> products;
    private APIService mAPIService;
    private Context ctx;

    private int listId;

    @BindView(R.id.toolbar_list)
    Toolbar toolbar;

    @BindView(R.id.recycler_product)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.products_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.lists_progress_bar)
    ProgressBar mProgressBar;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        ctx = this;

        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(TAG, "Unable to set up navigation!");
            Log.e(TAG, e.getMessage());
        }

        Intent i = getIntent();
        if (i.hasExtra("Index")) {
            listId = i.getIntExtra("Index", -1);
        }

        ShoppingApplication application = (ShoppingApplication) getApplication();
        mAPIService = application.getAPIService();

        mAPIService.getProducts(this,listId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    @OnClick(R.id.fab)
    void onFloatingButtonClicked() {
        ProductDialogFragment dialog = new ProductDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEditing", false);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "CreateProductDialog");
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            final ProductAdapter.ProductViewHolder vh = (ProductAdapter.ProductViewHolder) viewHolder;
            final int oldPosition = vh.getItemPosition();
            final Product oldProduct = products.get(oldPosition);
            products.remove(vh.getItemPosition());
            mAdapter.notifyItemRemoved(vh.getItemPosition());

            final Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, R.string.product_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            products.add(oldPosition, oldProduct);
                            mAdapter.notifyItemInserted(oldPosition);
                            mRecyclerView.scrollToPosition(oldPosition);
                        }
                    })
                    .setCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            // If the user clicked UNDO just leave
                            if (event == DISMISS_EVENT_ACTION)
                                return;

                            // Otherwise remove the product from the server
                            IProductsAPICallback callback = (IProductsAPICallback) ctx;
                            mAPIService.deleteProduct(callback, oldProduct.getId());
                        }
                    });
            snackbar.show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                openShareDialog(listId);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openShareDialog(int listId) {
        Snackbar.make(coordinatorLayout, "Sharing list with ID " + listId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(final int position) {
        if (products.size() >= position) {
            Product product = products.get(position);
            product.setFound(!product.isFound());

            mAPIService.updateProduct(this,product,product.getId(),position);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText etTitle = (EditText) dialog.getDialog().findViewById(R.id.et_product_title);
        EditText etQuantity = (EditText) dialog.getDialog().findViewById(R.id.et_product_quantity);
        String title = etTitle.getText().toString();
        String quantity = etQuantity.getText().toString();
        if (title.trim().length() < 1) {
            etTitle.setError(getString(R.string.product_name_required));
        } else if (quantity.trim().length() < 1) {
            etQuantity.setError(getString(R.string.quantity_required));
        } else {
            final Product product;
            if (dialog.getArguments().getBoolean("isEditing")) {
                final int listPosition = Integer.parseInt(dialog.getTag());
                product = products.get(listPosition);

                if(product.getTitle().equals(title) && product.getQuantity() == Float.parseFloat(quantity)){
                    Snackbar.make(coordinatorLayout, R.string.values_not_changed, Snackbar.LENGTH_SHORT).show();
                }
                else{
                    product.setTitle(title);
                    product.setQuantity(Float.parseFloat(quantity));
                    mAPIService.updateProduct(this, product, product.getId(), listPosition);
                }
            }
            else {
                product = new Product(title, Float.parseFloat(quantity), false, listId);
                mAPIService.createProduct(this, product);
            }
            dialog.dismiss();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onLongClicked(int index) {
        ProductDialogFragment dialog = new ProductDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEditing", true);
        bundle.putString("Title", products.get(index).getTitle());
        bundle.putString("Quantity", String.valueOf(products.get(index).getQuantity()));
        dialog.setArguments(bundle);
        // Send the position to the Alert Dialog
        dialog.show(getFragmentManager(), String.valueOf(index));
    }
    @Override
    public void onGetProductsPositiveResponse(List<Product> productsList) {
        products = productsList;
        mLayoutManager = new LinearLayoutManager(ListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mProgressBar.setVisibility(View.GONE);

        mAdapter = new ProductAdapter(products, ListActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onGetProductsNegativeResponse(String message) {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.get_products_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAPIService.getProducts((IProductsAPICallback)ctx, listId);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                });
        snackbar.show();
    }

    @Override
    public void onDeleteProductNegativeResponse(String message) {
        Snackbar.make(coordinatorLayout, R.string.delete_product_failed, Snackbar.LENGTH_SHORT).show();
        Log.e(TAG, message);
    }

    @Override
    public void onUpdateProductPositiveResponse(Product updatedProduct, int index) {
        if (updatedProduct != null)
            mAdapter.notifyItemChanged(index);
        else {
            Snackbar.make(coordinatorLayout, R.string.update_product_failed, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateProductNegativeResponse() {
        Snackbar.make(coordinatorLayout, R.string.update_product_failed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateProductPositiveResponse(Product product) {
        products.add(0, product);
        mAdapter.notifyItemInserted(0);
        Snackbar.make(coordinatorLayout, R.string.product_created, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateProductNegativeResponse(String message) {
        Snackbar.make(coordinatorLayout, R.string.create_product_failed, Snackbar.LENGTH_SHORT).show();
        Log.e(TAG, message);
    }
}

