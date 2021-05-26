package me.travelplan.web.common;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
public class PageDto {
    private int page;
    private int size;
    private String sortType;

    public PageDto(int page, int size) {
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

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size);
    }
}