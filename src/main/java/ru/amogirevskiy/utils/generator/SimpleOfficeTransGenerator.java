package ru.amogirevskiy.utils.generator;

import ru.amogirevskiy.utils.dictionary.DictionaryReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class SimpleOfficeTransGenerator implements OfficeTransGenerator {
    private String[] dictIdentArray;
    private static final float MIN_SUM = 10000f;
    private static final float MAX_SUM = 100000f;

    public SimpleOfficeTransGenerator(DictionaryReader dictionaryReader) {
        Set<String> dictIdents = dictionaryReader.readDictIds();
        dictIdentArray = dictIdents.stream().toArray(String[]::new);
    }

    public void genSalesPointTransactionFile(String outputTransFileName, int rowCount) {
        LocalDateTime today = LocalDateTime.now();

        System.out.println(String.format("Generating output file %s, row count: %d", outputTransFileName, rowCount));
        Path path = Paths.get(outputTransFileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            for(int opnum = 1; opnum <= rowCount; opnum++) {
                LocalDateTime transDate = RandomUtils.generateRandomDate(today.minusYears(1), today);
                String officeName = dictIdentArray[RandomUtils.generateRandomInt(dictIdentArray.length)];
                float transSum = RandomUtils.generateRandomSum(MIN_SUM, MAX_SUM);

                if(opnum > 1) {
                    writer.newLine();
                }
                String transRow = String.format("%s %s %d %s",
                        transDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")),
                        officeName, opnum, transSum);
                writer.write(transRow);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Can't generate sales point file", ioe);
        }
        System.out.println("Done");
    }
}
