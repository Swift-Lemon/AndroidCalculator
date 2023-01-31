package com.example.calculator;

public class Calc {

    private double num1;
    private double num2;
    private double result;
    private String operator;

    public double getResult() {return this.result;}

    public Calc(double num1, double num2, String operator) {

        //maybe alter to get rid of Operator enum:
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;

        switch (operator) {
            case "+":
                this.result = add(num1, num2);
                break;
            case "-":
                this.result = subtract(num1, num2);
                break;
            case "x":
                this.result = multiply(num1, num2);
                break;
            case "/":
                this.result = divide(num1, num2);
                break;
        }
    }

    private double add(double num1, double num2) {
        return num1 + num2;
    }

    private double subtract(double num1, double num2) {
        return num1 - num2;
    }

    private double multiply(double num1, double num2) {
        return num1 * num2;
    }

    private double divide(double num1, double num2) {
        return num1 / num2;
    }

}
