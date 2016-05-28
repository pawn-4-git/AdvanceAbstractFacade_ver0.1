/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parameter;

/**
 *
 * @author pawn4git
 */
public class BooleanNamedParameter extends BaseNamedParameter{
    private Boolean value;
    
     public BooleanNamedParameter(String key,Boolean value){
        setKey(key);
        this.value=value;
    }
    
    @Override
    public Boolean getValue() {
       return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Boolean value) {
        this.value = value;
    }
    
}
