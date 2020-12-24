package q3df.mil.converter;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class ULetter implements Serializable {

    private final String word;

    public static ULetter toUpper(String s){
        return new ULetter(StringUtils.capitalize(s));
    }

}
