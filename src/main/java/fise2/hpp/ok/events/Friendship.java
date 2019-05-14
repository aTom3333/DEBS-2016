package fise2.hpp.ok.events;

public class Friendship implements Event {
    @Override
    public Type getType() {
        return Type.FRIENDSHIP;
    }
}
