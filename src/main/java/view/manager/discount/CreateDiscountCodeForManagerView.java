package view.manager.discount;

import view.*;

import java.util.EnumSet;

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
