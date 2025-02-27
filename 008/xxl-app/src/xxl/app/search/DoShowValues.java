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
class DoShowValues extends Command<Spreadsheet> {

    DoShowValues(Spreadsheet receiver) {
        super(Label.SEARCH_VALUES, receiver);
        addStringField("valueToShow", Prompt.searchValue());
    }

    @Override
    protected final void execute() {

        Search search = new SearchValue(stringField("valueToShow"));
        List<String> results = _receiver.search(search);
        
        for(String cell : results){
            _display.popup(cell);
        }
    }

    private class SearchValue extends Search {

        public Content _searchValue;

        public SearchValue(String value){
            try {
                _searchValue = _receiver.parseContent(value);
            }catch(UnrecognizedEntryException e){}
            catch(UnrecognizedFunctionException e){}
        }
        
        public void visit(Content content){
            setOk(content.getValue().equalsValue(_searchValue));
        }
        public void visit(BinFunction binFunction){
            setOk(binFunction.getValue().equalsValue(_searchValue));
        }
        public void visit(Add add){
            setOk(add.getValue().equalsValue(_searchValue));
        }
        public void visit(Div div){
            setOk(div.getValue().equalsValue(_searchValue));
        }
        public void visit(Mul mul){
            setOk(mul.getValue().equalsValue(_searchValue));
        }
        public void visit(Sub sub){
            setOk(sub.getValue().equalsValue(_searchValue));
        }
        public void visit(Pow pow){
            setOk(pow.getValue().equalsValue(_searchValue));
        }
        public void visit(IntFunction intFunction){
            setOk(intFunction.getValue().equalsValue(_searchValue));
        }
        public void visit(Average average){
            setOk(average.getValue().equalsValue(_searchValue));
        }
        public void visit(Coalesce coalesce){
            setOk(coalesce.getValue().equalsValue(_searchValue));
        }
        public void visit(Concat concat){
            setOk(concat.getValue().equalsValue(_searchValue));
        }
        public void visit(Product product){
            setOk(product.getValue().equalsValue(_searchValue));
        }
        public void visit(LitInt litInt){
            setOk(litInt.equalsValue(_searchValue));
        }
        public void visit(LitString litString){
            setOk(litString.equalsValue(_searchValue));
        }
        public void visit(Ref ref){
            setOk(ref.getValue().equalsValue(_searchValue));
        }

    }

}
