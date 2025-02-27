package xxl.content.intFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.content.Ref;
import xxl.visitors.Visitor;

public class Average extends IntFunction {

    public Average(Content[] refs){
        super(refs);
    }

    public Average(){}

    @Override
    public Content computeValue(){

        int sum=0;

        for(Content content: getRefsList()){
            Content computedContent=null;

            try{
                Ref ref = (Ref) content;
                if(!ref.getCell().hasEmptyContent())
                    computedContent = ref.getCell().getContent().getValue();
            }catch(ClassCastException e){
                return null;
            }

            if (computedContent == null)
                return null;

            LitInt convertedContent;
            
            try{
                convertedContent = (LitInt) computedContent;
            }catch(ClassCastException e){
                return null;
            }

            sum += convertedContent.getIntegerValue();
        }
        return new LitInt(sum/super.getRefsList().length);
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=AVERAGE" +super.toString();
        }
        else{
           return "#VALUE=AVERAGE" +super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    
}
