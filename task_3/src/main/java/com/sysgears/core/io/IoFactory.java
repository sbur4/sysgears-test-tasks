package com.sysgears.core.io;

import com.sysgears.core.io.impl.JsonReaderImpl;
import com.sysgears.core.io.impl.JsonWriterImpl;
import com.sysgears.core.io.impl.PropertiesReaderImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IoFactory {

    private final PropertiesReaderImpl propertiesReader;
    private final JsonReaderImpl jsonReader;
    private final JsonWriterImpl outputDtoWriter;
}
