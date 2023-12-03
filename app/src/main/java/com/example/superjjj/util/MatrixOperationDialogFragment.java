package com.example.superjjj.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.superjjj.R;

public class MatrixOperationDialogFragment extends DialogFragment {

    private int choise;
    private int condition; // 0是2，1是4，2是1，3是dual
    private boolean am;

    Dialog dialogView;
    EditText rowA;
    EditText colA;
    EditText rowB;
    EditText colB;

    int row1,col1,row2,col2;

    public static MatrixOperationDialogFragment newInstance(int choise) {
        MatrixOperationDialogFragment frag = new MatrixOperationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("choise", choise);
        frag.setArguments(args);
        return frag;
    }

    public interface MatrixDialogListener {
        void onMatrixSizeSelected(int row1, int col1, int row2, int col2,boolean am);
    }

    private MatrixDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MatrixDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MatrixDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        choise = getArguments().getInt("choise");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Choose the right layout based on the choise
        switch (choise) {
            case 0:
            case 1:
                am = true;
                builder.setView(R.layout.dialog_two_inputs);
                condition = 0;
                break;
            case 3:
            case 5:
                am = false;
                builder.setView(R.layout.dialog_two_inputs);
                condition = 0;
                break;
            case 2:
                builder.setView(R.layout.dialog_four_inputs);
                condition = 1;
                am = true;
                // Add text changed listeners to sync Matrix A cols with Matrix B rows
                break;
            case 4:
            case 6:
            case 7:
                builder.setView(R.layout.dialog_one_input);
                condition = 2;
                am = false;
                break;
            default:
                builder.setView(R.layout.dialog_33);
                condition = 3;
                am = false;
                break;
        }

        builder.setPositiveButton("Confirm", null); // We will override the onClick below
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (condition == 1) {
                    rowA = dialog.findViewById(R.id.editTextRow1);
                    colA = dialog.findViewById(R.id.editTextCol1);
                    rowB = dialog.findViewById(R.id.editTextRow2);
                    colB = dialog.findViewById(R.id.editTextCol2);

                    // Add the TextWatchers to synchronize rowB and colA
                    TextWatcher syncTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            rowB.removeTextChangedListener(this);
                            colA.removeTextChangedListener(this);

                            rowB.setText(s);
                            colA.setText(s);

                            rowB.addTextChangedListener(this);
                            colA.addTextChangedListener(this);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    };

                    rowB.addTextChangedListener(syncTextWatcher);
                    colA.addTextChangedListener(syncTextWatcher);
                }

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogView = getDialog();
                        if (condition == 0){
                            rowA = dialogView.findViewById(R.id.editTextRow);
                            colA = dialogView.findViewById(R.id.editTextCol);

                            if (rowA.getText().toString().isEmpty() || colA.getText().toString().isEmpty()){
                                ToastUtil.showShort(getActivity(), "please input all values");
                                return;
                            }
                            row1 = Integer.parseInt(rowA.getText().toString());
                            col1 = Integer.parseInt(colA.getText().toString());
                            if (checkNum(row1) || checkNum(col1)){
                                ToastUtil.showShort(getActivity(), "the num is too large");
                                return;
                            }
                            listener.onMatrixSizeSelected(row1, col1, row1, col1, am);
                            dismiss();
                        } else if (condition == 1){
                            rowA = dialogView.findViewById(R.id.editTextRow1);
                            colA = dialogView.findViewById(R.id.editTextCol1);
                            rowB = dialogView.findViewById(R.id.editTextRow2);
                            colB = dialogView.findViewById(R.id.editTextCol2);

                            if (rowA.getText().toString().isEmpty() || colA.getText().toString().isEmpty()
                                    || rowB.getText().toString().isEmpty() || colB.getText().toString().isEmpty()){
                                ToastUtil.showShort(getActivity(), "please input all values");
                                return;
                            }
                            row1 = Integer.parseInt(rowA.getText().toString());
                            col1 = Integer.parseInt(colA.getText().toString());
                            row2 = Integer.parseInt(rowB.getText().toString());
                            col2 = Integer.parseInt(colB.getText().toString());

                            if (checkNum(row1) || checkNum(col1)
                            || checkNum(row2) || checkNum(col2)){
                                ToastUtil.showShort(getActivity(), "the num is too large");
                                return;
                            }

                            listener.onMatrixSizeSelected(row1, col1, row2, col2, am);
                            dismiss();
                        } else if (condition == 2){
                            rowA = dialogView.findViewById(R.id.editTextDimension);
                            if (rowA.getText().toString().isEmpty()){
                                ToastUtil.showShort(getActivity(), "please input all values");
                                return;
                            }
                            row1 = Integer.parseInt(rowA.getText().toString());

                            if (checkNum(row1)){
                                ToastUtil.showShort(getActivity(), "the num is too large");
                                return;
                            }
                            listener.onMatrixSizeSelected(row1, row1, 0, 0,am);
                            dismiss();
                        } else {
                            listener.onMatrixSizeSelected(1, 3, 0, 0,am);
                            dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    public boolean checkNum(int num){
        return num > 10;
    }
}

