package com.snegirekk.books_library.review.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDto<T> {

    private int page;
    private int itemsPerPage;
    private int totalPages;
    private List<T> items;

    public PageDto() {
        this.items = new ArrayList<>();
    }

    public int getPage() {
        return page;
    }

    public PageDto<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public PageDto<T> setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public PageDto<T> setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<T> getItems() {
        return items;
    }

    public PageDto<T> setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public PageDto<T> addItem(T item) {
        items.add(item);
        return this;
    }
}
