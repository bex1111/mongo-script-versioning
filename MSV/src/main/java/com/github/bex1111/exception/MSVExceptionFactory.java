package com.github.bex1111.exception;

import java.io.IOException;

public class MSVExceptionFactory {

    public static MSVException wrongJsonFileNameFormat() {
        return new MSVException("Wrong file description format! (Example: 0001_name_collectionName.json)");
    }

    public static MSVException wrongJsFileNameFormat() {
        return new MSVException("Wrong file description format! (Example: 0001_name.js)");
    }

    public static MSVException wrongVersionFormat() {
        return new MSVException("Wrong version format! Version contains only small letter,capital letter and numeric character.");
    }

    public static MSVException wrongEvalFail(String fileName, Throwable e) {
        return new MSVException("Cannot eval js file: " + fileName, e);
    }


    public static MSVException jsonParseFail(String fileName, Throwable e) {
        return new MSVException("Cannot parse json file: " + fileName, e);
    }

    public static MSVException notUniqVersion() {
        return new MSVException("Not uniq file version");
    }

    public static MSVException hashNotEqual(String fileName) {
        return new MSVException("You wanna change file which had already imported! (" + fileName + ")");
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
