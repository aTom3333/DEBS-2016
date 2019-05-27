package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.utils.CircularList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public CircularList<Perishable> perishables = new CircularList<>();

    public static int MS_PER_DAY = 24 * 60 * 60 * 1000;

    public Map<Long, User> users = new HashMap<>();

    public Map<Long, Post> posts = new HashMap<>();

    public Map<Long, Comment> comments = new HashMap<>();

    public ArrayList<Post> sortedPost = new ArrayList<>();

    public Data() {
    }

    public long lastTS = 0;
    private boolean testExact = true;

    public Post[] oldTop3;

    public int indexFor(Post p) {
        final int index = Collections.binarySearch(sortedPost, p);
        if(index < 0)
            return index;
        if(sortedPost.get(index) == p)
            return index;
        int copy = index;
        while(copy > 0 && sortedPost.get(copy).compareTo(sortedPost.get(copy - 1)) == 0) {
            copy--;
            if(sortedPost.get(copy) == p)
                return copy;
        }
        while(copy < sortedPost.size() - 1 && sortedPost.get(copy).compareTo(sortedPost.get(copy + 1)) == 0) {
            copy++;
            if(sortedPost.get(copy) == p)
                return copy;
        }
        return index;
    }

    public void addPost(Post post) {
        posts.put(post.post_id, post);

        expireAt();

        final int index = indexFor(post);
        if(index < 0)
            sortedPost.add(-(index + 1), post);
        else
            sortedPost.add(index, post);

        addInCircularAtLastOfSameTS(post);
    }

    private void resort(int index) {
        if(index < 0)
            System.out.println("lol");

        while(index > 0 && sortedPost.get(index).compareTo(sortedPost.get(index - 1)) < 0) {
            Collections.swap(sortedPost, index, index - 1);
            index--;
        }
        while(index < sortedPost.size() - 1 && sortedPost.get(index).compareTo(sortedPost.get(index + 1)) > 0) {
            Collections.swap(sortedPost, index, index + 1);
            index++;
        }
    }

    private void addInCircularAtLastOfSameTS(Perishable p) {
        //if(perishables.isEmpty()) {
        perishables.addBefore(p);
        if (perishables.size() == 2) {
            perishables.advanceBackward();
        }
    }

    public void addComment(Comment comment) {
        if (comment.answered == null) {
            return;
        }

        Post parent = comment.getParentPost();
        int post_index = indexFor(parent);
        if (parent.score <= 0) {
            return; // Si le post est déjà expiré, on ne retient pas le commentaire
        }
        comments.put(comment.comment_id, comment);
        parent.relatedComments.add(comment);
        parent.totalScore += 10;

        resort(post_index);

        expireAt();

        addInCircularAtLastOfSameTS(comment);
    }

    public void removePost(Post post) {
        int index = Collections.binarySearch(sortedPost, post);
        if(index >= 0)
            sortedPost.remove(index);
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

            perishables.markIf(p -> {
                if(p instanceof Post) {
                    int index = Collections.binarySearch(sortedPost, (Post) p);
                    if(index >= 0)
                        resort(index);
                    if(((Post) p).totalScore == 0) {
                        removePost((Post) p);
                        return true;
                    }
                }
                return false;
            });

            perishables.swipeMarked();
            lastTS = timestamp;
        } else {

            for (int i = 0; i < iter; i++)
                perishables.advanceBackward();
            for (int i = 0; i < iter; i++) {
                if(perishables.curr() instanceof Post) {
                    resort(indexFor((Post) perishables.curr()));
                    if(((Post) perishables.curr()).totalScore == 0) {
                        removePost((Post) perishables.curr());
                        perishables.markCurrent();
                    }
                }
                perishables.advanceForward();
            }

            perishables.swipeMarked();

            lastTS = timestamp;
        }
    }

    public void expireAt() {
        if (perishables.isEmpty() || !testExact) {
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
            if(perishables.curr() instanceof Post) {
                resort(indexFor((Post) perishables.curr()));
                if(((Post) perishables.curr()).totalScore == 0) {
                    removePost((Post) perishables.curr());
                    perishables.markCurrent();
                }
            }
            perishables.advanceForward();
        }
    }


    public Top3 getTop3() {
        Top3 top3 = new Top3();

//        MinMaxPriorityQueue<Post> q = MinMaxPriorityQueue.maximumSize(3).create();
//        q.addAll(posts.values());
//        for (int i = 0; i < 3; i++) {
//            if (q.isEmpty()) {
//                break;
//            }
//            top3.data[i] = new Top3.PostData(q.poll());
//        }
        for(int i = 0; i < 3; i++) {
            if(sortedPost.size() > i)
                top3.data[i] = new Top3.PostData(sortedPost.get(i));
        }

        return top3;
    }

}
