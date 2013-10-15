package cz.admin24.myachievo.web2.calendar.detail;

import com.vaadin.data.Container;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class LikeComboBox extends ComboBox {

    public LikeComboBox(String caption) {
        super(caption);
        setFilteringMode(FilteringMode.CONTAINS);
    }


    public LikeComboBox(String caption, Container container) {
        super(caption, container);
        setFilteringMode(FilteringMode.CONTAINS);
    }
}
