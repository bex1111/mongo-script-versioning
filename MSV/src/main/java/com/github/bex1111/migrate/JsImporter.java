package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.github.bex1111.repository.MSVRepository;
import com.mongodb.MongoException;

public class JsImporter extends BaseImporter {

    public JsImporter(MSVRepository msvRepository) {
        super(msvRepository);
    }


    public void executeJsCommand(String fileName, String jsText) {
        try {
            msvRepository.evalScript(jsText);
        } catch (MongoException e) {
            throw MSVExceptionFactory.wrongEvalFail(fileName, e);
        }
    }

}
