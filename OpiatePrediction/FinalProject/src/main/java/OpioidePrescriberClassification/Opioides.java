/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpioidePrescriberClassification;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.ConstantValueEncoder;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;

/**
 *
 * @author vishakha
 */
public class Opioides {

    public static final int FEATURES = 14;
    private static final ConstantValueEncoder interceptEncoder = new ConstantValueEncoder("intercept");
    private static final FeatureVectorEncoder featureEncoder = new StaticWordValueEncoder("feature");
//    private DenseVector vector = new DenseVector(13);
    private DenseVector vector;
    private Map<String, String> fields = new LinkedHashMap<>();

    public Opioides(Iterable<String> fieldNames, Iterable<String> values) {
        vector = new DenseVector(FEATURES);
        Iterator<String> value = values.iterator();
        interceptEncoder.addToVector("1", vector);
        for (String name : fieldNames) {
            String fieldValue = value.next();
            fields.put(name, fieldValue);
            switch (name) {
                case "DOXYCYCLINE.HYCLATE": {
                    double v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "TRAMADOL.HCL": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "OXYCODONE.ACETAMINOPHEN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "Gender": {
                    featureEncoder.addToVector(name + ":" + fieldValue, 1, vector);
                    break;
                }
                case "HYDROCODONE.ACETAMINOPHEN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "ACETAMINOPHEN.CODEINE": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "AMOX.TR.POTASSIUM.CLAVULANATE": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "CEPHALEXIN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "AZITHROMYCIN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "IBUPROFEN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "CHLORHEXIDINE.GLUCONATE": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "CLINDAMYCIN.HCL": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "AMOXICILLIN": {
                    double v;
                    v = Double.parseDouble(fieldValue);
                    featureEncoder.addToVector(name, Math.log(v), vector);
                    break;
                }
                case "Opioid.Prescriber":
                    break;
                default:
                    break;

            }
        }
    }

    public Vector asVector() {
        return vector;
    }

    public int getTarget() {
        return Integer.parseInt(fields.get("Opioid.Prescriber"));
    }

}
