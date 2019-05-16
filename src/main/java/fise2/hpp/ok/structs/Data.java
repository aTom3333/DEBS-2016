package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.utils.CircularList;

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

    public int lastTS = 0;

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
            return;
        }

        final long noOfFullDay = (timestamp - lastTS) / MS_PER_DAY;
        if (noOfFullDay > 0) {
            sortedPosts.forEach(p -> p.perish((int) noOfFullDay));
        }

        final Perishable first = sortedPosts.curr();

        int iter = 0;

        final int lastDayTS = lastTS % MS_PER_DAY; // Number of ms since midnight
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
    }

}
