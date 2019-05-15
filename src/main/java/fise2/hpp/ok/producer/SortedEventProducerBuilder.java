package fise2.hpp.ok.producer;

import fise2.hpp.ok.interfaces.Event;
import fise2.hpp.ok.utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class SortedEventProducerBuilder {
    private BlockingQueue<Event> queue;
    private ArrayList<Event.Type> requestedTypes = new ArrayList<>();
    private ArrayList<Reader> associatedReaders = new ArrayList<>();

    SortedEventProducerBuilder(BlockingQueue<Event> q) {
        queue = q;
    }

    public SortedEventProducerBuilder requestReader(Event.Type type, Reader reader) {
        requestedTypes.add(type);
        associatedReaders.add(reader);
        return this;
    }

    public SortedEventProducerBuilder requestString(Event.Type type, String string) {
        return requestReader(type, new StringReader(string));
    }

    public SortedEventProducerBuilder requesFile(Event.Type type, String filename) throws FileNotFoundException {
        return requestReader(type, new FileReader(filename));
    }

    public SortedEventProducerBuilder requestResource(Event.Type type, String resource) throws URISyntaxException, FileNotFoundException {
        return requesFile(type, Utils.pathToResource(resource));
    }

    public SortedEventProducer build() {
        return new SortedEventProducer(queue, requestedTypes.toArray(Event.Type[]::new), associatedReaders.toArray(Reader[]::new));
    }

}


