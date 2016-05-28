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
public class IntegerNamedParameter extends BaseNamedParameter{

    private Integer value;
    
     public IntegerNamedParameter(String key,Integer value){
        setKey(key);
        this.value=value;
    }
    
    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    
    
}
