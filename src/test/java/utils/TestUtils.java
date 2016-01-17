package utils;


import java.util.Arrays;

public class TestUtils {

    public static String createStringWithLength(Integer length){
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, '*');
            return new String(array);
        }else {
            return "";
        }

    }

}
