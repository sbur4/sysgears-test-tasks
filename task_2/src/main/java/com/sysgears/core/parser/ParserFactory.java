package com.sysgears.core.parser;

import com.sysgears.core.parser.impl.InputJsonParserImpl;
import com.sysgears.core.parser.impl.PropertiesParserImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParserFactory {

    private final PropertiesParserImpl propertiesMapper;
    private final InputJsonParserImpl inputJsonParser;

}
