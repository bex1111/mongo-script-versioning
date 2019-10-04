package filewriter;

import repository.MSVRepository;
import util.FileHandler;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static util.Constans.*;

public class CsvGenerator {

    private MSVRepository msvRepository;
    private String outputLocation;
    private static final String CSVSEPARATOR = ",";
    private static final String ROWSEPARATOR = "\n";
    private static final String FILENAME = "MVSDocumentation.csv";


    public CsvGenerator(MSVRepository msvRepository, String outputLocation) {
        this.msvRepository = msvRepository;
        this.outputLocation = outputLocation;
        startGenerating(outputLocation);
    }

    private void startGenerating(String outputLocation) {
        String finalString = "";
        List<String> headerOrder = Arrays.asList(FULLNAME, DESCRIPTION, VERSION, INSTALLEDBY, DATE, CHECKSUM, COLLECTIONNAME);
        finalString += generateHeaderText(headerOrder);
        finalString += generateDataLine(headerOrder);
        FileHandler.writeString(finalString, outputLocation + File.separator + LocalDate.now() + FILENAME);
    }

    private String generateHeaderText(List<String> headerOrder) {
        return headerOrder.stream().collect(Collectors.joining(CSVSEPARATOR)) + ROWSEPARATOR;
    }

    private String generateDataLine(List<String> headerOrder) {
        return msvRepository.findAll().stream().map(x ->
                headerOrder.stream()
                        .map(headerName -> x.containsField(headerName) ? x.get(headerName).toString() : null)
                        .filter(headerName -> !isNull(headerName))
                        .collect(Collectors.joining(CSVSEPARATOR))
        ).collect(Collectors.joining(ROWSEPARATOR));
    }
}
