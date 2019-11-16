package msv.validator.dto;

public enum FileType {
    JSON("Json"), JS("Js");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
