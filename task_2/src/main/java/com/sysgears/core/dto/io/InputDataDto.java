package com.sysgears.core.dto.io;

import com.sysgears.core.model.Condition;
import com.sysgears.core.model.Datas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@AllArgsConstructor
public class InputDataDto {

    private Datas data;
    private Condition condition;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
