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
public class StringNamedParameter extends BaseNamedParameter{

    private String value;
    
     public StringNamedParameter(String key,String value){
        setKey(key);
        this.value=value;
    }
    
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    
}
