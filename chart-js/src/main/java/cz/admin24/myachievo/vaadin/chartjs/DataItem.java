package cz.admin24.myachievo.vaadin.chartjs;

import java.io.Serializable;

public class DataItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public Number             value;
    public String             color;


    public DataItem(Number value, String color) {
        this.value = value;
        this.color = color;
    }

}
