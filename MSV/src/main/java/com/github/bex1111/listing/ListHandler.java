package com.github.bex1111.listing;

import com.github.bex1111.repository.MSVRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.bex1111.util.Constans.*;
import static com.github.bex1111.util.Logger.log;
import static java.util.Objects.isNull;

public class ListHandler {

    private MSVRepository msvRepository;
    private static final String CSVSEPARATOR = " | ";


    public ListHandler(MSVRepository msvRepository) {
        this.msvRepository = msvRepository;
        startListing();
    }


    private void startListing() {
        List<String> headerOrder = Arrays.asList(FULLNAME, DESCRIPTION, VERSION, INSTALLEDBY, DATE, TYPE, CHECKSUM, COLLECTIONNAME);
        generateHeaderText(headerOrder);
        generateDataLine(headerOrder);

    }

    private void generateHeaderText(List<String> headerOrder) {
        log().info("");
        log().info(headerOrder.stream().collect(Collectors.joining(CSVSEPARATOR)));
    }

    private void generateDataLine(List<String> headerOrder) {
        msvRepository.findAllSortWithVersion().forEach(x -> {
                    log().info("");
                    log().info(headerOrder.stream()
                            .map(headerName -> x.containsField(headerName) ? x.get(headerName).toString() : null)
                            .filter(headerName -> !isNull(headerName))
                            .collect(Collectors.joining(CSVSEPARATOR)));
                }
        );
        log().info("");
    }

}
