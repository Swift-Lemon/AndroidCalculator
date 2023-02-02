package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView equationTV, numDisplayTV;

    //strings used to access and manipulate contents of text views
    private String equation, numDisplayContent;

    //the user will never be allowed to enter a number with more than 9 digits:
    //(equation results will still show numbers outside this limit)
    private final int MAX_LENGTH = 9;

    private Calc calc;

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
    btnAdd, btnSubtract, btnMultiply, btnDivide, btnDecimal, btnNegPos, btnDelete, btnClear, btnEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numDisplayTV = (TextView) findViewById(R.id.numDisplayTextView);
        equationTV = (TextView) findViewById(R.id.equationTextView);

        calc = new Calc();

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
            //casting the view to a button allows using .getText()
            Button clickedButton = (Button)view;
            String buttonText = clickedButton.getText().toString();

            //on click, values currently displayed on the screen will be read for validation/manipulation
            equation = equationTV.getText().toString();
            numDisplayContent = numDisplayTV.getText().toString();

            //having NaN or Infinity as a result will cause any button to reset the ui:
            if ("NaN".equals(numDisplayContent) || "Infinity".equals(numDisplayContent) || "-Infinity".equals(numDisplayContent)) {

                resetAll();
            }

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

                    //contentEquals allows checking between a string and a charsequence. (or string and a string)
                    //on app creation/ui reset the numDisplayTV is set to a charSequence since
                    //it takes the value directly from strings.xml

                    if (numDisplayContent.contentEquals("0")) {
                        //this prevents 0-led numbers (ex.08)
                        numDisplayTV.setText(buttonText);

                    } else if (numDisplayContent.contentEquals("-0")) {

                        numDisplayTV.setText("-" + buttonText);

                    } else if (equationComplete()) {

                        resetEquationTV();
                        numDisplayTV.setText(buttonText);

                    } else {

                        concatToNumDisplayTV(buttonText);
                    }

                    break;

                case R.id.btnAdd:
                case R.id.btnSubtract:
                case R.id.btnMultiply:
                case R.id.btnDivide:

                    //this will allow the user to keep working off a result they get from hitting the = button,
                    if (equationComplete()) {

                        //store the left number for use in the calc class
                        calc.setLeft(Double.parseDouble(numDisplayContent));

                        resetEquationTV();

                    //this allows a user to enter a new number and continue the equation displyed in equationTV, without hitting the = button
                    //it's necessary to validate 0's so that no form of division by zero (or rather the NaN result) can go to equation TV
                    } else if (equationHasLeftAndOperator() && !removeEmptyDecimals(numDisplayContent).equals("0") && !removeEmptyDecimals(numDisplayContent).equals("-0")) {

                        calc.setRight(Double.parseDouble(numDisplayContent));

                        calc.setLeft(calc.getResult());
                        numDisplayContent = Double.toString(calc.getLeft());

                        resetEquationTV();

                    //this allows the user to change their operator after they've already picked one
                    } else if (equationHasLeftAndOperator()) {

                        numDisplayContent = Double.toString(calc.getLeft());

                        resetEquationTV();

                    } else {
                        //store the left number for use in the calc class
                        calc.setLeft(Double.parseDouble(numDisplayContent));
                    }

                    calc.setOperator(buttonText);

                    concatToEquationTV(removeEmptyDecimals(numDisplayContent), buttonText);

                    //reset the numDisplayTV
                    resetNumDisplayTV();

                    break;

                case R.id.btnDecimal:

                    //if the user is starting new after a finished equation, reset displays
                    if (equationComplete()) {

                        resetNumDisplayTV();
                        resetEquationTV();
                    }

                    //validate that there isn't already a dot in numDisplay
                    if (!numDisplayContent.contains(".")) {

                        concatToNumDisplayTV(buttonText);
                    }

                    break;

                case R.id.btnNegPos:

                    numDisplayContent = Double.toString(Double.parseDouble(numDisplayContent)*(-1));
                    numDisplayTV.setText(removeEmptyDecimals(numDisplayContent));

                    break;

                case R.id.btnDelete:

                    numDisplayTV.setText(deleteChar(numDisplayContent));

                    break;

                case R.id.btnClear:

                    resetAll();

                    break;

                case R.id.btnEquals:
                    //validate that left and operator have been assigned a value
                    if (equationHasLeftAndOperator()) {

                        calc.setRight(Double.parseDouble(numDisplayContent));

                        concatToEquationTV(numDisplayContent, buttonText);

                        double result = calc.getResult();
                        numDisplayTV.setText(removeEmptyDecimals(Double.toString(result)));
                    }

                    break;
            }
        }
    }; //end OnCLickListener

    private void concatToNumDisplayTV(String buttonText) {

        if (!(numDisplayContent.length() == MAX_LENGTH)) {
            numDisplayContent = numDisplayContent + buttonText;
        }
            numDisplayTV.setText(numDisplayContent);
    }

    private void concatToEquationTV(String number, String buttonText) {

        //this method will be called after validation and after an operator is selected to add the value from numDisplayTV;
        equation = equation+removeEmptyDecimals(number)+buttonText;
        equationTV.setText(equation);
    }

    //the purpose of the two booleans below is better code readability in conditionals
    private boolean equationHasLeftAndOperator() {
        return equation.matches("^(-?\\d*\\.?\\d+)(-?\\+?/?x?)$");
    }

    private boolean equationComplete() {
        return equation.matches("^(-?\\d*\\.?\\d+)(-?\\+?/?x?)(-?\\d*\\.?\\d+)=$");
    }

    private void resetAll() {

        resetNumDisplayTV();
        resetEquationTV();

        //below isn't really necessary since the equals button
        //won't be functional if new values haven't been given
        numDisplayContent = numDisplayTV.getText().toString();
        calc.setLeft(0);
        calc.setRight(0);
        calc.setOperator("");
    }

    private void resetEquationTV() {
        equationTV.setText("");
        equation = "";
    }

    private void resetNumDisplayTV() {
        numDisplayTV.setText(R.string.txt0);
    }

    private String deleteChar(String string) {

        //give a default value to return if the string passed in is too short
        String newString = "0";

        //the third piece of logic will ensure the user isn't left with a lone "-", and the numDisplayTV resets
        if ((string != null) && (string.length() > 1) && (!string.matches("^-\\d$"))) {
            newString = string.substring(0, string.length() - 1);
        }

        return newString;
    }

    private String removeEmptyDecimals(String num) {

        //could add whole number formatting in here as well
        String newString = num;

        //if the number entered is a whole number, remove the empty decimal
        if ((Double.parseDouble(num) % 1 == 0))
            newString = String.format(Locale.CANADA, "%.0f", Double.parseDouble(num));

        return newString;
    }
}