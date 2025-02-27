package xxl.content.intFunctions;

import xxl.content.Content;
import xxl.content.LitString;
import xxl.content.Ref;
import xxl.visitors.Visitor;

public class Concat extends IntFunction {

    public Concat(Content[] refs){
        super(refs);
    }

    public Concat(){}

    /* verificação?  */
    @Override
    public Content computeValue(){
        String concatenation = "";
        boolean quoteFlag = true;

        for(Content content: super.getRefsList()){
            try{
                Content computedContent = null;
                try{
                    Ref ref = (Ref) content;
                    if(!ref.getCell().hasEmptyContent())
                        computedContent = ref.getCell().getContent().getValue();
                }catch(ClassCastException e){
                    return null;
                }

                LitString convertedContent = (LitString) computedContent;
                if(quoteFlag){
                    quoteFlag = false;
                    concatenation+="\'";
                }
                if (convertedContent != null)
                    concatenation += convertedContent.getQuotlessValue();
            }
            catch(ClassCastException e){}
        }
        return new LitString(concatenation);
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return computeValue() + "=CONCAT" +super.toString();
        }
        else{
           return "#VALUE=CONCAT" +super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}