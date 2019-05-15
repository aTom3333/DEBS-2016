package fise2.hpp.ok;

import fise2.hpp.ok.interfaces.Event;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventQueue {
    boolean[] noMore = new boolean[Event.Type.values().length];
    Event[] buffer = new Event[Event.Type.values().length];
    private BlockingQueue<Event> queue = new ArrayBlockingQueue<>(40);

    //public
}
