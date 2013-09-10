package cz.admin24.myachievo.web2.service;

import java.util.Comparator;
import java.util.List;

import cz.admin24.myachievo.connector.http.dto.BaseObject;

public final class BaseObjectComparator implements Comparator<BaseObject> {
    /**
     *
     */
    private final List<String> order;


    /**
     * @param achievoConnectorWrapper
     */
    public BaseObjectComparator(List<String> order) {
        this.order = order;
    }


    @Override
    public int compare(BaseObject lhs, BaseObject rhs) {
        int lIdx = order.indexOf(lhs.getName());
        int rIdx = order.indexOf(rhs.getName());

        lIdx = lIdx == -1 ? Integer.MAX_VALUE : lIdx;
        rIdx = rIdx == -1 ? Integer.MAX_VALUE : rIdx;

        return lIdx - rIdx;
    }
}