package com.sysgears.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"data", "condition"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Root {

    @JsonProperty("data")
    @JsonUnwrapped
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    Datas data;

    @JsonProperty("condition")
    Condition condition;
}
