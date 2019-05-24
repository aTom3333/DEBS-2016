package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.utils.Utils;

import java.util.Arrays;
import java.util.Objects;

public class Top3 {
    public PostData[] data;
    public long ts;
    private boolean poisonous = false;

    public Top3() {
        data = new PostData[3];
    }

    public static Top3 poisonPill() {
        Top3 top3 = new Top3();
        top3.poisonous = true;
        return top3;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Top3 top3 = (Top3) o;
        return Arrays.equals(data, top3.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.TSToString(ts)).append(',');
        for (int i = 0; i < 3; i++) {
            sb.append(data[i] != null ? data[i].post_id : "-").append(',');
            sb.append(data[i] != null ? data[i].user : "-").append(',');
            sb.append(data[i] != null ? data[i].score : "-").append(',');
            sb.append(data[i] != null ? data[i].commenters : "-");
            if (i < 2) {
                sb.append(',');
            }
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    public static class PostData {
        public long post_id;
        public String user;
        public int score;
        public int commenters;

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            PostData postData = (PostData) o;
            return post_id == postData.post_id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(post_id);
        }

        public PostData(Post p) {
            this.post_id = p.post_id;
            this.user = p.poster.name;
            this.score = p.getTotalScore();
            this.commenters = p.getTotalCommenters();
        }
    }
}
