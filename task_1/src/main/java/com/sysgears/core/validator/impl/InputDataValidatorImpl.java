package com.sysgears.core.validator.impl;

import com.sysgears.app.config.AppConfig;
import com.sysgears.core.dto.io.InputDataDto;
import com.sysgears.core.exception.InputDtoValidationException;
import com.sysgears.core.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class InputDataValidatorImpl implements Validator<InputDataDto> {

    private final AppConfig appConfig;

    @Override
    public void isValid(final InputDataDto inputDataDto) {
        log.debug("Starting validation for input data: {}", inputDataDto);

        List<String> conversationRules = Stream.of(
                        appConfig.getConfigMap().getBaseRules().getDefaultRules(),
                        appConfig.getConfigMap().getAdditionalRules().getExtendedRules())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

        try {
            Validate.notNull(inputDataDto, "Input data DTO cannot be null");
            Validate.notNull(inputDataDto.getDistance(), "Distance data cannot be null");
            Validate.notNull(inputDataDto.getDistance().getValue(), "Distance value cannot be null");

            String distanceUnit = StringUtils.trim(inputDataDto.getDistance().getUnit());
            String convertTo = StringUtils.trim(inputDataDto.getConvert_to());

            Validate.notBlank(distanceUnit, "Distance unit cannot be blank");
            Validate.notBlank(convertTo, "Target conversion unit cannot be blank");

            Validate.isTrue(conversationRules.contains(distanceUnit),
                    "Invalid distance unit: " + distanceUnit + ". Valid units are: " + conversationRules);
            Validate.isTrue(conversationRules.contains(convertTo),
                    "Invalid conversion unit: " + convertTo + ". Valid units are: " + conversationRules);

            log.debug("Input data validation successful for units: {} -> {}", distanceUnit, convertTo);
        } catch (IllegalArgumentException | NullPointerException ex) {
            String errorMsg = "Input validation failed: " + ex.getMessage();
            log.error(errorMsg);
            throw new InputDtoValidationException(errorMsg, ex);
        }
    }
}
