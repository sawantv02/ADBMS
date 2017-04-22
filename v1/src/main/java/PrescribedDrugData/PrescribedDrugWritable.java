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
public class PrescribedDrugWritable implements Writable,WritableComparable<PrescribedDrugWritable>{
    
    private String npi;
    private String drugname;
    private String state;
    private String GenericName;
    private String TotalDrugCost;

    public PrescribedDrugWritable() {
    }
    

    public PrescribedDrugWritable(String npi,String drugname,String state,String GenericName,String TotalDrugCost) {
        this.npi=npi;
        this.drugname=drugname;
        this.state = state;
        this.GenericName = GenericName;
        this.TotalDrugCost = TotalDrugCost;
    }
    
    @Override
    public void write(DataOutput d) throws IOException {
       WritableUtils.writeString(d,npi);
       WritableUtils.writeString(d,drugname);
       WritableUtils.writeString(d,state);
       WritableUtils.writeString(d,GenericName);
       WritableUtils.writeString(d,TotalDrugCost);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
       npi=WritableUtils.readString(di);
       drugname=WritableUtils.readString(di);
       state = WritableUtils.readString(di);
       GenericName=WritableUtils.readString(di);
       TotalDrugCost=WritableUtils.readString(di);
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }
    
    

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGenericName() {
        return GenericName;
    }

    public void setGenericName(String GenericName) {
        this.GenericName = GenericName;
    }

    public String getTotalDrugCost() {
        return TotalDrugCost;
    }

    public void setTotalDrugCost(String TotalDrugCost) {
        this.TotalDrugCost = TotalDrugCost;
    }
    
    
    public String toString() {
        return (new StringBuilder().append(npi).append("\t").append(drugname).append("\t").append(state).append("\t").append(GenericName).append("\t").append(TotalDrugCost).toString());
    }

    @Override
    public int compareTo(PrescribedDrugWritable o) {
        int result=npi.compareTo(o.npi);
        
        if(result==0){
            result=drugname.compareTo(o.drugname);
        }
        return result;
    }
    
    
}
