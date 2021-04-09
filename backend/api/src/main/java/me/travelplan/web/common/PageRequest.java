package me.travelplan.web.common;

import lombok.Getter;

@Getter
public class PageRequest {
    private int page;
    private int size;

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }


    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}