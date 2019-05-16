package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.utils.CircularList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private static Data instance = new Data();

    private Data() {
    }

    private static int MS_PER_DAY = 24 * 60 * 60 * 1000;

    public static Data instance() {
        return instance;
    }

    public Map<Integer, User> users = new HashMap<>();

    public Map<Integer, Post> posts = new HashMap<>();

    public Map<Integer, Comment> comments = new HashMap<>();

    public CircularList<Perishable> sortedPosts = new CircularList<>();

    public long lastTS = 0;

    public Post[] oldTop3;

    public void addPost(Post post) {
        posts.put(post.post_id, post);
        sortedPosts.addBefore(post);
    }

    public void addComment(Comment comment) {
        Post parent = comment.getParentPost();
        if (parent.score <= 0) {
            return; // Si le post est déjà expiré, on ne retient pas le commentaire
        }
        comments.put(comment.comment_id, comment);
        parent.relatedComments.add(comment);
    }

    public void removePost(Post post) {
        posts.remove(post.post_id);
        for (Comment c : post.relatedComments)
            comments.remove(c.comment_id);
    }

    public void expireUntil(long timestamp) {
        if (sortedPosts.isEmpty()) {
            lastTS = timestamp;
            return;
        }

        final long noOfFullDay = (timestamp - lastTS) / MS_PER_DAY;
        if (noOfFullDay > 0) {
            sortedPosts.forEach(p -> p.perish((int) noOfFullDay));
        }

        final Perishable first = sortedPosts.curr();

        int iter = 0;

        final long lastDayTS = lastTS % MS_PER_DAY; // Number of ms since midnight
        final long currentDayTS = timestamp % MS_PER_DAY; // Number of ms since midnight

        if (lastDayTS < currentDayTS) {
            if (first.getTS() % MS_PER_DAY > lastDayTS
                    && first.getTS() % MS_PER_DAY < currentDayTS) {
                first.perish(1);
                sortedPosts.advanceForward();
                iter++;
            }
            while (sortedPosts.curr() != first
                    && sortedPosts.curr().getTS() % MS_PER_DAY > lastDayTS
                    && sortedPosts.curr().getTS() % MS_PER_DAY < currentDayTS) {
                sortedPosts.curr().perish(1);
                sortedPosts.advanceForward();
                iter++;
            }
        } else {
            if (first.getTS() % MS_PER_DAY > lastDayTS
                    || first.getTS() % MS_PER_DAY < currentDayTS) {
                first.perish(1);
                sortedPosts.advanceForward();
                iter++;
            }
            while (sortedPosts.curr() != first
                    && (sortedPosts.curr().getTS() % MS_PER_DAY > lastDayTS
                    || sortedPosts.curr().getTS() % MS_PER_DAY < currentDayTS)) {
                sortedPosts.curr().perish(1);
                sortedPosts.advanceForward();
                iter++;
            }
        }

        // Remove expired posts
        for (int i = 0; i < iter; i++)
            sortedPosts.advanceBackward();
        for (int i = 0; i < iter; i++) {
            if (sortedPosts.curr() instanceof Post && ((Post) sortedPosts.curr()).getTotalScore() == 0) {
                removePost((Post) sortedPosts.curr());
                sortedPosts.removeCurr();
            } else {
                sortedPosts.advanceForward();
            }
        }

        lastTS = timestamp;
    }


    public Post[] getTop3() {
        Post[] top = new Post[3];
        int[] scores = {0, 0, 0};

        ArrayList<Post> list = new ArrayList<>(posts.values());

        list.sort((a, b) -> b.getTotalScore() - a.getTotalScore());

        if (list.size() > 0) {
            top[0] = list.get(0);
        }
        if (list.size() > 1) {
            top[1] = list.get(1);
        }
        if (list.size() > 2) {
            top[2] = list.get(2);
        }

        return top;
    }

}
