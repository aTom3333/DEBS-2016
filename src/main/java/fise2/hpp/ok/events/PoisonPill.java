package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Event;

public class PoisonPill implements Event {
    @Override
    public Type getType() {
        return null;
    }

    @Override
    public long getTS() {
        return 0;
    }

    @Override
    public boolean isPoisonous() {
        return true;
    }
}
