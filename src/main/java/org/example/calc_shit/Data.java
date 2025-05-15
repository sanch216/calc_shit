package org.example.calc_shit;

public class Data {
    private String name;
    private double value;
    private double tax;
    private double total;
    public Data(String name, double value) {
        this.name = name;
        this.value = value;
        this.tax = 0.0;
        this.total = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    public double sumTotal(double x) {
        return total += x;
    }
    public double setTotal(double x) {
        return total = x;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Value: " + value + ", Tax: " + tax + ", Total: " + total;
    }
}
