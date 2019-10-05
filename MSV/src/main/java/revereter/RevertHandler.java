package revereter;

import repository.MSVRepository;

import static java.util.Objects.isNull;
import static util.Constans.FULLNAME;
import static util.Constans.VERSION;
import static util.Logger.log;

public class RevertHandler {

    MSVRepository msvRepository;
    String revertVersion;

    public RevertHandler(MSVRepository msvRepository, String revertVersion) {
        this.msvRepository = msvRepository;
        this.revertVersion = revertVersion;
        checkVersionExist();
    }

    private void checkVersionExist() {
        if (msvRepository.versionExist(revertVersion)) {
            startRevert();
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
