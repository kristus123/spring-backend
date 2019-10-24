package com.example.demo.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GoalTypeConverter implements AttributeConverter<GoalType, String> {

    @Override
    public String convertToDatabaseColumn(GoalType goalType) {
        return (goalType == null) ? null : goalType.name();
    }

    @Override
    public GoalType convertToEntityAttribute(String s) {
        return (s == null) ? null : GoalType.valueOf(s);
    }
}