package fise2.hpp.ok.events;

public interface Event {
    public Type getType();

    public enum Type {
        COMMENT, POST, FRIENDSHIP, LIKE
    }
}
