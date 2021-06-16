package com.demo.batch.springbatch.batch.csv;

import com.demo.batch.springbatch.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This Tasklet move existing files to archive directory.
 */
@Slf4j
@Component
public class FileArchiveTasklet implements Tasklet {

    private AppProperties appProperties;

    @Autowired
    public FileArchiveTasklet(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        // Create sourceDir if not exist
        Files.createDirectories(Paths.get(appProperties.getReport().getOutputPath()));
        final File sourceDir = new File(appProperties.getReport().getOutputPath());

        File[] files = sourceDir.listFiles();
        if (files != null) {
            Files.createDirectories(Paths.get(appProperties.getReport().getArchivePath()));

            for (File f : files) {
                Path sourcePath = Paths.get(appProperties.getReport().getOutputPath() + f.getName());
                Path destinationPath = Paths.get(appProperties.getReport().getArchivePath() + f.getName());

                try {
                    Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    log.error("Failed to move file.", e);
                }
            }
        }

        return RepeatStatus.FINISHED;
    }
}
