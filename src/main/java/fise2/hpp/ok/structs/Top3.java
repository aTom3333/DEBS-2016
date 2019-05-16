package fise2.hpp.ok.structs;

public class Top3 {
    public PostData[] posts;
    public long ts;

    @Override
    public String toString() {
        return "";
    }

    public class PostData {
        public int post_id;
        public String user;
        public int score;
        public int commenters;
    }
}
