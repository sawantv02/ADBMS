/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpioidePrescriberClassification;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.io.Resources;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishakha
 */
public class Parser implements Iterable<Opioides> {

    private String resourceName;
    private final Splitter onSemi = Splitter.on(",").trimResults(CharMatcher.anyOf("\""));

    public Parser(String resourceName) {
        String filePath = new File("").getAbsolutePath();

        System.out.println(filePath);
        this.resourceName = filePath+resourceName;
    }

    @Override
    public Iterator<Opioides> iterator() {
        try {
            return new AbstractIterator<Opioides>() {

                BufferedReader input
                        = new BufferedReader(new FileReader(resourceName));

                Iterable<String> fieldNames = onSemi.split(input.readLine());

                @Override
                protected Opioides computeNext() {
                    try {
                        String line = input.readLine();
                        if (line == null) {
                            return endOfData();
                        }

                        return new Opioides(fieldNames, onSemi.split(line));
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading data", e);
                    }
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Error reading data", e);
        }
    }

}
