/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parameter;

import java.util.Date;

/**
 *
 * @author pawn4git
 */
public class DateNamedParameter extends BaseNamedParameter{
    private Date value;

     public DateNamedParameter(String key,Date value){
        setKey(key);
        this.value=value;
    }
    
    @Override
    public Date getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Date value) {
        this.value = value;
    }
}
