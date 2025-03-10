package xxl.app.search;

import xxl.Spreadsheet;

/**
 * Menu builder for search operations.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

    public Menu(Spreadsheet receiver) {
        super(Label.TITLE, //
                new DoShowValues(receiver), //
                new DoShowFunctions(receiver), //
                new DoShowPairValues(receiver),
                new DoShowEmptyStrings(receiver)
        );
    }

}
