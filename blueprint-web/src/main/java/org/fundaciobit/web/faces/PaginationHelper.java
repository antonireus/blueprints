package org.fundaciobit.web.faces;

public class PaginationHelper {

    private int pageSize;
    private int page;
    private int count;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageFirstItem() {
        return page*pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasNextPage() {
        return (page+1)*pageSize+1 <= count;
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

}
