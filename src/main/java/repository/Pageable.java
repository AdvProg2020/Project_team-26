package repository;

public class Pageable {

    private boolean isPaged;
    private int from;
    private int maxResult;
    private int sortField;
    private Direction direction;

    public boolean isPaged() {
        return isPaged;
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

    public int getSortField() {
        return sortField;
    }

    public void setSortField(int sortField) {
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

    enum Direction{
        ASCENDING, DESCENDING
    }
}
