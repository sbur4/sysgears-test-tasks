package com.sysgears.app;

import com.google.gson.Gson;
import com.sysgears.app.config.GsonConfig;
import com.sysgears.app.config.PathConfig;
import com.sysgears.core.dto.ConfigDto;
import com.sysgears.core.dto.InputDataDto;
import com.sysgears.core.io.IoFactory;
import com.sysgears.core.io.impl.JsonReaderImpl;
import com.sysgears.core.io.impl.JsonWriterImpl;
import com.sysgears.core.io.impl.PropertiesReaderImpl;
import com.sysgears.core.mapper.MapperFactory;
import com.sysgears.core.mapper.impl.OutputDtoMapperImpl;
import com.sysgears.core.mapper.impl.PropertiesMapperImpl;
import com.sysgears.core.mapper.impl.QuestionMapperImpl;
import com.sysgears.core.model.Question;
import com.sysgears.core.parser.ParserFactory;
import com.sysgears.core.parser.impl.InputParserImpl;
import com.sysgears.core.service.ServiceFactory;
import com.sysgears.core.service.ChainQuestionsService;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@CommonsLog
public class Application {

    @SneakyThrows
    public static void runApp() {
        log.info("Performing an action...");

        log.debug("Initializing configuration objects...");
        PathConfig pathConfig = initPathConfig();
        GsonConfig gsonConfig = initGsonConfig();
        IoFactory ioFactory = initIoFactory(gsonConfig);
        MapperFactory mapperFactory = initMapperFactory();
        ParserFactory parserFactory = initParserFactory(gsonConfig);
        ServiceFactory serviceFactory = initServiceFactory();

        log.debug("Reading properties file from path: " + pathConfig.getPathToProperties());
        Properties appProperties= ioFactory.getPropertiesReader().readDataByPath(pathConfig.getPathToProperties());
        ConfigDto configDto = mapperFactory.getPropertiesMapper().map(appProperties);
        pathConfig.setPathToInputDto(configDto.getInputData());
        pathConfig.setPathToOutputDto(configDto.getOutputData());

        log.debug("Reading input data from path: " + pathConfig.getPathToInputDto());
        Byte[] inputData = ioFactory.getJsonReader().readDataByPath(pathConfig.getPathToInputDto());
        List<InputDataDto> inputDataDtos = parserFactory.getInputParser().parse(inputData);
        List<Question> questions = mapperFactory.getQuestionMapper().map(inputDataDtos);

        log.debug("Finding chains from questions...");
        List<List<Map<String, String>>> allChains = serviceFactory.getChainQuestionsService().findChains(questions);

        log.debug("Writing output data to path: " + pathConfig.getPathToOutputDto());
        Map<String, Object> result = mapperFactory.getOutputDtoMapper().map(allChains);
        ioFactory.getOutputDtoWriter().writeDataByPath(result, pathConfig.getPathToOutputDto());
    }

    private static ServiceFactory initServiceFactory() {
        return new ServiceFactory(new ChainQuestionsService());
    }

    private static ParserFactory initParserFactory(GsonConfig gsonConfig) {
        return new ParserFactory(new InputParserImpl(gsonConfig.getGson()));
    }

    private static MapperFactory initMapperFactory() {
        return new MapperFactory(new PropertiesMapperImpl(), new QuestionMapperImpl(), new OutputDtoMapperImpl());
    }

    private static IoFactory initIoFactory(GsonConfig gsonConfig) {
        return new IoFactory(new PropertiesReaderImpl(), new JsonReaderImpl(), new JsonWriterImpl(gsonConfig.getGson()));
    }

    private static GsonConfig initGsonConfig() {
        return new GsonConfig(new Gson());
    }

    private static PathConfig initPathConfig() {
        return new PathConfig();
    }
}
