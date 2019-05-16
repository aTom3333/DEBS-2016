package fise2.hpp.ok.main;

import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Event;
import fise2.hpp.ok.parsing.Linker;
import fise2.hpp.ok.producer.SortedEventProducer;
import fise2.hpp.ok.producer.SortedEventProducerBuilder;
import fise2.hpp.ok.structs.Data;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Query1 {
    public Data data;
    private BlockingQueue<Event> queue;
    private SortedEventProducer producer;

    public Query1(String directory) throws URISyntaxException, FileNotFoundException {
        data = Data.instance();
        queue = new ArrayBlockingQueue<>(40);
        producer = new SortedEventProducerBuilder(queue)
                .requestResource(Event.Type.COMMENT, directory + "/comments.dat")
                .requestResource(Event.Type.POST, directory + "/posts.dat")
                .build();
    }

//    public void loadFullRessources() throws IOException, ParseException {
//        fise2.hpp.ok.eventsIR.Comment commentIR;
//        fise2.hpp.ok.eventsIR.Post postIR;
//        while ((commentIR = commentParser.getNext()) != null)
//            Linker.link(commentIR, data);
//        while ((postIR = postParser.getNext()) != null)
//            Linker.link(postIR, data);
//    }

    public void run() throws InterruptedException {
        Thread producerThread = new Thread(producer);
        producerThread.start();

        Post[] top;

        while (true) {
            Event event = queue.take();
            data.expireUntil(event.getTS());
            Linker.link(event, data);
            Post[] top3 = data.getTop3();

            System.out.println(top3[0]); // TODO Send in writter queue
            System.out.println(top3[1]); // TODO Send in writter queue
            System.out.println(top3[2]); // TODO Send in writter queue
            System.out.println(""); // TODO Send in writter queue
        }
    }

    public void processNaive() {

    }
}
