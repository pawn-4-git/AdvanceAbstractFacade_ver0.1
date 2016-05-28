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
public class LongNamedParameter extends BaseNamedParameter{
    private Long value;
    
     public LongNamedParameter(String key,Long value){
        setKey(key);
        this.value=value;
    }
    
    @Override
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
    }
    
    
    
}
