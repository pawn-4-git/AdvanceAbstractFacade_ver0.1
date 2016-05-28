/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parameter;

import java.math.BigInteger;

/**
 *
 * @author pawn4git
 */
public class BigIntegerNamedParameter extends BaseNamedParameter{

    private BigInteger value;

    public BigIntegerNamedParameter(String key,BigInteger value){
        setKey(key);
        this.value=value;
    }
    /**
     * @return the value
     */
    @Override
    public BigInteger getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigInteger value) {
        this.value = value;
    }
    
    
}
