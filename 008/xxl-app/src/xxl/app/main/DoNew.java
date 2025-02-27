package xxl.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;


/**
 * Open a new file.
 */
class DoNew extends Command<Calculator> {

    DoNew(Calculator receiver) {
        super(Label.NEW, receiver);
    }

    @Override
    protected final void execute() throws CommandException{
        if (_receiver.hasChanged() &&
            Form.confirm(Prompt.saveBeforeExit())){

            DoSave saver = new DoSave(_receiver);
            saver.execute();
        }
        int nLines = Form.requestInteger(Prompt.lines());
        int nColumns = Form.requestInteger(Prompt.columns());
        _receiver.newSpreadsheet(nLines, nColumns);
    }

}
