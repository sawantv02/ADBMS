/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatesByOpioids;

import Top10PrescribedOpioidDrugs.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

/**
 *
 * @author vishakha
 */
public class SupplyAndCost implements Writable{

    private String TotalDrugSupply;
    private String TotalDeath;

    public SupplyAndCost() {
    }

    public SupplyAndCost(String TotalDrugSupply, String TotalDeath) {
        this.TotalDrugSupply = TotalDrugSupply;
        this.TotalDeath = TotalDeath;
    }

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, TotalDrugSupply);
        WritableUtils.writeString(d, TotalDeath);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        TotalDrugSupply = WritableUtils.readString(di);
        TotalDeath = WritableUtils.readString(di);       
    }

    public String getTotalDrugSupply() {
        return TotalDrugSupply;
    }

    public String getTotalDeath() {
        return TotalDeath;
    }

    public String toString() {
        return (new StringBuilder().append(TotalDrugSupply).append("\t").append(TotalDeath).toString());
    }

//    @Override
//    public int compareTo(SupplyAndCost o) {
//        Double result = Double.parseDouble(TotalDrugSupply)-Double.parseDouble(o.TotalDrugSupply);
////        if(result==0){
////            result=Double.parseDouble(TotalDrugCost)-Double.parseDouble(o.TotalDrugCost);
////        }
//        if(result>=0)
//        return 1;
//        else
//        return -1;
//    }

}
