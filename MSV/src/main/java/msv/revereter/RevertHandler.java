package msv.revereter;

import msv.exception.MSVExceptionFactory;
import msv.repository.MSVRepository;

import static java.util.Objects.isNull;
import static msv.util.Constans.FULLNAME;
import static msv.util.Constans.VERSION;
import static msv.util.Logger.log;

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
