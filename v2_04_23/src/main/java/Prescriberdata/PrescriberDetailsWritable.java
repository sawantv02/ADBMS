/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prescriberdata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

/**
 *
 * @author vishakha
 */
public class PrescriberDetailsWritable implements Writable{

    private String last_name;
    private String first_name;
    private String state;
    private String speciality;
    private String creds;
    private String gender;

    public PrescriberDetailsWritable() {
    }
    
    

    public PrescriberDetailsWritable(String last_name, String first_name, String state, String speciality, String creds, String gender) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.state = state;
        this.speciality = speciality;
        this.creds = creds;
        this.gender = gender;
    } 

    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d,last_name);
        WritableUtils.writeString(d,first_name);
        WritableUtils.writeString(d,state);
        WritableUtils.writeString(d,speciality);
        WritableUtils.writeString(d,creds);
        WritableUtils.writeString(d,gender);     
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        last_name = WritableUtils.readString(di);
        first_name = WritableUtils.readString(di);
        state = WritableUtils.readString(di);
        speciality = WritableUtils.readString(di);
        creds = WritableUtils.readString(di);
        gender = WritableUtils.readString(di);
    }
    
    public String toString() {
        return (new StringBuilder().append(last_name).append("\t").append(first_name).append("\t").append(state).append("\t").append(speciality).append("\t").append(creds).append("\t").append(gender).toString());
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCreds() {
        return creds;
    }

    public void setCreds(String creds) {
        this.creds = creds;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    
    
}
