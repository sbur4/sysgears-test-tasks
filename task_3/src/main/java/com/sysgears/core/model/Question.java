package com.sysgears.core.model;

import com.sysgears.core.util.ToStringUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Question  implements ToStringUtil{

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
