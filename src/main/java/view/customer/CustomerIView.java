package view.customer;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class CustomerIView extends View implements IView {
    EnumSet<CustomerValidCommand> validCommands;

    public CustomerIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(CustomerValidCommand.class);
    }

    @Override
    public View run() {
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (CustomerValidCommand command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    if (command.getView() != null) {
                        command.setManager(this.manager);
                        command.getView().run();
                    } else
                        command.goToFunction(this);
                }
            }
        }
        return null;
    }

    protected void editTheField(Matcher matcher) {

    }

    protected void personalInfo(Matcher matcher) {

    }

    protected void promoCodes() {

    }

    protected void customerInfo(Matcher matcher) {

    }

    protected void balance() {
    }

    protected void cart() {

    }
}
