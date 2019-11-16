package com.github.bex1111.filewriter;

import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.util.FileHandler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.bex1111.util.Constans.*;
import static com.github.bex1111.util.Logger.log;
import static java.util.Objects.isNull;

public class CsvGenerator {

    private MSVRepository msvRepository;
    private String outputLocation;
    private static final String CSVSEPARATOR = ",";
    private static final String ROWSEPARATOR = "\n";
    private static final String FILENAME = "_MVSDoc.csv";


    public CsvGenerator(MSVRepository msvRepository, String outputLocation) {
        this.msvRepository = msvRepository;
        this.outputLocation = outputLocation;
        startGenerating();
    }

    private void startGenerating() {
        String finalString = "";
        List<String> headerOrder = Arrays.asList(FULLNAME, DESCRIPTION, VERSION, INSTALLEDBY, DATE, TYPE, CHECKSUM, COLLECTIONNAME);
        finalString += generateHeaderText(headerOrder);
        finalString += generateDataLine(headerOrder);
        writeFile(finalString);
    }

    private String generateHeaderText(List<String> headerOrder) {
        return headerOrder.stream().collect(Collectors.joining(CSVSEPARATOR)) + ROWSEPARATOR;
    }

    private String generateDataLine(List<String> headerOrder) {
        return msvRepository.findAllSortWithVersion().stream().map(x ->
                headerOrder.stream()
                        .map(headerName -> x.containsField(headerName) ? x.get(headerName).toString() : null)
                        .filter(headerName -> !isNull(headerName))
                        .collect(Collectors.joining(CSVSEPARATOR))
        ).collect(Collectors.joining(ROWSEPARATOR));
    }

    private void writeFile(String finalString) {
        String fileName = getFormattedLocalDateTimeNow() + FILENAME;
        log().info("Write file: " + fileName);
        FileHandler.writeString(finalString, outputLocation + File.separator + fileName);
    }

    private String getFormattedLocalDateTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        return now.format(formatter);
    }
}
