package com.sysgears.core.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserWithInfo.class, name = "info"),
        @JsonSubTypes.Type(value = UserWithRating.class, name = "rating")
})
@JsonIgnoreProperties(value = {"type"})
public abstract class User {

    @JsonIgnore
    public String getType() {
        return null;
    }
}
