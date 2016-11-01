package com.naskogeorgiev.simpleshoppinglist.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.naskogeorgiev.simpleshoppinglist.R;
import com.naskogeorgiev.simpleshoppinglist.ShoppingApplication;
import com.naskogeorgiev.simpleshoppinglist.adapters.ShoppingListAdapter;
import com.naskogeorgiev.simpleshoppinglist.fragments.ShoppingListDialogFragment;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IShoppingListsAPICallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IListAdapterCallback;
import com.naskogeorgiev.simpleshoppinglist.interfaces.IRecycleViewSelectedElement;
import com.naskogeorgiev.simpleshoppinglist.models.ShoppingList;
import com.naskogeorgiev.simpleshoppinglist.services.APIService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRecycleViewSelectedElement,
        IListAdapterCallback, IShoppingListsAPICallback, ShoppingListDialogFragment.DialogListener{

    private List<ShoppingList> shoppingLists;
    private ShoppingListAdapter mAdapter;
    private APIService mAPIService;
    private Context ctx;
    private RecyclerView mRecyclerView;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.lists_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.lists_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ctx = this;

        setSupportActionBar(toolbar);

        ShoppingApplication application = (ShoppingApplication) getApplication();
        mAPIService = application.getAPIService();

        mAPIService.getShoppingLists(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initializeRecyclerView(List<ShoppingList> data) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_shopping_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ShoppingListAdapter(data, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView

            final ShoppingListAdapter.ShoppingListViewHolder vh = (ShoppingListAdapter.ShoppingListViewHolder) viewHolder;
            final int oldPosition = vh.getItemPosition();
            final ShoppingList oldShoppingList = shoppingLists.get(oldPosition);
            shoppingLists.remove(vh.getItemPosition());
            mAdapter.notifyItemRemoved(vh.getItemPosition());

            final Snackbar snackbar = Snackbar
                    .make(mCoordinatorLayout, R.string.shopping_list_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shoppingLists.add(oldPosition, oldShoppingList);
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
                            IShoppingListsAPICallback callback = (IShoppingListsAPICallback) ctx;
                            mAPIService.deleteShoppingList(callback, oldShoppingList.getId());
                        }
                    });
            snackbar.show();
        }
    };

    @Override
    public void onItemSelected(int position) {
        int listIndex = shoppingLists.get(position).getId();
        Intent i = new Intent(MainActivity.this, ListActivity.class);
        i.putExtra("Index", listIndex);
        startActivity(i);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText etTitle = (EditText) dialog.getDialog().findViewById(R.id.et_shopping_list_title);
        String title = etTitle.getText().toString();
        if (title.trim().length() < 1) {
            etTitle.setError(getString(R.string.title_required));
        }
        else {
            ShoppingList list;
            if (dialog.getArguments().getBoolean("isEditing")) {
                final int listPosition = Integer.parseInt(dialog.getTag());
                list = shoppingLists.get(listPosition);
                if(!list.getTitle().equals(title)){
                    list.setTitle(title);
                    mAPIService.editShoppingList(this, list.getId(), listPosition, list);
                }
                else {
                    Snackbar.make(mCoordinatorLayout, R.string.list_not_edited, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                list = new ShoppingList(title, false);
                mAPIService.createShoppingList(this, list);
            }
            dialog.dismiss();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                ShoppingListDialogFragment dialog = new ShoppingListDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isEditing", false);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "CreateShoppingListDialog");
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLongClicked(int index) {
        ShoppingListDialogFragment dialog = new ShoppingListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEditing", true);
        bundle.putString("Title", shoppingLists.get(index).getTitle());
        dialog.setArguments(bundle);
        // Send the position to the Alert Dialog
        dialog.show(getFragmentManager(), String.valueOf(index));
    }

    @Override
    public void onGetShoppingListsPositiveResponse(List<ShoppingList> shoppingLists) {
        mProgressBar.setVisibility(View.GONE);
        this.shoppingLists = shoppingLists;
        initializeRecyclerView(shoppingLists);

        if(shoppingLists.size()==0){
            Snackbar.make(mCoordinatorLayout, R.string.no_lists, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetShoppingListsNegativeResponse() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, R.string.get_lists_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAPIService.getShoppingLists((IShoppingListsAPICallback)ctx);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                });
        snackbar.show();

    }

    @Override
    public void onEditShoppingListPositiveResponse(int index) {

        mAdapter.notifyItemChanged(index);
        Snackbar.make(mCoordinatorLayout, R.string.edit_successful, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onEditShoppingListNegativeResponse() {
        Snackbar.make(mCoordinatorLayout, R.string.edit_unsuccessful, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteShoppingListNegativeResponse() {
        Snackbar.make(mCoordinatorLayout, R.string.deletion_problem, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateShoppingListPositiveResponse(ShoppingList shoppingList) {
        Snackbar.make(mCoordinatorLayout, R.string.shopping_list_created, Snackbar.LENGTH_SHORT).show();
        shoppingLists.add(0, shoppingList);
        mAdapter.notifyItemInserted(0);
    }

    @Override
    public void onCreateShoppingListNegativeResponse() {
        Snackbar.make(mCoordinatorLayout, R.string.create_list_fail, Snackbar.LENGTH_SHORT).show();
    }
}
