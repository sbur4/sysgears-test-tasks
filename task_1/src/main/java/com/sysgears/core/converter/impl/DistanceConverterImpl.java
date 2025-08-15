package com.sysgears.core.converter.impl;

import com.sysgears.core.converter.Converter;
import com.sysgears.core.dto.converter.ConverterDto;
import com.sysgears.core.exception.ConverterException;
import com.sysgears.core.util.ConverterUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.BiFunction;

@Slf4j
public class DistanceConverterImpl implements Converter<Double, ConverterDto> {

    private final BiFunction<Double, Double, Double> multiplierToMeter = (d, f) -> d * f;
    private final BiFunction<Double, Double, Double> divisionToFactor = (d, f) -> d / f;

    @Override
    public Double convert(final ConverterDto dto) {
        Objects.requireNonNull(dto, "ConverterDto cannot be null");

        String distanceUnit = dto.getInputDataDto().getDistance().getUnit();
        final double distanceValue = dto.getInputDataDto().getDistance().getValue();
        String convertTo = dto.getInputDataDto().getConvert_to();

        log.debug("Starting distance conversion from '{}' to '{}'", distanceUnit, convertTo);

        try {
            double inMeters = multiplierToMeter.apply(distanceValue, dto.getFactorDto().getFromFactor());
            double result = divisionToFactor.apply(inMeters, dto.getFactorDto().getToFactor());

            return ConverterUtil.roundToHundredths(result);
        } catch (ArithmeticException | NumberFormatException ex) {
            String errorMsg = String.format("Failed to convert distance '%s' from '%s' to '%s'. Error: %s",
                    distanceValue, distanceUnit, convertTo, ex.getMessage());
            log.error(errorMsg, ex);
            throw new ConverterException(errorMsg, ex);
        }
    }
}
