package repository;

import java.util.HashMap;

public class Pageable {

    private boolean isPaged;
    private int from;
    private int maxResult;
    private String sortField;
    private Direction direction;
    private HashMap<String,String> filters;

    public Pageable(int from, int maxResult, String sortField, Direction direction) {
        isPaged = true;
        this.from = from;
        this.maxResult = maxResult;
        this.sortField = sortField;
        this.direction = direction;
    }

    public Pageable() {
        isPaged = false;
    }

    public boolean isPaged() {
        return isPaged;
    }

    public HashMap<String, String> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, String> filters) {
        this.filters = filters;
    }

    public void setPaged(boolean paged) {
        isPaged = paged;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        isPaged = true;
        this.from = from;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        isPaged = true;
        this.maxResult = maxResult;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        isPaged = true;
        this.sortField = sortField;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        isPaged = true;
        this.direction = direction;
    }

    public enum Direction{
        ASCENDING, DESCENDING
    }
}
