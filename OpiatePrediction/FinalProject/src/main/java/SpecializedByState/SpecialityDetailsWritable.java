/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecializedByState;

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
public class SpecialityDetailsWritable implements WritableComparable<SpecialityDetailsWritable> {

    private String npi;
    private String state;
    private String speciality;

    public SpecialityDetailsWritable() {
    }

    public SpecialityDetailsWritable(String npi, String state, String speciality) {
        this.npi = npi;
        this.state = state;
        this.speciality = speciality;

    }

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, npi);
        WritableUtils.writeString(d, state);
        WritableUtils.writeString(d, speciality);

    }

    @Override
    public void readFields(DataInput di) throws IOException {
        npi=WritableUtils.readString(di);
        state = WritableUtils.readString(di);
        speciality = WritableUtils.readString(di);

    }

    @Override
    public int compareTo(SpecialityDetailsWritable o) {
        int result=speciality.compareTo(o.speciality);
        
        if(result==0){
            result=state.compareTo(o.state);
        }
        return result;
    }

    public String getNpi() {
        return npi;
    }

    public String getState() {
        return state;
    }

    public String getSpeciality() {
        return speciality;
    }


    @Override
    public String toString() {
         return (new StringBuilder().append(state).append("\t").append(speciality).append("\t").toString());
    }
    
    

}
