package ru.amogirevskiy.utils.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDictionaryReader implements DictionaryReader {
    private String dictFileName;

    public FileDictionaryReader(String dictFileName) {
        this.dictFileName = dictFileName;
    }

    public Set<String> readDictIds() {
        try {
            Pattern dictNumPattern = Pattern.compile("(\\d+)$");
            Stream<String> lines = Files.readAllLines(Paths.get(dictFileName)).stream();
            Set<String> out = lines.map(dictNumPattern::matcher)
                    .filter(Matcher::find)
                    .map(matcher -> matcher.group(1))
                    .distinct()
                    .collect(Collectors.toSet());

            return out;
        } catch(IOException ioe) {
            throw new RuntimeException(String.format("Error reading dictionary file: %s", dictFileName), ioe);
        }
    }
}
