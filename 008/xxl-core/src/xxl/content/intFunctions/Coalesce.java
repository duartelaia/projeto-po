package xxl.content.intFunctions;

import xxl.content.Content;
import xxl.content.LitString;
import xxl.content.Ref;
import xxl.visitors.Visitor;

public class Coalesce extends IntFunction {

    public Coalesce(Content[] refs){
        super(refs);
    }

    public Coalesce(){}

    /* verificação?  */
    @Override
    public Content computeValue(){
        for(Content content: getRefsList()){
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
                if (convertedContent != null)
                    return new LitString(convertedContent.getStringValue());
            }
            catch(ClassCastException e){}
        }
        return new LitString("\'");
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=COALESCE" +super.toString();
        }
        else{
           return "#VALUE=COALESCE" +super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}