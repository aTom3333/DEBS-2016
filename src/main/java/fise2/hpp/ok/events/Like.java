package fise2.hpp.ok.events;

public class Like implements Event {
    @Override
    public Type getType() {
        return Type.LIKE;
    }
}
