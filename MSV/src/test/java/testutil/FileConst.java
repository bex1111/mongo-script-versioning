package testutil;

import lombok.experimental.UtilityClass;
import validator.dto.FileJsDto;
import validator.dto.FileJsonDto;

@UtilityClass
public class FileConst {

    public static FileJsDto F0001 = FileJsDto.builder().fileName("0001_test.js").description("test").version("0001").build();
    public static FileJsonDto F0002 = FileJsonDto.builder().fileName("0002_test_test.json").description("test").version("0002").collectionName("test").build();
    public static FileJsDto F0003 = FileJsDto.builder().fileName("0003_wrongtest.js").description("wrongtest").version("0003").build();
    public static FileJsonDto F0004 = FileJsonDto.builder().fileName("0004_testwrong_test.json").description("testwrong").version("0004").collectionName("test").build();
    public static FileJsonDto FAAAA2 = FileJsonDto.builder().fileName("AAAA2_test1_test.json").description("test1").version("AAAA2").collectionName("test").build();
    public static FileJsonDto FAAAB2 = FileJsonDto.builder().fileName("AAAB2_test2_test.json").description("test2").version("AAAB2").collectionName("test").build();
    public static FileJsDto FAAAA3 = FileJsDto.builder().fileName(" AAAA3_test.js").description("test").version("0003").build();

}
