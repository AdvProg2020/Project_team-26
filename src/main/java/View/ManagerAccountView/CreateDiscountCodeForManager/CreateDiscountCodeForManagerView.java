package View.ManagerAccountView.CreateDiscountCodeForManager;

import View.*;

import java.util.EnumSet;
import java.util.List;

public class CreateDiscountCodeForManagerView extends View {
    EnumSet<CreateDiscountCodeFoeManagerViewValidCOmmands> validCommands;

    CreateDiscountCodeForManagerView() {
        validCommands = EnumSet.allOf(CreateDiscountCodeFoeManagerViewValidCOmmands.class);
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }
}
