package cz.admin24.myachievo.web2.calendar.detail;

import com.vaadin.data.util.IndexedContainer;

public class MinutesContainer extends IndexedContainer {

    private static final long      serialVersionUID  = 1L;
    private static final Integer[] AVAILABLE_MINUTES = new Integer[] { 0, 15, 30, 45 };


    public MinutesContainer() {
        for (Integer i : AVAILABLE_MINUTES)
            addItem(i);
    }
}