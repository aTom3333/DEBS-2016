package fise2.hpp.ok.writer;

import fise2.hpp.ok.structs.Top3;

import java.util.concurrent.BlockingQueue;

public class Top3Writer implements Runnable {
    private BlockingQueue<Top3> queue;
    private Top3 oldTop3;

    public Top3Writer(BlockingQueue<Top3> q) {
        queue = q;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Top3 top3 = queue.take();

                if(!top3.equals(oldTop3)) {
                    // TODO Write in file
                }


            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
