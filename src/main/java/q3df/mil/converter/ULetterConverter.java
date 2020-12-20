package q3df.mil.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ULetterConverter implements AttributeConverter<ULetter,String> {

    @Override
    public String convertToDatabaseColumn(ULetter attribute) {
        return attribute.getWord();
    }

    @Override
    public ULetter convertToEntityAttribute(String dbData) {
        return ULetter.toUpper(dbData);
    }
}
