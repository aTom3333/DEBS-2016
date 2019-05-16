package fise2.hpp.ok.interfaces;

public interface Event {
    public Type getType();

    public long getTS();

    public enum Type {
        COMMENT, FRIENDSHIP, LIKE, POST
    }
}
