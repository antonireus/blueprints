package org.fundaciobit.web.faces;

/**
 * Per mantenir l'estat bàsic d'una paginació.
 */
public class PaginationHelper {

    private int pageSize;
    private int page;
    private int count;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageFirstItem() {
        return page*pageSize;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasNextPage() {
        return (page+1)*pageSize+1 <= count;
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }
}
