package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView numDisplayTV, equationTV;

    String equation, currentNum;

    //declare the left and right side of the equation, and operator, as string instance variables
    //so that you don't need to parse them from operation later
    double left;
    double right;
    String operator;

    //declare all buttons:
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
    btnAdd, btnSubtract, btnMultiply, btnDivide, btnDecimal, btnNegPos, btnDelete, btnClear, btnEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numDisplayTV = (TextView) findViewById(R.id.numDisplayTextView);
        equationTV = (TextView) findViewById(R.id.equationTextView);

        btn0 = (Button)findViewById(R.id.btn0);
        btn0.setOnClickListener(onButtonClicked);

        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(onButtonClicked);

        btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(onButtonClicked);

        btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(onButtonClicked);

        btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(onButtonClicked);

        btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(onButtonClicked);

        btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(onButtonClicked);

        btn7 = (Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(onButtonClicked);

        btn8 = (Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(onButtonClicked);

        btn9 = (Button)findViewById(R.id.btn9);
        btn9.setOnClickListener(onButtonClicked);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(onButtonClicked);

        btnSubtract = (Button)findViewById(R.id.btnSubtract);
        btnSubtract.setOnClickListener(onButtonClicked);

        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btnMultiply.setOnClickListener(onButtonClicked);

        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(onButtonClicked);

        btnDecimal = (Button)findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(onButtonClicked);

        btnNegPos = (Button)findViewById(R.id.btnNegPos);
        btnNegPos.setOnClickListener(onButtonClicked);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(onButtonClicked);

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(onButtonClicked);

        btnEquals = (Button)findViewById(R.id.btnEquals);
        btnEquals.setOnClickListener(onButtonClicked);
    } //end onCreate


    public View.OnClickListener onButtonClicked = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //casting the view to a button allows using .getText().toString()
            Button clickedButton = (Button)view;
            String buttonText = clickedButton.getText().toString();

            equation = equationTV.getText().toString();
            currentNum = numDisplayTV.getText().toString();

            switch (clickedButton.getId()) {
                case R.id.btn0:
                case R.id.btn1:
                case R.id.btn2:
                case R.id.btn3:
                case R.id.btn4:
                case R.id.btn5:
                case R.id.btn6:
                case R.id.btn7:
                case R.id.btn8:
                case R.id.btn9:

                    if (currentNum == "0") {

                        numDisplayTV.setText(buttonText);
                    } else {

                        concatToNumDisplayTV(buttonText);
                    }

                    break;

                //for all of the operator buttons, you need to see if there's already a left and right value, and if so
                //the button activates the equals button and perform the operation on the result plus the next number entered.
                case R.id.btnAdd:
                case R.id.btnSubtract:
                case R.id.btnMultiply:
                case R.id.btnDivide:
                    //if the right side of the equation hasn't been assigned yet:
                    //also need to validate that currentNum isn't null or 0 or 0-led(e.x. 09)

                    //this will allow the user to keep working off a result they get from hitting the = button,
                    //or keep the functions rolling if they've only entered numbers and equation
                    if (equation.matches("^\\d*\\.?\\d+\\+?-?/?x?\\d*\\.?\\d+=?$")) {

                        right = Double.parseDouble(currentNum);

                        equationTV.setText("");
                        equation = "";

                        currentNum = String.valueOf(calculateResult());
                    }

                    //store the operator and left number for use in the calc class
                    operator = buttonText;
                    left = Double.parseDouble(currentNum);

                    //update the equation textview
                    concatToEquationTV(currentNum,buttonText);

                    //reset the numDisplayTV
                    numDisplayTV.setText("");

                    break;

                case R.id.btnDecimal:
                    //validate that there isn't already a dot in numDisplay with regex, and if not
                    if (!containsDecimal(currentNum)) {
                        concatToNumDisplayTV(buttonText);
                    }
                    break;

                case R.id.btnNegPos:
                    currentNum = Double.toString(Double.parseDouble(currentNum)*(-1));
                    numDisplayTV.setText(currentNum);

                    break;

                case R.id.btnDelete:
                    //make sure the screen isn't displaying the default value
                    currentNum = deleteChar(currentNum);
                    numDisplayTV.setText(currentNum);

                    break;

                case R.id.btnClear:
                    numDisplayTV.setText("0");
                    equationTV.setText("");

                    break;

                case R.id.btnEquals:
                    //validate that left and operator have been assigned a value
                    if (equation.matches("^\\d*\\.?\\d+\\+?-?/?x?$")) {

                        right = Double.parseDouble(currentNum);
                        concatToEquationTV(currentNum, buttonText);

                        double result = calculateResult();
                        numDisplayTV.setText(Double.toString(result));
                        //somewhere add decimal formatting for if the result is an int
                    }

                    break;

                default:
                    //edit this to do what you want
                    throw new IllegalStateException("Unexpected value: " + clickedButton.getId());
            }
        }
    }; //end OnCLickListener

    private boolean containsOperator(String equation) {
        //this method is used to determine if the next operator clicked will automatically
        //call the calculateResult method, or if this is the first time an operator has been selected.
        return (equation.contains("x") || equation.contains("-") || equation.contains("+") || equation.contains("/"));
    }

    private boolean containsDecimal(String equation) {
        return (equation.contains("."));
    }

    private boolean equationComplete(String equation) {
        //could come up with a regex for matching a patern of 0.00(+-x/)0.00(=)
        return (equation.contains("="));
    }

    private void concatToNumDisplayTV(String buttonText) {

        currentNum = currentNum+buttonText;
        numDisplayTV.setText(currentNum);
    }

    private void concatToEquationTV(String number, String buttonText) {

        //this method will be called after validation and after an operator is selected to add the value from numDisplayTV;
        equation = equation+number+buttonText;
        equationTV.setText(equation);
    }


    private String deleteChar(String string) {

        //give a default value to return if the string passed in is too short
        String newString = "0";

        if ((string != null) && (string.length() > 1)) {
            newString = string.substring(0, string.length() - 1);
        }

        return newString;
    }

    //seperate method so that it can be called by = or second instance of operator
    private double calculateResult() {

        Calc calc = new Calc(left, right, operator);
        return calc.getResult();

    }
}