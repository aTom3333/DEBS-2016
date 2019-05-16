package fise2.hpp.ok.interfaces;

public interface Event {
    Type getType();

    long getTS();

    default boolean isPoisonous() {
        return false;
    }

    enum Type {
        COMMENT, FRIENDSHIP, LIKE, POST
    }
}
