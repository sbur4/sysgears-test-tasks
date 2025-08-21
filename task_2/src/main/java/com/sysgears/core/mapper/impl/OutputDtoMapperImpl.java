package com.sysgears.core.mapper.impl;

import com.sysgears.core.dto.io.OutputDataDto;
import com.sysgears.core.mapper.Mapper;
import com.sysgears.core.model.user.User;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.util.List;

@Log4j2
public class OutputDtoMapperImpl implements Mapper<OutputDataDto, List<User>> {

    @Override
    public OutputDataDto mapData(@Nonnull final List<User> sortedResult) {
        log.info("Mapping sorted user list to OutputDataDto...");
        log.debug("Input sortedResult: {}", sortedResult);

        return new OutputDataDto(sortedResult);
    }
}
