/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parameter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 変数名と値を入れると自動的にリストを作成するクラス
 * @author pawn4git
 */
public class ListBaseNamedParameter {
    private List<BaseNamedParameter> list=new ArrayList<>();

    /**
     * @return the list
     */
    public List<BaseNamedParameter> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<BaseNamedParameter> list) {
        this.list = list;
    }
    
    public void addParameter(String name,BigInteger value){
        list.add(new BigIntegerNamedParameter(name,value));
    }
    
    public void addParameter(String name,Boolean value){
        list.add(new BooleanNamedParameter(name,value));
    }
    
    public void addParameter(String name,Date value){
        list.add(new DateNamedParameter(name,value));
    }
    
    public void addParameter(String name,Long value){
        list.add(new LongNamedParameter(name,value));
    }
    
    public void addParameter(String name,String value){
        list.add(new StringNamedParameter(name,value));
    }
    
    public void addParameter(String name,Integer value){
        list.add(new IntegerNamedParameter(name,value));
    }
}
