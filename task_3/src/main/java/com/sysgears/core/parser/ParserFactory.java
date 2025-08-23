package com.sysgears.core.parser;

import com.sysgears.core.parser.impl.InputParserImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParserFactory {

    private final InputParserImpl inputParser;
}
