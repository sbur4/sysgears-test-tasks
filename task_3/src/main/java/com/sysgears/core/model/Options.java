package com.sysgears.core.model;

import com.sysgears.core.util.ToStringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Options implements ToStringUtil {

    private String answer;
    private String nextQuestion;

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public String toJson() {
        return ToStringUtil.super.toJson();
    }
}
