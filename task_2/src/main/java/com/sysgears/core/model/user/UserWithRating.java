package com.sysgears.core.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonPropertyOrder({"user", "rating", "disabled"})
@JsonIgnoreProperties(value = {"type"})
public class UserWithRating extends User {

    @JsonProperty("user")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String user;

    @JsonProperty("rating")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer rating;

    @JsonProperty("disabled")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean disabled;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
