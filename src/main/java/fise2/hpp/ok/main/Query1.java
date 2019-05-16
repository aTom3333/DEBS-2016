package fise2.hpp.ok.main;

import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Event;
import fise2.hpp.ok.parsing.Linker;
import fise2.hpp.ok.producer.SortedEventProducer;
import fise2.hpp.ok.producer.SortedEventProducerBuilder;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.structs.Top3;
import fise2.hpp.ok.utils.Utils;
import fise2.hpp.ok.writer.Top3Writer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Query1 {
    public Data data;
    private BlockingQueue<Event> queue;
    private BlockingQueue<Top3> top3queue;
    private SortedEventProducer producer;
    private Top3Writer top3writer;

    public Query1(String directory, String outputFile) throws URISyntaxException, IOException {
        data = new Data();
        queue = new ArrayBlockingQueue<>(40);
        top3queue = new ArrayBlockingQueue<Top3>(40);
        producer = new SortedEventProducerBuilder(queue)
                .requestResource(Event.Type.COMMENT, directory + "/comments.dat")
                .requestResource(Event.Type.POST, directory + "/posts.dat")
                .build();
        top3writer = new Top3Writer(top3queue, Paths.get(Utils.getJARPath().toString(), outputFile));
    }

    public void run() throws InterruptedException {
        //production
        Thread producerThread = new Thread(producer);
        producerThread.start();

        // écriture
        Thread writerThread = new Thread(top3writer);
        writerThread.start();

        Post[] top;

        while (true) {
            Event event = queue.take();
            if (event.isPoisonous()) {
                break;
            }
            data.expireUntil(event.getTS());
            Linker.link(event, data);
            //data.expireAt();
            Top3 top3 = data.getTop3();
            top3.ts = event.getTS();
            top3queue.put(top3);
        }
        top3queue.put(Top3.poisonPill());


        producerThread.join();
        writerThread.join();
    }
}
