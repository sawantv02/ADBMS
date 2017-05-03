/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Top10PrescribedOpioidDrugs;

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
    private String TotalDrugCost;

    public SupplyAndCost() {
    }

    public SupplyAndCost(String TotalDrugSupply, String TotalDrugCost) {
        this.TotalDrugSupply = TotalDrugSupply;
        this.TotalDrugCost = TotalDrugCost;
    }

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, TotalDrugSupply);
        WritableUtils.writeString(d, TotalDrugCost);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        TotalDrugSupply = WritableUtils.readString(di);
        TotalDrugCost = WritableUtils.readString(di);       
    }

    public String getTotalDrugSupply() {
        return TotalDrugSupply;
    }

    public String getTotalDrugCost() {
        return TotalDrugCost;
    }

    public String toString() {
        return (new StringBuilder().append(TotalDrugSupply).append("\t").append(TotalDrugCost).toString());
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
