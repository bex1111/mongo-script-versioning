package exception;

import java.io.IOException;

public class MSVExceptionFactory {

    public static MSVException wrongJsonFileNameFormat() {
        return new MSVException("Wrong file description format! (Example: 0001_name_collectionName.json)");
    }

    public static MSVException wrongJsFileNameFormat() {
        return new MSVException("Wrong file description format! (Example: 0001_name.js)");
    }

    public static MSVException wrongEvalFail(String fileName, Throwable e) {
        return new MSVException("Cannot eval js file: " + fileName, e);
    }


    public static MSVException jsonParseFail(String fileName, Throwable e) {
        return new MSVException("Cannot parse json file: " + fileName, e);
    }

    public static MSVException jsonInsertFail(String fileName, Throwable e) {
        return new MSVException("Cannot insert json. Filename: " + fileName);
    }

    public static MSVException notUniqVersion() {
        return new MSVException("Not uniq file version");
    }

    public static MSVException hashNotEqual(String fileName) {
        return new MSVException("You change file which had already imported! (" + fileName + ")");
    }

    public static MSVException cannotReadFile(String filePath, IOException e) {
        return new MSVException("Cannot read file. (Path: " + filePath + ")", e);
    }

    public static MSVException cannotWriteFile(String filePath, IOException e) {
        return new MSVException("Cannot write file. (Path: " + filePath + ")", e);
    }

    public static MSVException mongoAuthProblem() {
        return new MSVException("Cannot authenticate to MongoDB");
    }

    public static MSVException revertVersionNotExist() {
        return new MSVException("Reverted version is not exist!");
    }


}
