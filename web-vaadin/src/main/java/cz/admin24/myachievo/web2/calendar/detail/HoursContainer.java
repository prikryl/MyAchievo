package cz.admin24.myachievo.web2.calendar.detail;

import com.vaadin.data.util.IndexedContainer;

public class HoursContainer extends IndexedContainer {

    private static final long      serialVersionUID = 1L;
    private static final Integer[] AVAILABLE_HOURS  = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };


    public HoursContainer() {
        for (Integer i : AVAILABLE_HOURS)
            addItem(i);
    }
}