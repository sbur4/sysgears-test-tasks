package com.sysgears.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysgears.app.config.JacksonConfig;
import com.sysgears.app.config.PathConfig;
import com.sysgears.core.dto.config.ConfigDto;
import com.sysgears.core.dto.io.OutputDataDto;
import com.sysgears.core.filter.FilterContext;
import com.sysgears.core.filter.FilterFactory;
import com.sysgears.core.io.IoFactory;
import com.sysgears.core.io.impl.InputDataReaderImpl;
import com.sysgears.core.io.impl.OutputDataWriterImpl;
import com.sysgears.core.io.impl.PropertiesReaderImpl;
import com.sysgears.core.mapper.MapperFactory;
import com.sysgears.core.mapper.impl.DtoToJsonMapperImpl;
import com.sysgears.core.mapper.impl.OutputDtoMapperImpl;
import com.sysgears.core.mapper.impl.PropertiesMapperImpl;
import com.sysgears.core.model.Root;
import com.sysgears.core.model.user.User;
import com.sysgears.core.parser.ParserFactory;
import com.sysgears.core.parser.impl.InputJsonParserImpl;
import com.sysgears.core.parser.impl.PropertiesParserImpl;
import com.sysgears.core.sorter.SortContext;
import com.sysgears.core.sorter.SortFactory;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Application {

    public static void runApp() {
        log.info("Application is starting ...");
        long startTime = System.currentTimeMillis();

        JacksonConfig jacksonConfig = initJacksonConfig();
        IoFactory ioFactory = initIoFactory();
        ParserFactory parserFactory = initParserFactory(jacksonConfig.getObjectMapper());
        MapperFactory mapperFactory = initMapperFactory(jacksonConfig.getObjectMapper());
        ConfigDto configDto = initConfigDto(ioFactory.getPropertiesReader(),
                parserFactory.getPropertiesMapper(), mapperFactory.getPropertiesMapper());

        log.debug("Reading input data from path: {}", configDto.getInputData());
        Byte[] inputJsonData = ioFactory.getInputDataReader().readDataByPath(configDto.getInputData());
        log.debug("Input JSON data read successfully.");

        log.debug("Parsing input JSON data...");
        Root root = parserFactory.getInputJsonParser().parseData(inputJsonData);
        log.debug("Parsed JSON data into Root object: {}", root);

        log.debug("Applying filters on parsed data...");
        FilterContext filterContext = initDefaultFilterContext();
        List<User> filteredUsers = filterContext.filter(root.getData().getData(), root.getCondition());
        log.debug("Filtered users: {}", filteredUsers);

        log.debug("Sorting filtered data...");
        SortContext sortContext = initDefaultSortContext();
        List<User> sortedResult = sortContext.sort(filteredUsers, root.getCondition());
        log.debug("Sorted result: {}", sortedResult);

        OutputDataDto outputDataDto = mapperFactory.getOutputDtoMapper().mapData(sortedResult);
        String prettyJson = mapperFactory.getDtoToJsonMapper().mapData(outputDataDto);

        log.debug("Writing output JSON to path: {}", configDto.getOutputData());
        ioFactory.getOutputDataWriter().readDataByPath(configDto.getOutputData(), prettyJson);
        log.debug("Successfully wrote output JSON.");

        long duration = System.currentTimeMillis() - startTime;
        log.info("Application completed with errors in '{}' ms", duration);
    }

    private static SortContext initDefaultSortContext() {
        return SortFactory.createDefaultSortContext();
    }

    private static FilterContext initDefaultFilterContext() {
        return FilterFactory.createDefaultFilterContext();
    }

    private static ConfigDto initConfigDto(PropertiesReaderImpl propertiesReader, PropertiesParserImpl propertiesMapper,
                                           PropertiesMapperImpl propertiesMapper1) {
        return PathConfig.initProperties(propertiesReader, propertiesMapper, propertiesMapper1);
    }

    private static MapperFactory initMapperFactory(ObjectMapper objectMapper) {
        return new MapperFactory(new PropertiesMapperImpl(), new OutputDtoMapperImpl(), new DtoToJsonMapperImpl(objectMapper));
    }

    private static ParserFactory initParserFactory(ObjectMapper objectMapper) {
        return new ParserFactory(new PropertiesParserImpl(), new InputJsonParserImpl(objectMapper));
    }

    private static IoFactory initIoFactory() {
        return new IoFactory(new PropertiesReaderImpl(), new InputDataReaderImpl(),
                new OutputDataWriterImpl());
    }

    private static JacksonConfig initJacksonConfig() {
        return new JacksonConfig();
    }
}
