package com.malikmotan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit;
    private Button btn_clear;
    private StringBuffer stringBuff = new StringBuffer("");
    private BigDecimal num1, num2;
    private boolean isInitialValue = false;
    private String operation = null;
    private String result = null;
    private int scale = 2;
    private double memory=0;
    private boolean negate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editText);
        findViewById(R.id.buttonZer).setOnClickListener(this);
        findViewById(R.id.buttonOne).setOnClickListener(this);
        findViewById(R.id.buttonTwo).setOnClickListener(this);
        findViewById(R.id.buttonThr).setOnClickListener(this);
        findViewById(R.id.buttonFou).setOnClickListener(this);
        findViewById(R.id.buttonFiv).setOnClickListener(this);
        findViewById(R.id.buttonSix).setOnClickListener(this);
        findViewById(R.id.buttonSev).setOnClickListener(this);
        findViewById(R.id.buttonEig).setOnClickListener(this);
        findViewById(R.id.buttonNin).setOnClickListener(this);

        findViewById(R.id.buttonBack).setOnClickListener(this);
        findViewById(R.id.buttonDiv).setOnClickListener(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonMul).setOnClickListener(this);
        findViewById(R.id.buttonEqu).setOnClickListener(this);
        findViewById(R.id.buttonSub).setOnClickListener(this);
        findViewById(R.id.buttonC).setOnClickListener(this);

        findViewById(R.id.buttonMC).setOnClickListener(this);
        findViewById(R.id.buttonMR).setOnClickListener(this);
        findViewById(R.id.buttonMplus).setOnClickListener(this);
        findViewById(R.id.buttonN).setOnClickListener(this);

        btn_clear = (Button) findViewById(R.id.buttonC);
        btn_clear.setOnClickListener(this);

        btn_clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edit.setText("");
                stringBuff = new StringBuffer("");
                isInitialValue = false;
                operation = null;
                negate = false;

            }
        });
    }

    public static double round(Double result, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(result));
        BigDecimal one = new BigDecimal(1);
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double add(BigDecimal num1,BigDecimal num2) {
        return num1.add(num2).doubleValue();
    }

    public static double sub(BigDecimal num1,BigDecimal num2) {
        return num1.subtract(num2).doubleValue();
    }

    public static double mul(BigDecimal num1,BigDecimal num2) {
        return num1.multiply(num2).doubleValue();
    }

    public static double div(BigDecimal num1,BigDecimal num2, int scale) {
        return num1.divide(num2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (v.getId()) {

            case R.id.buttonBack:
                if (!(stringBuff.toString() == "")) {
                        String lastStr = String.valueOf(stringBuff.charAt(stringBuff.length() - 1));


                    stringBuff.deleteCharAt(stringBuff.length() - 1);
                    if(stringBuff.toString().equals("")){
                        negate = false;
                    }
                    edit.setText(stringBuff.toString());
                } else {
                    edit.setText("");
                    result = null;
                    stringBuff = new StringBuffer("");
                    negate = false;
                }
                isInitialValue = false;
                break;
            //---------------------------------------
            case R.id.buttonAdd:
                setInitialValue(button.getText().toString());
                break;
            //---------------------------------------
            case R.id.buttonSub:
                if (!negate) {
                    if (stringBuff.toString().equals("")) {
                        stringBuff.append("-");
                        edit.setText(stringBuff.toString());
                        negate = true;
                        break;
                    }
                }
                setInitialValue(button.getText().toString());
                break;
            //--------------------------------------------
            case R.id.buttonMul:
                setInitialValue(button.getText().toString());
                break;
            //----------------------------------------------
            case R.id.buttonDiv:
                setInitialValue(button.getText().toString());
                break;
            //--------------------------------------
            case R.id.buttonEqu:
                if (operation == null || stringBuff.toString().equals("")
                        || !isInitialValue)
                    break;
                performOperation();
                break;
            //-----------------------------------------------
            case R.id.buttonMC:
                memory = 0;
            break;

            //-----------------------------------------------
            case R.id.buttonMplus:
                memory += Double.parseDouble(String.valueOf(edit.getText()));
                edit.setText("");
                break;

            //-----------------------------------------------
            case R.id.buttonMR:
                stringBuff.append(memory);
                break;

            //-----------------------------------------------
            case R.id.buttonN:
                stringBuff.append("-");
                negate = true;

                break;

            default:
                stringBuff.append(button.getText().toString());
                edit.setText(stringBuff.toString());
                break;
        }
    }

    private void performOperation() {
        if(stringBuff.toString().equals("-")) return;
        double result = 0;
        num2 = new BigDecimal(stringBuff.toString());
        if (operation.equals("+")) {
            result = add(num1, num2);
        }
        if (operation.equals("-")) {
            result = sub(num1, num2);
        }
        if (operation.equals("X")) {
            result = mul(num1, num2);
        }
        if (operation.equals("/")) {
            if (!num2.equals(BigDecimal.ZERO)) {
                result = div(num1, num2, scale);

            } else {
                Toast.makeText(MainActivity.this, "Invalid Operation！", Toast.LENGTH_LONG)
                        .show();
                edit.setText("");
                stringBuff = new StringBuffer("");
                operation = null;
                isInitialValue = false;

                return;
            }
        }
        this.result = String.valueOf(round(result, scale));
        String[] resultStrings = this.result.split("\\.");
        if (resultStrings[1].equals("0")) {
            this.result = resultStrings[0];
        }
        edit.setText(this.result);
        stringBuff = new StringBuffer("");
        isInitialValue = false;
        operation = null;
        negate = true;
    }

    private void setInitialValue(String oper) {
        if (operation != null && !stringBuff.toString().equals("") && isInitialValue) {
            performOperation();
        }
        operation = oper;
        if (!(stringBuff.toString() == "") && !stringBuff.toString().equals("-")) {
            num1 = new BigDecimal(stringBuff.toString());
            edit.setText(stringBuff.toString());
            stringBuff = new StringBuffer("");
            result = null;
            isInitialValue = true;
            negate = false;

        } else if (result != null) {
            num1 = new BigDecimal(result);
            edit.setText(result);
            result = null;
            isInitialValue = true;
            negate = false;
        }
    }
}

