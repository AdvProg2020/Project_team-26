package view.filterAndSort;

import view.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class ProductFilterAndSort extends FilterAndSort {
    public ProductFilterAndSort(ViewManager manager) {
        super(manager);
    }

    @Override
    protected void init() {
        filterFields.put(1, "name");
        filterFields.put(3, "averageRate");
        filterFields.put(4, "brand");
        filterFields.put(5, "description");
        sortField.put(2, "averageRate");
        fieldNameForSort = "averageRate";
        isAscending = true;
    }

    protected void filterWithAvailableFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1));
        switch (chose) {
            case 1:
                filterProduct(1);
                break;
            case 2:
                filterPrice(2);
                break;
            case 3:
                filterRate(3);
                break;
            case 4:
                filterBrand(4);
                break;
            case 5:
                filterDescription(5);
                break;
            default:
                manager.inputOutput.println("enter the number exist in list");
                break;
        }
    }

    private void filterDescription(int chose) {
        manager.inputOutput.println("enter the descriptions you want and type finish to end");
        filterForController.put(filterFields.get(chose), forRepoBuilder(fieldBuilder("description")));
    }

    private void filterRate(int chose) {
        String value = periodBuilder(chose, "rate", true, false);
        if (value == null)
            return;
        filterForController.put(filterFields.get(chose), value);

    }

    private void filterBrand(int chose) {
        manager.inputOutput.println("enter the brands you want and type finish to end");
        filterForController.put(filterFields.get(chose), forRepoBuilder(fieldBuilder("brand")));
    }

    private void filterPrice(int chose) {
        String value = periodBuilder(chose, "price", false, true);
        if (value == null)
            return;
        filterForController.put(filterFields.get(chose), value);
    }

    private String periodBuilder(int chose, String fieldname, boolean isDouble, boolean isLong) {
        StringBuilder period = new StringBuilder("");
        manager.inputOutput.println("enter minimum " + fieldname);
        String min = manager.inputOutput.nextLine();
        String max;
        if (manager.isValidNUmber(min, isDouble, isLong)) {
            period.append(min);
            manager.inputOutput.println("enter maximum " + fieldname);
            max = manager.inputOutput.nextLine();
            if (manager.isValidNUmber(max, isDouble, isLong)) {
                period.append("-" + max);
                return period.toString();
            }
        }
        manager.inputOutput.println("invalid input");
        return null;
    }

    private void filterProduct(int chose) {
        manager.inputOutput.println("enter the names you want and type finish to end");
        filterForController.put(filterFields.get(chose), forRepoBuilder(fieldBuilder("name")));
    }

    private List<String> fieldBuilder(String field) {
        List<String> list = new ArrayList<>();
        while (true) {
            manager.inputOutput.println("enter " + field);
            super.input = manager.inputOutput.nextLine();
            if (super.input.trim().equalsIgnoreCase("finish"))
                break;
            list.add(super.input);
        }
        return list;
    }

    private String forRepoBuilder(List<String> stringList) {
        StringBuilder fields = new StringBuilder("");
        if (stringList.size() > 0) {
            fields.append(stringList.get(0));
            int count = 1;
            for (String s : stringList) {
                if (count != 1) {
                    fields.append("-" + s);
                }
                count++;
            }
        }
        return fields.toString();
    }

    protected void disableSelectedFilter(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1)) - 1;
        if (chose >= filterFields.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        if (filterForController.containsKey(filterFields.get(chose)))
            filterForController.remove(filterFields.get(chose));
    }

    protected void showAvailableFilter() {
        manager.inputOutput.println("filters:");
        filterFields.forEach((number, filed) -> manager.inputOutput.println("" + number + " : " + filed));
    }

    protected void showCurrentFilters() {
        filterForController.forEach((field, value) -> manager.inputOutput.println("" + field + " : " + value));
    }

    protected void showAvailableSort() {
        manager.inputOutput.println("sorts:");
        sortField.forEach((field, value) -> manager.inputOutput.println("" + field + ". " + value));
    }

    protected void showCurrentSort() {
        manager.inputOutput.println(fieldNameForSort);
    }

    protected void disableSelectedSort() {
        fieldNameForSort = new String();
    }


    protected void sortWithAvailableSort(Matcher matcher) {
        matcher.find();
        int chose = Integer.parseInt(matcher.group(1));
        if (chose > sortField.size()) {
            manager.inputOutput.println("enter the number exist in list");
            return;
        }
        fieldNameForSort = sortField.get(chose);
        manager.inputOutput.println("do you want to sort ascending. if type yes it will be ascending" +
                "and if no otherwise");
        if (manager.inputOutput.nextLine().trim().equalsIgnoreCase("yes")) {
            isAscending = true;
            return;
        }
        isAscending = false;
    }

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("show available sorts");
        commandList.add("sort [number]");
        commandList.add("current sort");
        commandList.add("disable sort");
        commandList.add("show available filters");
        commandList.add("filter [number]");
        commandList.add("current filters");
        commandList.add("disable filter [number]");
        if (manager.getIsUserLoggedIn())
            commandList.add("logout");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }
}
