package xxl.visitors;

import xxl.cellNetwork.Cell;
import xxl.content.*;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;

public class RemoveObserverVisitor implements Visitor{
    
    private Cell _currentCell;

    public RemoveObserverVisitor(Cell currentCell){
        _currentCell = currentCell;
    }

    /**
     * Default behaviour of a binary function
     *
     * @param binFunction the binary function
     */
    private void binFunctionBehaviour(BinFunction binFunction){
        binFunction.getArg1().accept(this);
        binFunction.getArg2().accept(this); 
    }

    /**
     * Default behaviour of an interval function
     *
     * @param intFunction the interval function
     */
    private void intFunctionBehaviour(IntFunction intFunction){
        for(int i = 0; i < intFunction.getRefsList().length; i++){
            Content ref = intFunction.getRef(i);
            ref.accept(this);
        }
    }

    public void visit(Content content){}
    public void visit(BinFunction binFunction){}
    public void visit(Add add){
        binFunctionBehaviour(add);
    }
    public void visit(Div div){
        binFunctionBehaviour(div);
    }
    public void visit(Mul mul){
        binFunctionBehaviour(mul);
    }
    public void visit(Sub sub){
        binFunctionBehaviour(sub);
    }
    public void visit(Pow pow){
        binFunctionBehaviour(pow);
    }
    public void visit(IntFunction intFunction){}
    public void visit(Average average){
        intFunctionBehaviour(average);
    }
    public void visit(Coalesce coalesce){
        intFunctionBehaviour(coalesce);
    }
    public void visit(Concat concat){
        intFunctionBehaviour(concat);
    }
    public void visit(Product product){
        intFunctionBehaviour(product);
    }
    public void visit(LitInt litInt){}
    public void visit(LitString litString){}

    public void visit(Ref ref){
        ref.getCell().removeObserver(_currentCell);
    }

}
