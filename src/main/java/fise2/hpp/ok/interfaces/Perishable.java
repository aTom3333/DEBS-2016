package fise2.hpp.ok.interfaces;

public interface Perishable {
    public long getTS();

    /**
     * Decrement the score of this
     *
     * @param amount The amount to decrement
     */
    public void perish(int amount);
}
