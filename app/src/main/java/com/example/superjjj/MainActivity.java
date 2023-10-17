package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superjjj.util.PrecisionUtil;
import com.example.superjjj.util.SwitchButton;
import com.example.superjjj.util.ToastUtil;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scrollView);
        input = findViewById(R.id.input_view);
        output = findViewById(R.id.output_view);

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

        normalKeyboardLayout = findViewById(R.id.normalKeyboardLayout);
        superKeyboardLayout = findViewById(R.id.superKeyboardLayout);

        switchButton = findViewById(R.id.change_btn);
        // 设置开关状态改变的监听器
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchView();
            }

        });

        precisionSpinner = findViewById(R.id.precision);

        // 初始化下拉列表
        PrecisionUtil.initPrecisionSpinner(this, precisionSpinner);
        precisionSpinner.setSelection(2);
        precisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 当选择项改变时，更新选中的精度值
                precision = Integer.parseInt((String) parentView.getItemAtPosition(position));
                ToastUtil.showShort(MainActivity.this,"现在的精度是: " + precision);
                // TODO: 处理选择的精度值
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 在用户未选择任何项时的处理
                precision = 3;
            }
        });
        ToastUtil.showShort(MainActivity.this,"现在的精度是3");

        button0 = findViewById(R.id.number0);
        button1 = findViewById(R.id.number1);
        button2 = findViewById(R.id.number2);
        button3 = findViewById(R.id.number3);
        button4 = findViewById(R.id.number4);
        button5 = findViewById(R.id.number5);
        button6 = findViewById(R.id.number6);
        button7 = findViewById(R.id.number7);
        button8 = findViewById(R.id.number8);
        button9 = findViewById(R.id.number9);
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;

        button0s = findViewById(R.id.number0s);
        button1s = findViewById(R.id.number1s);
        button2s = findViewById(R.id.number2s);
        button3s = findViewById(R.id.number3s);
        button4s = findViewById(R.id.number4s);
        button5s = findViewById(R.id.number5s);
        button6s = findViewById(R.id.number6s);
        button7s = findViewById(R.id.number7s);
        button8s = findViewById(R.id.number8s);
        button9s = findViewById(R.id.number9s);
        buttonss[0] = button1s;
        buttonss[1] = button2s;
        buttonss[2] = button3s;
        buttonss[3] = button4s;
        buttonss[4] = button5s;
        buttonss[5] = button6s;
        buttonss[6] = button7s;
        buttonss[7] = button8s;
        buttonss[8] = button9s;

        dot = findViewById(R.id.dot);
        dots = findViewById(R.id.dots);
        equal = findViewById(R.id.equal);
        equals = findViewById(R.id.equals);
        add = findViewById(R.id.add);
        adds = findViewById(R.id.adds);
        min = findViewById(R.id.min);
        mins = findViewById(R.id.mins);
        mul = findViewById(R.id.mul);
        muls = findViewById(R.id.muls);
        div = findViewById(R.id.div);
        divs = findViewById(R.id.divs);
        symbol = findViewById(R.id.symbol);
        symbols = findViewById(R.id.symbols);
        back = findViewById(R.id.back);
        backs = findViewById(R.id.backs);
        clear = findViewById(R.id.clear);
        clears = findViewById(R.id.clears);
        percent = findViewById(R.id.percent);
        percents = findViewById(R.id.percents);
        reciprocal = findViewById(R.id.reciprocal);
        factorial = findViewById(R.id.factorial);
        tan = findViewById(R.id.tan);
        cos = findViewById(R.id.cos);
        sin = findViewById(R.id.sin);
        lg = findViewById(R.id.lg);
        ln = findViewById(R.id.ln);
        sqrt = findViewById(R.id.sqrt);
        left_br = findViewById(R.id.left_bracket);
        right_br = findViewById(R.id.right_bracket);

        superBtn[0] = sqrt;
        superBtn[1] = ln;
        superBtn[2] = lg;
        superBtn[3] = sin;
        superBtn[4] = cos;
        superBtn[5] = tan;

        initInput();
        initNumBtns();
        initBaseOpers();
        initBaseCalculatorFunction();
        initSuperFunction();


    }


    private void initInput() {
        input.setMovementMethod(ScrollingMovementMethod.getInstance()); //内容自动滚动到最新的一行
        input.setTextIsSelectable(true); //长按复制
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
                        ToastUtil.showShort(MainActivity.this, "不可输入0");
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
                        ToastUtil.showShort(MainActivity.this, "不可输入0");
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
                if (expression.length() == 0) {                                //1.为空，+0.
                    expression += "0.";
                } else if (isOpe(expression.charAt(expression.length() - 1))) {  //2.最后一个字符为oper，+0.
                    expression += "0.";
                } else if (isNumber(expression.charAt(expression.length() - 1))) {   //3.最后一个字符为num，+.
                    expression += ".";
                } else {                                                    //4.除此之外，不加
                    expression += "";
                    ToastUtil.showShort(MainActivity.this, "不可连续输入小数点0");
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
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    expression += "0.";
                } else if (isOpe(expression.charAt(expression.length() - 1))
                        || expression.charAt(expression.length() - 1) == '(') {  //2.mathNow的最后一个字符为oper，+0.
                    expression += "0.";
                } else if (isNumber(expression.charAt(expression.length() - 1))) {   //3.mathNow的最后一个字符为num，+.
                    expression += ".";
                } else {                                                    //4.除此之外，不加
                    expression += "";
                    ToastUtil.showShort(MainActivity.this, "不可连续输入小数点0");

                }
                input.setText(expression);
            }
        });
    }

    private void initBaseOpers() {
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    ToastUtil.showShort(MainActivity.this, "不可在空公式中输入运算符");
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + div.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += div.getText().toString();
                }
                input.setText(expression);
            }
        });
        divs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + divs.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += divs.getText().toString();
                }
                input.setText(expression);
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + mul.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += mul.getText().toString();
                }
                input.setText(expression);
            }
        });
        muls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + muls.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += muls.getText().toString();
                }
                input.setText(expression);
            }
        });
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + min.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += min.getText().toString();
                }
                input.setText(expression);
            }
        });
        mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + mins.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += mins.getText().toString();
                }
                input.setText(expression);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    Toast.makeText(MainActivity.this, "不可在空公式中输入运算符", Toast.LENGTH_SHORT).show();
                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + add.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += add.getText().toString();
                }
                input.setText(expression);
            }
        });
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equal_flag) {
                    equal_flag = false;
                }
                if (expression.length() == 0) {                                //1.mathNow为空，+0.
                    ToastUtil.showShort(MainActivity.this, "不可在空公式中输入运算符");

                } else if (expression.matches("^.*[+\\-×÷.]+$")) {  //2.mathNow的最后一个字符为oper，+0.
                    expression = expression.substring(0, expression.length() - 1) + add.getText().toString();
                } else {                                                    //4.除此之外，不加
                    expression += add.getText().toString();
                }
                input.setText(expression);
            }
        });
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
                    ToastUtil.showShort(MainActivity.this, "现在没有内容");
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
                    ToastUtil.showShort(MainActivity.this, "现在没有内容");
                }
            }
        });
        percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(MainActivity.this, "不能这样加百分号");
                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += percent.getText().toString();
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(MainActivity.this, "不能这样加百分号");

                }
            }
        });
        percents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(MainActivity.this, "不能这样加百分号");

                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += percents.getText().toString();
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(MainActivity.this, "不能这样加百分号");


                }
            }
        });
        symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(MainActivity.this, "不能这样改变正负");
                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) { // 如果前面是数字的话
                    int i;
                    for (i = expression.length() - 1; i >= 0; i--) {
                        if (!isNumber(expression.charAt(i)) || expression.charAt(i) == '.') {
                            break; // 此时的i的值是符号的位置，所以下面的substring要加一
                        }
                    }
                    // 把数据切开
                    String before = expression.substring(0, i + 1);
                    String after = expression.substring(i + 1, expression.length()); //
                    expression = before + "(-" + after + ")";
                    input.setText(expression);
                } // 此时是把正数变负数
                else if (expression.matches("^.*\\(-[0123456789.]+\\)$")) {
                    String before = expression.substring(0, expression.lastIndexOf("(-"));
                    // 表示记录（-之前的所有数据
                    String after = expression.substring(expression.lastIndexOf("(-") + 2,
                            expression.length() - 1);
                    // 表示记录（-后的去掉右括号的所有数值
                    expression = before + after;
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(MainActivity.this, "不能这样改变正负");

                }
            }
        });
        symbols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() == 0) {
                    ToastUtil.showShort(MainActivity.this, "不能这样改变正负");

                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) { // 如果前面是数字的话
                    int i;
                    for (i = expression.length() - 1; i >= 0; i--) {
                        if (!isNumber(expression.charAt(i)) || expression.charAt(i) == '.') {
                            break; // 此时的i的值是符号的位置，所以下面的substring要加一
                        }
                    }
                    // 把数据切开
                    String before = expression.substring(0, i + 1);
                    String after = expression.substring(i + 1, expression.length()); //
                    expression = before + "(-" + after + ")";
                    input.setText(expression);
                } // 此时是把正数变负数
                else if (expression.matches("^.*\\(-[0123456789.]+\\)$")) {
                    String before = expression.substring(0, expression.lastIndexOf("(-"));
                    // 表示记录（-之前的所有数据
                    String after = expression.substring(expression.lastIndexOf("(-") + 2,
                            expression.length() - 1);
                    // 表示记录（-后的去掉右括号的所有数值
                    expression = before + after;
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(MainActivity.this, "不能这样改变正负");

                }
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
                            ToastUtil.showShort(MainActivity.this, "不允许这样添加");

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
                    ToastUtil.showShort(MainActivity.this, "必须在数字的后面");

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
                    ToastUtil.showShort(MainActivity.this, "必须在数字的后面");

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
                    ToastUtil.showShort(MainActivity.this, "必须在数字的后面");
                    return;
                }
                if (isNumber(expression.charAt(expression.length() - 1))) {
                    expression += "!";
                    input.setText(expression);
                } else {
                    ToastUtil.showShort(MainActivity.this, "必须在数字的后面");
                }
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator calculator = new Calculator();
                double result = calculator.cal(expression, precision); //调用科学计算器

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
            Toast.makeText(MainActivity.this, "super_page", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MainActivity.this, "normal_page", Toast.LENGTH_SHORT).show();
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

}
