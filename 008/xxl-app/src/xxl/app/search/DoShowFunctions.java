package xxl.app.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.tecnico.uilib.menus.Command;
import xxl.Search;
import xxl.Spreadsheet;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;

/**
 * Command for searching function names.
 */
class DoShowFunctions extends Command<Spreadsheet> {

    DoShowFunctions(Spreadsheet receiver) {
        super(Label.SEARCH_FUNCTIONS, receiver);
        addStringField("functionToShow", Prompt.searchFunction());
    }

    @Override
    protected final void execute() {
        Search search = new SearchFunctionName(stringField("functionToShow"));
        List<String> searchResults = _receiver.search(search);
        Collections.sort(searchResults, new FunctionNameComparator());
        for(String cell : searchResults){
            _display.popup(cell);
        }
    }

    private class SearchFunctionName extends Search {
        public String _searchValue;

        public SearchFunctionName(String value){
            _searchValue = value;
        }
        
        public void visit(Add add){
            setOk("ADD".contains(_searchValue));
        }
        public void visit(Div div){
            setOk("DIV".contains(_searchValue));
        }
        public void visit(Mul mul){
            setOk("MUL".contains(_searchValue));
        }
        public void visit(Sub sub){
            setOk("SUB".contains(_searchValue));
        }
        public void visit(Pow pow){
            setOk("POW".contains(_searchValue));
        }
        public void visit(Average average){
            setOk("AVERAGE".contains(_searchValue));
        }
        public void visit(Coalesce coalesce){
            setOk("COALESCE".contains(_searchValue));
        }
        public void visit(Concat concat){
            setOk("CONCAT".contains(_searchValue));
        }
        public void visit(Product product){
            setOk("PRODUCT".contains(_searchValue));
        }
    }

    private class FunctionNameComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2){
            String[] splittedS1 = s1.split("=")[1].split("\\(");
            String[] splittedS2 = s2.split("=")[1].split("\\(");
            return splittedS1[0].compareTo(splittedS2[0]);
        }
    }

}
