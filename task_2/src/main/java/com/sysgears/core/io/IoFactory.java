package com.sysgears.core.io;

import com.sysgears.core.io.impl.InputDataReaderImpl;
import com.sysgears.core.io.impl.OutputDataWriterImpl;
import com.sysgears.core.io.impl.PropertiesReaderImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IoFactory {

    private final PropertiesReaderImpl propertiesReader;
    private final InputDataReaderImpl inputDataReader;
    private final OutputDataWriterImpl outputDataWriter;
}
