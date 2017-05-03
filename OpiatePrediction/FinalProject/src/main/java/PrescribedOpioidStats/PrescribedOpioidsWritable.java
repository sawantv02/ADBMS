/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrescribedOpioidStats;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

/**
 *
 * @author vishakha
 */
public class PrescribedOpioidsWritable implements Writable {

    private String npi;
    private String drugname;
    private String genericname;
    private String state;
    private String totalDrugSupply;
    private String totalDrugCost;

    public PrescribedOpioidsWritable() {
    }

    public PrescribedOpioidsWritable(String npi, String drugname, String genericname, String state, String totalDrugSupply, String totalDrugCost) {
        this.npi = npi;
        this.drugname = drugname;
        this.genericname = genericname;
        this.state = state;
        this.totalDrugSupply = totalDrugSupply;
        this.totalDrugCost = totalDrugCost;
    }

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, npi);
        WritableUtils.writeString(d, drugname);
        WritableUtils.writeString(d, genericname);
        WritableUtils.writeString(d, state);
        WritableUtils.writeString(d, totalDrugSupply);
        WritableUtils.writeString(d, totalDrugCost);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        npi = WritableUtils.readString(di);
        drugname = WritableUtils.readString(di);
        genericname = WritableUtils.readString(di);
        state = WritableUtils.readString(di);
        totalDrugSupply = WritableUtils.readString(di);
        totalDrugCost = WritableUtils.readString(di);
    }
    
    public String toString() {
        return (new StringBuilder().append(npi).append("\t").append(drugname).append("\t").append(genericname).append("\t").append(state).append("\t").append(totalDrugSupply).append("\t").append(totalDrugCost).toString());
    }

}
