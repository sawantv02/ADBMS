/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpioidePrescriberClassification;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.common.RandomUtils;

/**
 *
 * @author vishakha
 */
public class Driver {

    public static final int NUM_CATEGORIES = 2;

    public static void main(String args[]) throws Exception {
        List<Opioides> calls = Lists.newArrayList(new Parser("/input1/try.csv"));
        double heldOutPercentage = 0.10;
//        for (int run = 0; run < 20; run++) 
        {
//            Random random = RandomUtils.getRandom();
            Collections.shuffle(calls);
            int cutoff = (int) (heldOutPercentage * calls.size());
            List<Opioides> test = calls.subList(0, cutoff);
            List<Opioides> train = calls.subList(cutoff, calls.size());

            OnlineLogisticRegression lr = new OnlineLogisticRegression(NUM_CATEGORIES, Opioides.FEATURES, new L1())
                    .learningRate(1)
                    .alpha(1)
                    .lambda(0.000001)
                    .stepOffset(10000)
                    .decayExponent(0.2);
            
//            for (int pass = 0; pass < 2 ; pass++)
            {
                System.err.println("pass");
                for (Opioides observation : train){
                    lr.train(observation.getTarget(), observation.asVector());
                }
//                if (pass % 2 == 0) 
                {
                    Auc eval = new Auc(0.5);
                    for (Opioides testCall : test){
                        eval.add(testCall.getTarget(), lr.classifyScalar(testCall.asVector()));
                    }
                    System.out.printf("%d, %.4f, %.4f\n",1, lr.currentLearningRate(), eval.auc());
                }
            }
        }
    }

}
