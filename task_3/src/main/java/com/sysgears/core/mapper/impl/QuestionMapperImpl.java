package com.sysgears.core.mapper.impl;

import com.sysgears.core.dto.InputDataDto;
import com.sysgears.core.mapper.BaseMapper;
import com.sysgears.core.model.Question;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuestionMapperImpl implements BaseMapper<List<Question>, List<InputDataDto>> {

    @Override
    public List<Question> map(List<InputDataDto> inputDataDtos) {
        if (CollectionUtils.isEmpty(inputDataDtos)) {
            return new ArrayList<>();
        }

        List<Question> questions = inputDataDtos.stream()
                .map(i -> Question.builder()
                        .source(i.getSource())
                        .question(Objects.requireNonNull(i.getQuestion()))
                        .options(Objects.requireNonNull(i.getOptions()))
                        .build())
                .collect(Collectors.toList());

        return questions;
    }
}
