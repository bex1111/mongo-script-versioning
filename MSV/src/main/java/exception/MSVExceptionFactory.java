package exception;

public class MSVExceptionFactory {

    public static MSVException wrongFileNameFormat() {
        return new MSVException("Wrong file name format! (Example: 0001_name_collectionName.json)");
    }

    public static MSVException wrongVersionFormat() {
        return new MSVException("Wrong version format! (Example: 0001_name_collectionName.json)");
    }
    
    public static MSVException wrongVersionFormat() {
        return new MSVException("Wrong version format! (Example: 0001_name_collectionName.json)");
    }


}
