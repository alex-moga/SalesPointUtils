package ru.amogirevskiy.utils;

import ru.amogirevskiy.utils.calculator.SumByDateStatisticProcessor;
import ru.amogirevskiy.utils.calculator.SumByOfficeStatisticProcessor;
import ru.amogirevskiy.utils.calculator.TransactionFileProcessor;
import ru.amogirevskiy.utils.dictionary.DictionaryReader;
import ru.amogirevskiy.utils.dictionary.FileDictionaryReader;
import ru.amogirevskiy.utils.generator.OfficeTransGenerator;
import ru.amogirevskiy.utils.generator.SimpleOfficeTransGenerator;

public class OfficeTransFileGenerator {

    public static void main(String[] args) {
        if(args.length < 3) {
            System.out.println("Usage: java -jar task1.jar offices.txt 90000 operation.txt");
            return;
        }
        String officeDictFile = args[0];
        String outputTransFile= args[2];
        String argValue = null;
        int rowCount;
        try {
            argValue = args[1];
            rowCount = Integer.valueOf(argValue);
        } catch (NumberFormatException ex) {
            throw new RuntimeException(String.format("Wrong value for transaction count: %s", argValue));
        }

        DictionaryReader dictionaryReader = new FileDictionaryReader(officeDictFile);
        OfficeTransGenerator officeTransGenerator = new SimpleOfficeTransGenerator(dictionaryReader);
        officeTransGenerator.genSalesPointTransactionFile(outputTransFile, rowCount);
    }
}
