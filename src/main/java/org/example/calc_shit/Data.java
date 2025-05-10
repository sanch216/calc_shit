package org.example.calc_shit;

public class Data {
    private String name;
    private double value;
    private double tax;
    public Data(String name, double value) {
        this.name = name;
        this.value = value;
        this.tax = 0.0;
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

    public void setTax(double tax) {
        this.tax = tax;
    }
}
