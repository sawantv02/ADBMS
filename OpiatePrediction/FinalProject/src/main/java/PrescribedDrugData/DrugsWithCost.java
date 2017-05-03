/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrescribedDrugData;

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
public class DrugsWithCost implements Writable {

    private String TotalDrugSupply;
    private String TotalDrugCost;

    public DrugsWithCost() {
    }

    public DrugsWithCost(String TotalDrugSupply, String TotalDrugCost) {
        this.TotalDrugSupply = TotalDrugSupply;
        this.TotalDrugCost = TotalDrugCost;
    }

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, TotalDrugSupply);
        WritableUtils.writeString(d, TotalDrugCost);

    }

    public String getTotalDrugSupply() {
        return TotalDrugSupply;
    }

    public String getTotalDrugCost() {
        return TotalDrugCost;
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        TotalDrugSupply = WritableUtils.readString(di);
        TotalDrugCost = WritableUtils.readString(di);
    }

    public String toString() {
        return (new StringBuilder().append(TotalDrugSupply).append("\t").append(TotalDrugCost).toString());
    }

}
