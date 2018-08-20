package ru.amogirevskiy.utils.calculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class AbstractTransactionFileProcessor implements TransactionFileProcessor {
    private Pattern datePattern = Pattern.compile("^(\\d{2}.\\d{2}.\\d{4})");
    private Pattern sumPattern = Pattern.compile("(\\d+\\.\\d+)$");
    private Pattern officePattern = Pattern.compile(":\\d{2} (\\d+)");
    private DateTimeFormatter transDateFormater = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

    public AbstractTransactionFileProcessor() {
        decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
    }

    protected String formatSum(double sum) {
        DecimalFormat df = new DecimalFormat("#0.00", decimalFormatSymbols);
        return df.format(sum);
    }

    protected Stream<String> getInputFileStream(String inputFile) throws IOException {
        return Files.lines(Paths.get(inputFile));
    }

    protected BufferedWriter getOutputWriter(String outputFile) throws IOException {
        return Files.newBufferedWriter(Paths.get(outputFile));
    }

    private String parsePattern(Pattern pattern, String row) {
        Matcher matcher = pattern.matcher(row);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException(
                String.format("Could't parse pattern %s in row %s", pattern, row));
        }
    }

    protected Function<String, TransactionInfo> parseTransactionInfoFunc() {
        return fileLine -> {
            TransactionInfo transInfo = new TransactionInfo();

            String dateItem = parsePattern(datePattern, fileLine);
            try {
                transInfo.setTransDate(LocalDate.parse(dateItem, transDateFormater));
            } catch (DateTimeParseException e) {
                throw new RuntimeException(String.format("Wrong date format %s expected dd.MM.yyyy", dateItem));
            }

            String sumItem = parsePattern(sumPattern, fileLine);
            try {
                transInfo.setTransSum(Float.parseFloat(sumItem));
            } catch (NumberFormatException e) {
                throw new RuntimeException(String.format("Wrong sum format %s expected float value", sumItem));
            }

            String officeItem = parsePattern(officePattern, fileLine);
            try {
                transInfo.setOffice(Integer.valueOf(officeItem));
            } catch (NumberFormatException e) {
                throw new RuntimeException(String.format("Wrong sum format %s expected integer value", officeItem));
            }

            return transInfo;
        };
    }
}
