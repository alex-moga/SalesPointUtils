package ru.amogirevskiy.utils.calculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumByDateStatisticProcessor extends AbstractTransactionFileProcessor {

    @Override
    public void calculateStatistics(String inputTransFile, String outputStatFile) {
        Map<LocalDate, Double> totalSumByDateMap;
        try (Stream<String> stream = getInputFileStream(inputTransFile)) {
            totalSumByDateMap = stream.map(parseTransactionInfoFunc()).collect(
                    Collectors.groupingBy(TransactionInfo::getTransDate,
                    Collectors.summingDouble(TransactionInfo::getTransSum))
            );
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't read input office file: %s", inputTransFile));
        }

        List<LocalDate> transDateList =
                totalSumByDateMap.keySet()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());

        try (BufferedWriter outStatFileWriter = getOutputWriter(outputStatFile)) {
            transDateList.stream().forEach(
                    td -> {
                        double sum = totalSumByDateMap.get(td);
                        String fileRow = String.format("%s %s",
                                td.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                formatSum(sum));
                        try {
                            outStatFileWriter.write(fileRow);
                            outStatFileWriter.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(
                                    String.format("Error write row to statistic file: %s", fileRow), e);
                        }
                    }
                );
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't write statistic file: %s", outputStatFile));
        }
    }
}
