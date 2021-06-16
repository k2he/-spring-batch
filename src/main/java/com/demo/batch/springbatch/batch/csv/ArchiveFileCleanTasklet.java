package com.demo.batch.springbatch.batch.csv;

import com.demo.batch.springbatch.config.AppProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

/**
 * This Tasklet delete file older than certain days.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ArchiveFileCleanTasklet implements Tasklet {

    @NonNull
    private AppProperties appProperties;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        File targetDir = new File(appProperties.getReport().getArchivePath());

        if (targetDir.exists()) {
            LocalDate today = LocalDate.now();
            LocalDate eailer = today.minusDays(appProperties.getReport().getArchivePeriodInDays());
            Date threshold = Date.from(eailer.atStartOfDay(ZoneId.systemDefault()).toInstant());
            AgeFileFilter filter = new AgeFileFilter(threshold);

            Iterator<File> filesToDelete = FileUtils.iterateFiles(targetDir, filter, null);
            while (filesToDelete.hasNext()) {
                try {
                    filesToDelete.next().delete();
                } catch (Exception e) {
                    log.error("Not able to delete file.", e);
                }
            }
        }

        return RepeatStatus.FINISHED;
    }
}
