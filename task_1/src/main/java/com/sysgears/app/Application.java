package com.sysgears.app;

import com.sysgears.app.config.AppConfig;
import com.sysgears.core.builder.impl.ResultBuilderImpl;
import com.sysgears.core.converter.impl.DistanceConverterImpl;
import com.sysgears.core.dto.converter.FactorDto;
import com.sysgears.core.dto.io.InputDataDto;
import com.sysgears.core.dto.io.OutputDataDto;
import com.sysgears.core.extractor.impl.TaskExtractorImpl;
import com.sysgears.core.io.impl.IoImpl;
import com.sysgears.core.mapper.MapperContainer;
import com.sysgears.core.mapper.impl.ConverterMapperImpl;
import com.sysgears.core.mapper.impl.FactorMapperImpl;
import com.sysgears.core.validator.impl.InputDataValidatorImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Application {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void runApp() {
        log.info("Application is starting ...");
        long startTime = System.currentTimeMillis();

        try {
            CompletableFuture
                    .supplyAsync(Application::initAppConfig, executor)
                    .thenCompose(appConfig -> {
                        log.debug("Application configuration loaded successfully");
                        log.debug("Initializing services...");

                        ResultBuilderImpl resultBuilder = new ResultBuilderImpl();
                        IoImpl ioService = new IoImpl(resultBuilder);
                        TaskExtractorImpl taskExtractor = new TaskExtractorImpl();
                        DistanceConverterImpl distanceConverter = new DistanceConverterImpl();

                        return ioService.readFrom(appConfig.getConfigMap().getPathToFile().getInputData())
                                .thenApply(input -> {
                                    log.debug("Input data read successfully");
                                    InputDataDto extracted = taskExtractor.extractData(input);
                                    log.debug("Data extraction completed");
                                    return extracted;
                                })
                                .thenApply(dto -> {
                                    log.debug("Validating input data...");
                                    new InputDataValidatorImpl(appConfig).isValid(dto);
                                    log.debug("Input data validation passed");
                                    return dto;
                                })
                                .thenCompose(in -> {
                                    log.debug("Starting conversion process...");
                                    return processConversion(appConfig, in, ioService, distanceConverter);
                                });
                    })
                    .exceptionally(ex -> {
                        log.error("Processing pipeline failed", ex);
                        throw new CompletionException("Application processing failed", ex);
                    })
                    .whenComplete((result, ex) -> {
                        long duration = System.currentTimeMillis() - startTime;
                        if (ex != null) {
                            log.error("Application completed with errors in '{}' ms", duration);
                        } else {
                            log.info("Application completed successfully in '{}' ms", duration);
                        }
                    })
                    .join();
        } finally {
            shutdownExecutor();
        }
    }

    private static CompletableFuture<Void> processConversion(AppConfig appConfig,
                                                             InputDataDto in,
                                                             IoImpl ioService,
                                                             DistanceConverterImpl distanceConverter) {
        return CompletableFuture
                .supplyAsync(() -> {
                    log.debug("Creating mapper container...");
                    MapperContainer mapperContainer = new MapperContainer(
                            new FactorMapperImpl(appConfig),
                            new ConverterMapperImpl()
                    );
                    FactorDto factorDto = mapperContainer.getFactorMapper().map(new Object[]{in});
                    log.debug("Factor mapping completed");
                    return new AbstractMap.SimpleEntry<>(mapperContainer, factorDto);
                }, executor)
                .thenApplyAsync(entry -> {
                    log.debug("Starting converter mapping...");
                    MapperContainer mapperContainer = entry.getKey();
                    FactorDto factorDto = entry.getValue();
                    return mapperContainer.getConverterMapper().map(new Object[]{in, factorDto});
                }, executor)
                .thenApplyAsync(converterDto -> {
                    log.debug("Performing distance conversion...");
                    double convertedData = distanceConverter.convert(converterDto);
                    log.debug("Conversion completed. Result: {}", convertedData);
                    return new OutputDataDto(in.getConvert_to(), convertedData);
                }, executor)
                .thenAcceptAsync(dto -> {
                    log.debug("Writing output data...");
                    ioService.doWrite(dto, appConfig.getConfigMap().getPathToFile().getOutputData());
                    log.debug("Output data written successfully");
                }, executor)
                .exceptionally(ex -> {
                    log.error("Conversion processing failed", ex);
                    throw new CompletionException(ex);
                });
    }

    private static void shutdownExecutor() {
        log.debug("Initiating executor service shutdown...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("Forcing executor service shutdown after timeout");
                executor.shutdownNow();
            }
            log.debug("Executor service shutdown completed");
        } catch (InterruptedException e) {
            log.warn("Executor shutdown interrupted", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static AppConfig initAppConfig() {
        log.debug("Initializing application configuration");
        return AppConfig.getInstance();
    }
}
