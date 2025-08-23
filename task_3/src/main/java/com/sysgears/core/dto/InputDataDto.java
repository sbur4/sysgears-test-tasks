package com.sysgears.core.dto;

import com.sysgears.core.model.Options;
import com.sysgears.core.util.ToStringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputDataDto implements ToStringUtil {

    private Boolean source;
    private String question;
    private List<Options> options;

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return ToStringUtil.super.toJson();
    }
}
