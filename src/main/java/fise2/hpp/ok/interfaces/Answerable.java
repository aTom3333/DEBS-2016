package fise2.hpp.ok.interfaces;

public interface Answerable {
    public Type getType();

    public enum Type {
        COMMENT, POST
    }
}
