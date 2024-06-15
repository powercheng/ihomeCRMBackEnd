package com.ihomeCabinet.crm.tools.response;

import java.util.List;

public class PaginatedResult<T> {

    private List<T> items;
    private long total;

    public PaginatedResult(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }

    // Getters and setters
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
