package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Answerable;

import java.util.HashMap;
import java.util.Iterator;
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

    //public CircularList<Answerable> sortedPosts = new CircularList<>();

    public Iterator<Answerable> nextExpiringAnswerable;
    public int lastTS = 0;

    public void addPost(Post post) {
        posts.put(post.post_id, post);
        // TODO Ajouter dans la CircularList (insérer à la bonne position)
    }

    public void addComment(Comment comment) {
        Post parent = comment.getParentPost();
        if(parent.score <= 0)
            return; // Si le post est déjà expiré, on ne retient pas le commentaire
        comments.put(comment.comment_id, comment);
        parent.relatedComments.add(comment);
    }

    public void removePost(Post post) {
        posts.remove(post.post_id);
        for(Comment c : post.relatedComments)
            comments.remove(c.comment_id);
    }

    public void expireUntil(int timestamp) {
        int noOfFullDay = (timestamp - lastTS) / MS_PER_DAY;


        int lastDayTS = lastTS % MS_PER_DAY; // Number of ms since midnight
        int currentDayTS = timestamp % MS_PER_DAY; // Number of ms since midnight
    }

}
