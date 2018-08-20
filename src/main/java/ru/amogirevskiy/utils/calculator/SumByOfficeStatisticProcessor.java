package ru.amogirevskiy.utils.calculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumByOfficeStatisticProcessor extends AbstractTransactionFileProcessor {

    @Override
    public void calculateStatistics(String inputTransFile, String outputStatFile) {
        Map<Integer, Double> totalSumByOfficeMap;
        try (Stream<String> stream = getInputFileStream(inputTransFile)) {
            totalSumByOfficeMap = stream.map(parseTransactionInfoFunc()).collect(
                    Collectors.groupingBy(TransactionInfo::getOffice,
                    Collectors.summingDouble(TransactionInfo::getTransSum))
            );
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't read input office file: %s", inputTransFile));
        }

        List<Integer> officeList =
                totalSumByOfficeMap.keySet()
                        .stream()
                        .sorted((ol, o2) -> Integer.compare(o2, ol))
                        .collect(Collectors.toList());

        try (BufferedWriter outStatFileWriter = getOutputWriter(outputStatFile)) {
            officeList.stream().forEach(
                    office -> {
                        double sum = totalSumByOfficeMap.get(office);
                        String fileRow = String.format("%d %s", office, formatSum(sum));
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
