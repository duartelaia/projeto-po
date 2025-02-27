package xxl.app.search;

import java.util.List;

import pt.tecnico.uilib.menus.Command;
import xxl.Search;
import xxl.Spreadsheet;
import xxl.content.*;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;
import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.UnrecognizedFunctionException;

/**
 * Command for searching content values.
 */
class DoShowEmptyStrings extends Command<Spreadsheet> {

    DoShowEmptyStrings(Spreadsheet receiver) {
        super("Celulas com string vazia", receiver);
    }

    @Override
    protected final void execute() {

        Search search = new SearchEmptyString();
        List<String> results = _receiver.search(search);
        
        for(String cell : results){
            _display.popup(cell);
        }
    }

    private class SearchEmptyString extends Search {
        
        private void aux(Content content){
            try{
                LitString value = (LitString) content.getValue();
                setOk(value.getStringValue().equals("\'"));
            }catch(ClassCastException e){
                setOk(false);
            }
        }

        public void visit(Concat concat){
            aux(concat);
        }

        public void visit(Coalesce coalesce){
            aux(coalesce); 
        }

        public void visit(Ref ref){
            aux(ref); 
        }

        public void visit(LitString litString){
            setOk(litString.getStringValue().equals("\'"));
        }

    }

}