/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecializedByState;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 *
 * @author vishakha
 */
public class GroupingStateComparator extends WritableComparator {

    protected GroupingStateComparator() {
        super(SpecialityDetailsWritable.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        SpecialityDetailsWritable cw1 = (SpecialityDetailsWritable) a;
        SpecialityDetailsWritable cw2 = (SpecialityDetailsWritable) b;

        return cw1.getState().compareTo(cw2.getState());
    }

}
