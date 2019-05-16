package fise2.hpp.ok.writer;

import fise2.hpp.ok.structs.Top3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.BlockingQueue;

public class Top3Writer implements Runnable {
    private BlockingQueue<Top3> queue;
    private Top3 oldTop3;
    private Path outpath;

    public Top3Writer(BlockingQueue<Top3> q, Path outpath) throws IOException {
        queue = q;
        this.outpath = outpath;
        Files.write(outpath, "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Top3 top3 = queue.take();
                if (top3.isPoisonous()) {
                    break;
                }

                if (!top3.equals(oldTop3)) {
                    try {
                        Files.write(outpath, top3.toString().getBytes(), StandardOpenOption.APPEND, StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
