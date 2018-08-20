package ru.amogirevskiy.utils;

import ru.amogirevskiy.utils.calculator.SumByDateStatisticProcessor;
import ru.amogirevskiy.utils.calculator.SumByOfficeStatisticProcessor;
import ru.amogirevskiy.utils.calculator.TransactionFileProcessor;

public class StatisticTransFileGenerator {
    public static void main(String[] args) {
        if(args.length < 3) {
            System.out.println("Usage: java -jar task2.jar operations.txt " +
                    "sums-by-dates.txt sums-by-offices.txt");
            return;
        }
        String inputTransFile= args[0];
        String outSumsByDateFile = args[1];
        String outSumsByOfficesFile = args[2];

        SumByDateStatisticProcessor sumByDateFileProcessor = new SumByDateStatisticProcessor();
        sumByDateFileProcessor.calculateStatistics(inputTransFile, outSumsByDateFile);

        TransactionFileProcessor sumByOfficeFileProcessor = new SumByOfficeStatisticProcessor();
        sumByOfficeFileProcessor.calculateStatistics(inputTransFile, outSumsByOfficesFile);
    }
}
