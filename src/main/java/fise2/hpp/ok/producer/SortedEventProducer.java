package fise2.hpp.ok.producer;

import fise2.hpp.ok.interfaces.Event;
import fise2.hpp.ok.parsing.*;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.concurrent.BlockingQueue;

public class SortedEventProducer implements Runnable {

    private BlockingQueue<Event> queue;

    private Event[] buffer;

    private AbstractParser[] parsers;

    public SortedEventProducer(BlockingQueue<Event> q, Event.Type[] requestedTypes, Reader[] associatedReaders) {
        queue = q;
        setup(requestedTypes, associatedReaders);
    }

    private void setup(Event.Type[] requestedTypes, Reader[] readers) {
        buffer = new Event[requestedTypes.length];
        parsers = new AbstractParser[requestedTypes.length];

        for (int i = 0; i < requestedTypes.length; i++) {
            setupType(requestedTypes[i], readers[i], i);
        }
    }

    private void setupType(Event.Type type, Reader reader, int index) {
        switch (type) {
            case LIKE:
                parsers[index] = new LikeParser(reader);
                break;
            case COMMENT:
                parsers[index] = new CommentParser(reader);
                break;
            case POST:
                parsers[index] = new PostParser(reader);
                break;
            case FRIENDSHIP:
                parsers[index] = new FriendshipParser(reader);
                break;
        }
    }

    private void prefillBuffer() throws IOException, ParseException {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = parsers[i].getNext();
        }
    }


    private boolean insertAndRefill() throws InterruptedException, IOException, ParseException {
        int index = -1;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != null && (index == -1 || buffer[index].getTS() > buffer[i].getTS())) {
                index = i;
            }
        }
        if (index == -1) // Plus de données, tous les buffers sont vides
        {
            return false;
        }

        queue.put(buffer[index]);

        buffer[index] = parsers[index].getNext();

        return true;
    }


    @Override
    public void run() {
        try {
            prefillBuffer();
            while (insertAndRefill()) ;
        } catch (InterruptedException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
