package com.sysgears.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"exclude", "include", "sort_by"})
public class Condition {

    @Singular("excludeEntry")
    @JsonProperty("exclude")
    public List<Map<String, Object>> exclude;

    @Singular("includeEntry")
    @JsonProperty("include")
    public List<Map<String, Object>> include;

    @Singular("sortByField")
    @JsonProperty("sort_by")
    public List<String> sortBy;
}
