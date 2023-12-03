package com.example.superjjj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superjjj.util.Matrix;
import com.example.superjjj.util.MatrixChooseUtil;
import com.example.superjjj.util.MatrixOperationDialogFragment;
import com.example.superjjj.util.ToastUtil;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link matrix.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link matrix#newInstance} factory method to
 * create an instance of this fragment.
 */
public class matrix extends Fragment implements MatrixOperationDialogFragment.MatrixDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private View view;
    private Spinner matrixSpinner;
    private int choise;

    DialogFragment dialogFragment;
    private boolean show = false;

    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] result;
    private ArrayList<EditText> matrixAEditTexts = new ArrayList<>();
    private ArrayList<EditText> matrixBEditTexts = new ArrayList<>();

    LinearLayout container;


    public matrix() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static matrix newInstance(String param1, String param2) {
        matrix fragment = new matrix();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matrix, container, false);

        matrixSpinner = view.findViewById(R.id.matrixChoose);
        MatrixChooseUtil.initSpinner(getActivity(), matrixSpinner);


        matrixSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOperation = (String) parentView.getItemAtPosition(position);
                choise = MatrixChooseUtil.operationMap.get(selectedOperation);
                ToastUtil.showShort(getActivity(), "operation is " + selectedOperation);
                if (show) {
                    showMatrixOperationDialog(choise);
                }
                show = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        return view;
    }

    private void showMatrixOperationDialog(int choise) {
        dialogFragment = MatrixOperationDialogFragment.newInstance(choise);
        dialogFragment.setTargetFragment(this, 0); // 将当前 Fragment 设置为目标 Fragment
        dialogFragment.show(getFragmentManager(), "MatrixOperationDialog");
    }

    @Override
    public void onMatrixSizeSelected(int row1, int col1, int row2, int col2, boolean am) {
        createGrids(row1, col1, row2, col2, am);
    }

    @SuppressLint("ResourceAsColor")
    private void createGrids(int row1, int col1, int row2, int col2, boolean am) {
        container = view.findViewById(R.id.scrollViewContainer);
        container.removeAllViews();
        matrixAEditTexts.clear();
        matrixBEditTexts.clear();

        if (row1 > 0 && col1 > 0) {
            matrixA = new double[row1][col1];
            TextView matrixA = new TextView(new ContextThemeWrapper(getContext(), R.style.matrixText), null, 0);
            matrixA.setText("Matrix A");
            matrixA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // 设置文本大小
            matrixA.setTextColor(Color.BLACK); // 设置文本颜色
            matrixA.setGravity(Gravity.CENTER); // 设置文本居中
            GridLayout gridLayout1 = createGridLayout(row1, col1, true);
            View divider = new View(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.divider_height)
            );
            int marginTop = getResources().getDimensionPixelSize(R.dimen.divider_margin_top);
            int marginBottom = getResources().getDimensionPixelSize(R.dimen.divider_margin_bottom);
            layoutParams.setMargins(0, marginTop, 0, marginBottom);
            divider.setLayoutParams(layoutParams);
            divider.setBackgroundColor(Color.BLACK);
            container.addView(matrixA);
            container.addView(gridLayout1);
            container.addView(divider);

        }

        if (row2 > 0 && col2 > 0 && am) {
            matrixB = new double[row2][col2];
            TextView matrixB = new TextView(new ContextThemeWrapper(getContext(), R.style.matrixText), null, 0);
            matrixB.setText("Matrix B");
            matrixB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // 设置文本大小
            matrixB.setTextColor(Color.BLACK); // 设置文本颜色
            matrixB.setGravity(Gravity.CENTER); // 设置文本居中
            GridLayout gridLayout2 = createGridLayout(row2, col2, false);
            container.addView(matrixB);
            container.addView(gridLayout2);

        }
        Button calculate = new Button(getContext());
        calculate.setText("Calculate");
        calculate.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.matrixbutton)); // 设置带圆角的背景
        calculate.setTextColor(Color.WHITE); // 设置文本颜色
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
        container.addView(calculate);

    }

    private GridLayout createGridLayout(int rows, int cols, boolean isA) {
        GridLayout gridLayout = new GridLayout(new ContextThemeWrapper(getContext(), R.style.matrixGrid), null, 0);
        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(cols);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        int margin = 10;
        for (int i = 0; i < rows * cols; i++) {
            EditText editText = new EditText(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(margin, margin, margin, margin);
            params.setGravity(Gravity.FILL_HORIZONTAL);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
            editText.setLayoutParams(params);

            editText.setGravity(Gravity.CENTER);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER |
                    InputType.TYPE_NUMBER_FLAG_DECIMAL |
                    InputType.TYPE_NUMBER_FLAG_SIGNED);
            if (isA) {
                matrixAEditTexts.add(editText);
            } else {
                matrixBEditTexts.add(editText);
            }
            gridLayout.addView(editText);
        }

        return gridLayout;
    }

    private void populateMatrix(double[][] matrix, ArrayList<EditText> editTexts, int rows, int cols) {
        for (int i = 0; i < rows * cols; i++) {
            int row = i / cols;
            int col = i % cols;
            String valueStr = editTexts.get(i).getText().toString();
            double value = valueStr.isEmpty() ? 0.0 : Double.parseDouble(valueStr);
            matrix[row][col] = value;
        }
    }

    private void calculate() {
        if (matrixAEditTexts.size() > 0) {
            populateMatrix(matrixA, matrixAEditTexts, matrixA.length, matrixA[0].length);
        }
        if (matrixBEditTexts.size() > 0) {
            populateMatrix(matrixB, matrixBEditTexts, matrixB.length, matrixB[0].length);
        }
        if (choise == 0) {
            result = Matrix.add(matrixA, matrixB);
        } else if (choise == 1) {
            result = Matrix.minus(matrixA, matrixB);
        } else if (choise == 2) {
            result = Matrix.multiply(matrixA, matrixB);
        } else if (choise == 3) {
            result = Matrix.getTranspose(matrixA);
        } else if (choise == 4) {
            result = Matrix.inverse(matrixA);
        } else if (choise == 5) {
            int rank = Matrix.rankOfMatrix(matrixA);
            result = new double[1][1];
            result[0][0] = rank;
        } else if (choise == 6) {
            double det = Matrix.determinant(matrixA);
            result = new double[1][1];
            result[0][0] = det;
        } else if (choise == 7) {
            result = Matrix.adjugate(matrixA);
        } else {
            double[] m = {matrixA[0][0], matrixA[0][1], matrixA[0][2]};
            result = Matrix.dual(m);
        }
        showResult(result);
    }

    private void showResult(double[][] result) {
        container.removeAllViews();
        TextView matrixA = new TextView(new ContextThemeWrapper(getContext(), R.style.matrixText), null, 0);
        matrixA.setText("Result");
        matrixA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // 设置文本大小
        matrixA.setTextColor(Color.BLACK); // 设置文本颜色
        matrixA.setGravity(Gravity.CENTER); // 设置文本居中
        GridLayout gridLayout1 = createResult(result.length, result[0].length, result);

        container.addView(matrixA);
        container.addView(gridLayout1);
    }

    private GridLayout createResult(int rows, int cols, double[][] result) {
        GridLayout gridLayout = new GridLayout(new ContextThemeWrapper(getContext(), R.style.matrixGrid), null, 0);
        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(cols);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        int margin = 10;
        int size = 0;

        if (rows <= 7){
            size = 20;
        } else if (rows == 8){
            size = 16;
        } else if (rows == 9){
            size = 14;
        } else {
            size = 12;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextView textView = new TextView(getContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                params.width = 0; // 修改宽度为自适应内容
                params.height = GridLayout.LayoutParams.WRAP_CONTENT; // 高度也设置为自适应内容
                params.setMargins(margin, margin, margin, margin);
                params.setGravity(Gravity.FILL_HORIZONTAL);
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
                textView.setLayoutParams(params);

                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(size);
                textView.setTextColor(Color.BLACK);
                textView.setText(formatNumber(result[i][j])); // 使用自定义的格式化方法

                gridLayout.addView(textView);
            }
        }


        return gridLayout;
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.format(Locale.getDefault(), "%d", (long) number);
        } else {
            return String.format(Locale.getDefault(), "%.2f", number).replaceAll("\\.00$", "");
        }
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
