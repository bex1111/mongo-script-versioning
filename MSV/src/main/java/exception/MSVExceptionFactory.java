package exception;

public class MSVExceptionFactory {

    public static MSVException wrongJsonFileNameFormat() {
        return new MSVException("Wrong file name format! (Example: 0001_name_collectionName.json)");
    }

    public static MSVException wrongJsFileNameFormat() {
        return new MSVException("Wrong file name format! (Example: 0001_name.js)");
    }

    public static MSVException wrongVersionFormat() {
        return new MSVException("Wrong version format, 4 digit is acceptable! (Example: 0001)");
    }

    public static MSVException mongoAuthProblem() {
        return new MSVException("Cannot authenticate to MongoDB");
    }


}
