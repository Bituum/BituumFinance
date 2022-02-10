package bituum.bot.telegrambot.service.impl;

import bituum.bot.telegrambot.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {
    public final String location ="/home/bituum/IdeaProjects/BituumFinance/TelegramBot/src/main/python/candleStick.py";


    public void execute(String toPath, String ticker) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python",location, toPath, ticker);
        processBuilder.redirectErrorStream(true);
        log.warn("START PROCESS");
        Process process = processBuilder.start();
        Thread.sleep(2000);
        int exitCode = process.waitFor();
        log.info("status code: " + exitCode);
    }
}
