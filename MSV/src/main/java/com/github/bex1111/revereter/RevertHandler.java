package com.github.bex1111.revereter;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.github.bex1111.repository.MSVRepository;

import static com.github.bex1111.util.Constans.FULLNAME;
import static com.github.bex1111.util.Constans.VERSION;
import static com.github.bex1111.util.Logger.log;
import static java.util.Objects.isNull;

public class RevertHandler {

    MSVRepository msvRepository;
    String revertVersion;

    public RevertHandler(MSVRepository msvRepository, String revertVersion) {
        this.msvRepository = msvRepository;
        this.revertVersion = revertVersion;
        checkVersionExist();
    }

    private void checkVersionExist() {
        if (isNull(revertVersion) || msvRepository.versionExist(revertVersion)) {
            startRevert();
        } else {
            throw MSVExceptionFactory.revertVersionNotExist();
        }
    }

    private void startRevert() {
        if (isNull(revertVersion)) {
            msvRepository.findAll().forEach(x -> {
                log().warn("Revert file: " + x.get(FULLNAME));
                msvRepository.remove(x);
            });
        } else {
            msvRepository.findAll()
                    .stream()
                    .filter(x -> x.get(VERSION).toString().compareTo(revertVersion) == 1)
                    .forEach(x -> {
                        log().warn("Revert file: " + x.get(FULLNAME));
                        msvRepository.remove(x);
                    });
        }

    }
}
