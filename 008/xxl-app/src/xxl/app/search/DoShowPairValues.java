package xxl.app.search;

import java.util.List;

import pt.tecnico.uilib.menus.Command;
import xxl.Search;
import xxl.Spreadsheet;
import xxl.content.*;

/**
 * Command for searching content values.
 */
class DoShowPairValues extends Command<Spreadsheet> {

    DoShowPairValues(Spreadsheet receiver) {
        super("Mostrar inteiros pares", receiver);
    }

    @Override
    protected final void execute() {

        Search search = new SearchValue();
        List<String> results = _receiver.search(search);
        
        for(String cell : results){
            _display.popup(cell);
        }
    }

    private class SearchValue extends Search {

        public void visit(LitInt litInt){
            setOk(litInt.getIntegerValue() % 2 == 0);
        }

    }

}