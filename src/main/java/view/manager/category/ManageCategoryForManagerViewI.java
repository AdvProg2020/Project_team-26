package view.manager.category;

import controller.Exceptions;
import controller.category.Category;
import controller.interfaces.category.ICategoryController;
import controller.interfaces.category.IShowCategoryController;
import view.*;
import view.main.MainPageView;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCategoryForManagerViewI extends View implements ViewI {
    EnumSet<ManageCategoryForManagerViewValidCommands> validCommands;
    private ICategoryController controller;
    private IShowCategoryController detailController;


    public ManageCategoryForManagerViewI(ViewManager managerView, ICategoryController controller, IShowCategoryController detailController) {
        super(managerView);
        validCommands = EnumSet.allOf(ManageCategoryForManagerViewValidCommands.class);
        this.controller = controller;
        this.detailController = detailController;
    }

    @Override
    public View run() {
        while (manager.getIsUserLoggedin()) {
            if ((super.input = (manager.inputOutput.nextLine()).trim()).matches("back"))
                break;
            for (ManageCategoryForManagerViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
        return null;
    }

    protected Category addCategoryForManager(Matcher matcher) {
        matcher.find();
        String name = matcher.group(1);
        try {
            controller.addCategory(name, manager.getTocken());
            createCategory(name);
        } catch (Exceptions.FieldsExistWithSameName fieldsExistWithSameName) {
            fieldsExistWithSameName.getMessage();
        }
        return detailController.getCategory(name, manager.getTocken());
    }

    private void createCategory(String name) {
        System.out.println("type attribute");
        controller.addAttribute(name, manager.inputOutput.nextLine(), manager.getTocken());
        addProduct(name);
        addUsers(name);
        addSubCategories(name);
    }

    private void addSubCategories(String name) {
        System.out.println("enter the subCategories and remember to type end to finish entering subCategories");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            Category category = addCategoryForManager(Pattern.compile("create (.*)").matcher("create " + input));
            try {
                controller.addSubCategories(name, category, manager.getTocken());
            } catch (Exceptions.FieldsExistWithSameName fieldsExistWithSameName) {
                fieldsExistWithSameName.printStackTrace();
            }
            input = manager.inputOutput.nextLine();
        }
    }

    private void addProduct(String name) {
        System.out.println("enter the products and remember to type end to finish entering products");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            try {
                controller.addProduct(name, input, manager.getTocken());
            } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
                theParameterDoesNOtExist.getMessage();
            }
            input = manager.inputOutput.nextLine();
        }
    }

    private void addUsers(String name) {
        System.out.println("enter the users and remember to type end to finish entering users");
        String input = manager.inputOutput.nextLine();
        while (!input.matches("end")) {
            try {
                controller.addCustomer(name, input, manager.getTocken());
            } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
                theParameterDoesNOtExist.getMessage();
            }
            input = manager.inputOutput.nextLine();
        }
    }


    protected void EditCategoryForManager(Matcher matcher) {
        matcher.find();
        ArrayList<String>


    }

    private ArrayList<String> makeEditPart() {
        System.out.println("which field you want to change");
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(manager.inputOutput.nextLine());
        if (inputs.get(0).matches("users")) {
            System.out.println("type [remove|add] [username] [username]");
            inputs.add(manager.inputOutput.nextLine());
            return inputs;
        }
        if (inputs.get(0).matches("subCategories")) {
            System.out.println("enter the name");
            inputs.add("subCategories name ")
        }
        System.out.println("type new one");
        inputs.add(manager.inputOutput.nextLine());
        return inputs;
    }

    protected void RemoveCategoryForManager(Matcher matcher) {
        try {
            controller.removeACategory(matcher.group(1), manager.getTocken());
        } catch (Exceptions.TheParameterDoesNOtExist theParameterDoesNOtExist) {
            theParameterDoesNOtExist.getMessage();
        }

    }

    protected void logOut() {
        new MainPageView(manager).logout(manager.getTocken());
    }

    protected void help(boolean isLoggedIn) {
        System.out.println("add [category]\nremove [category]\nnote that if you want to edit the sub category you have to select category" +
                " \nedit [category]\nhelp");
        if (isLoggedIn)
            System.out.println("logout");

    }


}
