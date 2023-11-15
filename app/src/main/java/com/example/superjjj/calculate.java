package com.example.superjjj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.superjjj.util.PrecisionUtil;
import com.example.superjjj.util.SwitchButton;
import com.example.superjjj.util.ToastUtil;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link calculate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link calculate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class calculate extends Fragment {

    private View view;

    List<String> historyList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView historyListView;
    private ArrayAdapter historyAdapter;

    private boolean isNormalLayout = true;
    private SwitchButton switchButton;
    private View normalKeyboardLayout; // 简单布局
    private View superKeyboardLayout; // 复杂布局
    private ScrollView scrollView;

    private Spinner precisionSpinner;
    private int precision;

    private String expression = "";
    private TextView input;
    private TextView output;

    Button button0, button1, button2, button3, button4, button5, button6, button7;
    Button button8, button9, button0s, button1s, button2s, button3s, button4s, button5s;
    Button button6s, button7s, button8s, button9s, dot, dots, equal, equals;
    Button add, adds, min, mins, mul, muls, div, divs;
    Button symbol, symbols, back, backs, clear, clears, percent, percents;
    Button reciprocal, factorial, tan, cos, sin;
    Button lg, ln, sqrt, left_br, right_br;

    Button[] buttons = new Button[9];
    Button[] buttonss = new Button[9];
    Button[] superBtn = new Button[6];


    private boolean equal_flag = false; //设置flag值判断是否需要清空expression进行新的运算


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public calculate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calculate.
     */
    // TODO: Rename and change types and number of parameters
    public static calculate newInstance(String param1, String param2) {
        calculate fragment = new calculate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 把碎片关联到activity上
     *
     * @param context
     */

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    /**
     * 创建页面
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * 绑定fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calculate, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        input = view.findViewById(R.id.input_view);
        output = view.findViewById(R.id.output_view);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, input.getBottom());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 滚动到底部
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, input.getBottom());
                    }
                });
            }
        });

        normalKeyboardLayout = view.findViewById(R.id.normalKeyboardLayout);
        superKeyboardLayout = view.findViewById(R.id.superKeyboardLayout);

        switchButton = view.findViewById(R.id.change_btn);
        // 设置开关状态改变的监听器
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchView();
            }

        });

        precisionSpinner = view.findViewById(R.id.precision);

        // 初始化下拉列表
        PrecisionUtil.initPrecisionSpinner(getActivity(), precisionSpinner);
        precisionSpinner.setSelection(2);
        precisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 当选择项改变时，更新选中的精度值
                precision = Integer.parseInt((String) parentView.getItemAtPosition(position));
                ToastUtil.showShort(getActivity(), "现在的精度是: " + precision);
                // TODO: 处理选择的精度值
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 在用户未选择任何项时的处理
                precision = 3;
            }
        });

        button0 = view.findViewById(R.id.number0);
        button1 = view.findViewById(R.id.number1);
        button2 = view.findViewById(R.id.number2);
        button3 = view.findViewById(R.id.number3);
        button4 = view.findViewById(R.id.number4);
        button5 = view.findViewById(R.id.number5);
        button6 = view.findViewById(R.id.number6);
        button7 = view.findViewById(R.id.number7);
        button8 = view.findViewById(R.id.number8);
        button9 = view.findViewById(R.id.number9);
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;

        button0s = view.findViewById(R.id.number0s);
        button1s = view.findViewById(R.id.number1s);
        button2s = view.findViewById(R.id.number2s);
        button3s = view.findViewById(R.id.number3s);
        button4s = view.findViewById(R.id.number4s);
        button5s = view.findViewById(R.id.number5s);
        button6s = view.findViewById(R.id.number6s);
        button7s = view.findViewById(R.id.number7s);
        button8s = view.findViewById(R.id.number8s);
        button9s = view.findViewById(R.id.number9s);
        buttonss[0] = button1s;
        buttonss[1] = button2s;
        buttonss[2] = button3s;
        buttonss[3] = button4s;
        buttonss[4] = button5s;
        buttonss[5] = button6s;
        buttonss[6] = button7s;
        buttonss[7] = button8s;
        buttonss[8] = button9s;

        dot = view.findViewById(R.id.dot);
        dots = view.findViewById(R.id.dots);
        equal = view.findViewById(R.id.equal);
        equals = view.findViewById(R.id.equals);
        add = view.findViewById(R.id.add);
        adds = view.findViewById(R.id.adds);
        min = view.findViewById(R.id.min);
        mins = view.findViewById(R.id.mins);
        mul = view.findViewById(R.id.mul);
        muls = view.findViewById(R.id.muls);
        div = view.findViewById(R.id.div);
        divs = view.findViewById(R.id.divs);
        symbol = view.findViewById(R.id.symbol);
        symbols = view.findViewById(R.id.symbols);
        back = view.findViewById(R.id.back);
        backs = view.findViewById(R.id.backs);
        clear = view.findViewById(R.id.clear);
        clears = view.findViewById(R.id.clears);
        percent = view.findViewById(R.id.percent);
        percents = view.findViewById(R.id.percents);
        reciprocal = view.findViewById(R.id.reciprocal);
        factorial = view.findViewById(R.id.factorial);
        tan = view.findViewById(R.id.tan);
        cos = view.findViewById(R.id.cos);
        sin = view.findViewById(R.id.sin);
        lg = view.findViewById(R.id.lg);
        ln = view.findViewById(R.id.ln);
        sqrt = view.findViewById(R.id.sqrt);
        left_br = view.findViewById(R.id.left_bracket);
        right_br = view.findViewById(R.id.right_bracket);

        superBtn[0] = sqrt;
        superBtn[1] = ln;
        superBtn[2] = lg;
        superBtn[3] = sin;
        superBtn[4] = cos;
        superBtn[5] = tan;



        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 这里你可以初始化视图和设置监听器
        // 例如：
        drawerLayout = view.findViewById(R.id.drawer_layout);
        historyListView = view.findViewById(R.id.historyListView);
        historyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(historyAdapter);

        // 其他的初始化代码...

        // 设置手势监听
        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // 双击事件的处理
                drawerLayout.openDrawer(GravityCompat.START); // 打开Drawer
                return true;
            }
        });

        // 设置触摸监听
        drawerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 处理触摸事件
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 当手指抬起时模拟点击
                    v.performClick();
                }
                return gestureDetector.onTouchEvent(event);
            }

        });

        // 设置清除历史记录的按钮监听器
        Button clearHistoryButton = view.findViewById(R.id.clearHistoryButton);
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

        initInput();
        initNumBtns();
        initBaseOpers();
        initBaseCalculatorFunction();
        initSuperFunction();
    }



    private void initInput() {
        input.setMovementMethod(ScrollingMovementMethod.getInstance()); //内容自动滚动到最新的一行
        input.setTextIsSelectable(true);
    }

    private void initNumBtns() {
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equal_flag) { // 如果成功计算过一次的话，expression清空
                    expression = "";
                    equal_flag = false;
                }
                if (expression.length() == 0) {
                    expression += 0;
                } else if (expression.length() == 1) {
                    if (expression.charAt(0) == '0') {
                        expression += "";
                        ToastUtil.showShort(getActivity(), "不可输入0");
                    } else if (isNumber(expression.charAt(0)) && expression.charAt(0) != '0') {
                        expression += "0";
                    }
                } else if (!isNumber(expression.charAt(expression.length() - 2)) &&
                        expression.charAt(expression.length() - 1) == '0') {
                    expression += "";
                } else {
                    expression += "0";
                }
                input.setText(expression);
            }
        });
        button0s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equal_flag) {
                    expression = "";
                    equal_flag = false;
                }
                if (expression.length() == 0) {
                    expression += 0;
                } else if (expression.length() == 1) {
                    if (expression.charAt(0) == '0') {
                        expression += "";
                        ToastUtil.showShort(getActivity(), "不可输入0");
                    } else if (isNumber(expression.charAt(0)) && expression.charAt(0) != '0') {
                        expression += "0";
                    }
                } else if (!isNumber(expression.charAt(expression.length() - 2)) &&
                        expression.charAt(expression.length() - 1) == '0') {
                    expression += "";
                } else {
                    expression += "0";
                }
                input.setText(expression);
            }
        });
        for (final Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (equal_flag) {
                        expression = "";
                        equal_flag = false;
                    }

                    if (expression.length() == 0) {
                        expression += button.getText().toString();
                    } else if (expression.matches("^.*[+\\-×÷]+0$")) {
                        expression = expression.substring(0, expression.length() - 1) + button.getText().toString();
                    } else {
                        //math的最后一个字符是：1-9, oper, (, .
                        char ch = expression.charAt(expression.length() - 1);
                        if (isNumber(ch) || isOpe(ch) || ch == '(' || ch == '.')
                            expression += button.getText().toString();
                    }
                    input.setText(expression);
                }
            });
        }
        for (final Button button : buttonss) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (equal_flag) {
                        expression = "";
                        equal_flag = false;
                    }

                    if (expression.length() == 0) {
                        expression += button.getText().toString();
                    } else if (expression.matches("^.*[+\\-×÷]+0$")) {
                        expression = expression.substring(0, expression.length() - 1) + button.getText().toString();
                    } else {
                        //math的最后一个字符是：1-9, oper, (, .
                        char ch = expression.charAt(expression.length() - 1);
                        if (isNumber(ch) || isOpe(ch) || ch == '(' || ch == '.')
                            expression += button.getText().toString();
                    }
                    input.setText(expression);
                }
            });
        }
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.expression为空，+0.
                    expression += "0.";
                } else if (isOpe(expression.charAt(expression.length() - 1))
                        || expression.charAt(expression.length() - 1) == '(') {  //2.expression的最后一个字符为oper，+0.
                    expression += "0.";
                } else if (isNumber(expression.charAt(expression.length() - 1))) {   //3.expression的最后一个字符为num，+.
                    expression += ".";
                } else {                                                    //4.除此之外，不加
                    expression += "";
                    ToastUtil.showShort(getActivity(), "不可连续输入小数点0");

                }
                input.setText(expression);
            }
        });
        dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.expression为空，+0.
                    expression += "0.";
                } else if (isOpe(expression.charAt(expression.length() - 1))
                        || expression.charAt(expression.length() - 1) == '(') {  //2.expression的最后一个字符为oper，+0.
                    expression += "0.";
                } else if (isNumber(expression.charAt(expression.length() - 1))) {   //3.expression的最后一个字符为num，+.
                    expression += ".";
                } else {                                                    //4.除此之外，不加
                    expression += "";
                    ToastUtil.showShort(getActivity(), "不可连续输入小数点0");

                }
                input.setText(expression);
            }
        });
    }

    private void initBaseOpers() {
        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                handleOperatorButtonClick(button.getText().toString());
            }
        };

        div.setOnClickListener(operatorClickListener);
        divs.setOnClickListener(operatorClickListener);
        mul.setOnClickListener(operatorClickListener);
        muls.setOnClickListener(operatorClickListener);
        min.setOnClickListener(operatorClickListener);
        mins.setOnClickListener(operatorClickListener);
        add.setOnClickListener(operatorClickListener);
        adds.setOnClickListener(operatorClickListener);
    }

    private void handleOperatorButtonClick(String operator) {
        if (equal_flag) {
            equal_flag = false;
        }

        int length = expression.length();

        if (length == 0) {
            Toast.makeText(getActivity(), "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
        } else if (expression.matches("^.*[+\\-×÷.]$")) {
            expression = expression.substring(0, length - 1) + operator;
        } else {
            expression += operator;
        }

        input.setText(expression);
    }


    private void initBaseCalculatorFunction() {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                input.setText("0");
                output.setText(null);
            }
        });
        clears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                input.setText("0");
                output.setText(null);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(getActivity(), "现在没有内容");
                }
            }
        });
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() != 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(getActivity(), "现在没有内容");
                }
            }
        });
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(getActivity(), "不能这样加百分号");
                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += percent.getText().toString();
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(getActivity(), "不能这样加百分号");

                }
            }
        });
        percents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(getActivity(), "不能这样加百分号");

                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += percents.getText().toString();
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(getActivity(), "不能这样加百分号");


                }
            }
        });
        symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(getActivity(), "不能这样改变正负");
                    return;
                }

                int length = expression.length();
                int i = length - 1;

                // 寻找最后一个数字或点的位置
                while (i >= 0 && (isNumber(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    i--;
                }

                // 如果是正数，添加括号和负号
                if (i < length - 1 && expression.charAt(i + 1) != '(') {
                    String before = expression.substring(0, i + 1);
                    String after = expression.substring(i + 1);
                    expression = before + "(-" + after + ")";
                }
                // 如果是已经添加了括号和负号的数字，去掉它们
                else if (expression.matches("^.*\\(-[0123456789.]+\\)$")) {
                    String before = expression.substring(0, expression.lastIndexOf("(-"));
                    // 表示记录（-之前的所有数据
                    String after = expression.substring(expression.lastIndexOf("(-") + 2,
                            expression.length() - 1);
                    // 表示记录（-后的去掉右括号的所有数值
                    expression = before + after;
                    input.setText(expression);
                }
                // 如果没有匹配的情况，显示错误消息
                else {
                    ToastUtil.showShort(getActivity(), "不能这样改变正负");
                    return;
                }

                input.setText(expression);
            }
        });
        symbols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(getActivity(), "不能这样改变正负");
                    return;
                }

                int length = expression.length();
                int i = length - 1;

                // 寻找最后一个数字或点的位置
                while (i >= 0 && (isNumber(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    i--;
                }

                // 如果是正数，添加括号和负号
                if (i < length - 1 && expression.charAt(i + 1) != '(') {
                    String before = expression.substring(0, i + 1);
                    String after = expression.substring(i + 1);
                    expression = before + "(-" + after + ")";
                }
                // 如果是已经添加了括号和负号的数字，去掉它们
                else if (expression.matches("^.*\\(-[0123456789.]+\\)$")) {
                    String before = expression.substring(0, expression.lastIndexOf("(-"));
                    // 表示记录（-之前的所有数据
                    String after = expression.substring(expression.lastIndexOf("(-") + 2,
                            expression.length() - 1);
                    // 表示记录（-后的去掉右括号的所有数值
                    expression = before + after;
                    input.setText(expression);
                }
                // 如果没有匹配的情况，显示错误消息
                else {
                    ToastUtil.showShort(getActivity(), "不能这样改变正负");
                    return;
                }

                input.setText(expression);
            }
        });
    }

    private void initSuperFunction() {
        for (final Button button : superBtn) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (equal_flag) {
                        equal_flag = false;
                    }
                    if (expression.length() == 0) {
                        expression += button.getText().toString() + "(";
                    } else {
                        char c = expression.charAt(expression.length() - 1);
                        if (isOpe(c) || c == '(') {
                            expression += button.getText().toString() + "(";
                        } else if (isNumber(c)) {
                            expression += "×" + button.getText().toString() + "(";
                        } else {
                            ToastUtil.showShort(getActivity(), "不允许这样添加");

                        }
                    }
                    input.setText(expression);
                }
            });
        }
        left_br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (!expression.isEmpty() && isNumber(expression.charAt(expression.length() - 1))) {
                    expression += "×" + "(";
                } else {
                    expression += "(";
                }
                input.setText(expression);
            }
        });
        right_br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNumber(expression.charAt(expression.length() - 1))) {
                    ToastUtil.showShort(getActivity(), "必须在数字的后面");

                } else {
                    expression += ")";
                }
                input.setText(expression);
            }
        });
        reciprocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0 || !isNumber(expression.charAt(expression.length() - 1))) {
                    ToastUtil.showShort(getActivity(), "必须在数字的后面");

                } else {
                    expression += "^-1";
                    input.setText(expression);
                }

            }
        });
        factorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(getActivity(), "必须在数字的后面");
                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += "!";
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(getActivity(), "必须在数字的后面");
                }
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator calculator = new Calculator();
                double result = calculator.cal(expression, precision); //调用科学计算器
                historyList.add(expression + "=" + result);
                historyAdapter.notifyDataSetChanged();

                if (result == Double.MAX_VALUE)
                    expression = "Math Error";
                else {
                    expression = String.valueOf(result);
                    if (expression.charAt(expression.length() - 2) == '.' &&
                            expression.charAt(expression.length() - 1) == '0') {
                        expression = expression.substring(0, expression.length() - 2);
                    }
                }
                input.setText(expression);
                output.setText(expression);
                equal_flag = true;
            }
        });
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator calculator = new Calculator();
                double result = calculator.cal(expression, precision); //调用科学计算器
                historyList.add(expression + "=" + result);
                historyAdapter.notifyDataSetChanged();


                if (result == Double.MAX_VALUE)
                    expression = "Math Error";
                else {
                    expression = String.valueOf(result);
                    if (expression.charAt(expression.length() - 2) == '.' &&
                            expression.charAt(expression.length() - 1) == '0') {
                        expression = expression.substring(0, expression.length() - 2);
                    }
                }
                input.setText(expression);
                output.setText(expression);
                equal_flag = true;
            }
        });

    }


    private void switchView() {
        if (isNormalLayout) {
            // 从当前视图渐变消失
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(normalKeyboardLayout, "alpha", 1f, 0f);
            fadeOut.setDuration(300); // 设置动画持续时间
            fadeOut.start();
            normalKeyboardLayout.setVisibility(View.GONE);

            // 显示另一个视图并进行渐变出现
            superKeyboardLayout.setAlpha(0f);
            superKeyboardLayout.setVisibility(View.VISIBLE);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(superKeyboardLayout, "alpha", 0f, 1f);
            fadeIn.setDuration(300); // 设置动画持续时间
            fadeIn.start();
            Toast.makeText(getActivity(), "super_page", Toast.LENGTH_SHORT).show();
        } else {
            // 从当前视图渐变消失
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(superKeyboardLayout, "alpha", 1f, 0f);
            fadeOut.setDuration(300); // 设置动画持续时间
            fadeOut.start();
            superKeyboardLayout.setVisibility(View.GONE);

            // 显示另一个视图并进行渐变出现
            normalKeyboardLayout.setAlpha(0f);
            normalKeyboardLayout.setVisibility(View.VISIBLE);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(normalKeyboardLayout, "alpha", 0f, 1f);
            fadeIn.setDuration(300); // 设置动画持续时间
            fadeIn.start();
            Toast.makeText(getActivity(), "normal_page", Toast.LENGTH_SHORT).show();
        }
        isNormalLayout = !isNormalLayout;
    }

    private boolean isNumber(char c) {
        return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' ||
                c == '6' || c == '7' || c == '8' || c == '9';
    }

    private boolean isOpe(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷';
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
