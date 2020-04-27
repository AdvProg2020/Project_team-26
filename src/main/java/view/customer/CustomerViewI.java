package view.customer;

import view.*;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class CustomerViewI extends View implements ViewI {
    EnumSet<CustomerValidCommand> validCommands;

    public CustomerViewI(ViewManager manager) {
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
