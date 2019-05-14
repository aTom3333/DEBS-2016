package fise2.hpp.ok.eventsIR;

public interface Event {
    public Type getType();

    public enum Type {
        COMMENT, FRIENDSHIP, LIKE, POST
    }
}
