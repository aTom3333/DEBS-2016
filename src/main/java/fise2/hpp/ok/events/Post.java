package fise2.hpp.ok.events;

public class Post implements Event {
    @Override
    public Type getType() {
        return Type.POST;
    }
}
