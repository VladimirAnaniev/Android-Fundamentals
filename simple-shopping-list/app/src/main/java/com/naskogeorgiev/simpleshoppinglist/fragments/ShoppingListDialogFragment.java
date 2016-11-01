package com.naskogeorgiev.simpleshoppinglist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naskogeorgiev.simpleshoppinglist.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShoppingListDialogFragment extends DialogFragment implements DialogInterface.OnShowListener {

    DialogListener mListener;
    boolean mEditing;

    @BindView(R.id.et_shopping_list_title)
    EditText mListTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mEditing = getArguments().getBoolean("isEditing");

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_list, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mEditing ? R.string.edit_shopping_list : R.string.new_shopping_list)
                .setView(view)
                .setPositiveButton(mEditing ? getString(R.string.edit) : getString(R.string.create), null)
                .setNegativeButton(getString(R.string.cancel), null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        ButterKnife.bind(this, view);

        return dialog;
    }


    @Override
    public void onShow(DialogInterface dialog) {
        Button positive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        Button negative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(ShoppingListDialogFragment.this);
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(ShoppingListDialogFragment.this);
            }
        });

        if (mEditing) {
            mListTitle.setText(getArguments().getString("Title"));
        }
    }


    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

}
