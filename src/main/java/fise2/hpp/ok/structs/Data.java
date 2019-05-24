package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.utils.CircularList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public CircularList<Perishable> perishables = new CircularList<>();

    public static int MS_PER_DAY = 24 * 60 * 60 * 1000;

    public Map<Integer, User> users = new HashMap<>();

    public Map<Integer, Post> posts = new HashMap<>();

    public Map<Integer, Comment> comments = new HashMap<>();

    public Data() {
    }

    public long lastTS = 0;
    private boolean testExact = true;

    public Post[] oldTop3;

    public void addPost(Post post) {
        posts.put(post.post_id, post);

        expireAt();

        addInCircularAtLastOfSameTS(post);
    }

    private void addInCircularAtLastOfSameTS(Perishable p) {
        //if(perishables.isEmpty()) {
        perishables.addBefore(p);
        if (perishables.size() == 2)
            perishables.advanceBackward();
//            return;
//        }
//
//        int iter = 0;
//        final Perishable first = perishables.curr();
//        while(perishables.curr().getTS() % MS_PER_DAY == lastTS % MS_PER_DAY) {
//            perishables.advanceForward();
//            iter++;
//            if(perishables.curr() == first)
//                break;
//        }
//        perishables.addBefore(p);
//        for(int i = 0; i < iter; i++)
//            perishables.advanceBackward();
    }

    public void addComment(Comment comment) {
        if(comment.answered == null)
            return;

        Post parent = comment.getParentPost();
        if (parent.score <= 0) {
            return; // Si le post est déjà expiré, on ne retient pas le commentaire
        }
        comments.put(comment.comment_id, comment);
        parent.relatedComments.add(comment);

        expireAt();

        addInCircularAtLastOfSameTS(comment);
    }

    public void removePost(Post post) {
        posts.remove(post.post_id);
        int size = perishables.size();
        perishables.markIf(p -> {
            return post.relatedComments.contains(p);
        });
        for (Comment c : post.relatedComments)
            comments.remove(c.comment_id);
    }

    public void expireUntil(long timestamp) {
        if (perishables.isEmpty() || timestamp <= lastTS) {
            lastTS = timestamp;
            return;
        }

        final long noOfFullDay = (timestamp - lastTS) / MS_PER_DAY;

        int iter = 0;

        while (perishables.curr().updateScore(timestamp - 1)) {
            iter++;
            perishables.advanceForward();
        }

        // Remove expired data
        if (noOfFullDay > 0) {
            final int size = perishables.size();

//            for(int i = 0; i < size; i++) {
//                if(perishables.curr() instanceof Post && ((Post) perishables.curr()).getTotalScore() == 0) {
//                    removePost((Post) perishables.curr());
//                } else {
//                    perishables.advanceForward();
//                }
//            }

            perishables.markIf(p -> {
                if (p instanceof Post && ((Post) p).getTotalScore() == 0) {
                    removePost((Post) p);
                    return true;
                }
                return false;
            });

            perishables.swipeMarked();
            lastTS = timestamp;
        } else {

            for (int i = 0; i < iter; i++)
                perishables.advanceBackward();
            for (int i = 0; i < iter; i++) {
                if (perishables.curr() instanceof Post && ((Post) perishables.curr()).getTotalScore() == 0) {
                    removePost((Post) perishables.curr());
                    perishables.markCurrent();
                }
                perishables.advanceForward();
            }

            perishables.swipeMarked();

            lastTS = timestamp;
        }
    }

    public void expireAt() {
        if(perishables.isEmpty() || !testExact) {
            return;
        }

        int iter = 0;

        while (perishables.curr().updateScore(lastTS)) {
            iter++;
            perishables.advanceForward();
        }


        // Remove expired data
        for (int i = 0; i < iter; i++)
            perishables.advanceBackward();
        for (int i = 0; i < iter; i++) {
            if (perishables.curr() instanceof Post && ((Post) perishables.curr()).getTotalScore() == 0) {
                removePost((Post) perishables.curr());
                perishables.removeCurr();
            } else {
                perishables.advanceForward();
            }
        }
    }


    public Top3 getTop3() {
        Top3 top3 = new Top3();

        ArrayList<Post> list = new ArrayList<>(posts.values());

        list.sort((a, b) -> {
            int r = b.getTotalScore() - a.getTotalScore();
            if (r != 0) {
                return r;
            }
            r = (int) (b.getTS() - a.getTS());
            return r;
        });

        if (list.size() > 0) {
            top3.data[0] = new Top3.PostData(list.get(0));
        }
        if (list.size() > 1) {
            top3.data[1] = new Top3.PostData(list.get(1));
        }
        if (list.size() > 2) {
            top3.data[2] = new Top3.PostData(list.get(2));
        }

        return top3;
    }

}
