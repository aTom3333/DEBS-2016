package fise2.hpp.ok.events;

public class Comment implements Event {
    @Override
    public Type getType() {
        return Type.COMMENT;
    }
}
