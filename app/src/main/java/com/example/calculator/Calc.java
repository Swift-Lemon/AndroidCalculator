package com.example.calculator;

public class Calc {

    private double left;
    private double right;
    private double result;
    private String operator;

    public void setLeft(double left) {this.left = left;}
    public void setRight(double right) {this.right = right;}
    public void setOperator(String operator) {this.operator = operator;}

    public double getLeft() {return left;}

    public double getResult() {
        switch (operator) {
            case "+":
                this.result = add(left, right);
                break;
            case "-":
                this.result = subtract(left, right);
                break;
            case "x":
                this.result = multiply(left, right);
                break;
            case "/":
                this.result = divide(left, right);
                break;
        }
        return this.result;
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
