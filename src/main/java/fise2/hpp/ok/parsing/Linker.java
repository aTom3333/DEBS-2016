package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Comment;
import fise2.hpp.ok.eventsIR.Friendship;
import fise2.hpp.ok.eventsIR.Like;
import fise2.hpp.ok.eventsIR.Post;
import fise2.hpp.ok.interfaces.Event;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.structs.User;

public class Linker {

    public static void link(Event event, Data data) {
        if (event instanceof Comment) {
            link((Comment) event, data);
        }
        if (event instanceof Like) {
            link((Like) event, data);
        }
        if (event instanceof Friendship) {
            link((Friendship) event, data);
        }
        if (event instanceof Post) {
            link((Post) event, data);
        }
    }

    public static void link(Comment comment, Data data) {
        User commentator = new User(comment.user);
        User existing = data.users.putIfAbsent(comment.user_id, commentator);
        if(existing != null) {
            existing.name = comment.user;
            commentator = existing;
        }
        data.addComment(
                new fise2.hpp.ok.events.Comment(
                        comment.ts,
                        comment.comment_id,
                        commentator,
                        comment.comment,
                        (comment.comment_replied != null ? data.comments.get(comment.comment_replied) : data.posts.get(comment.post_replied))));
    }

    public static void link(Friendship friendship, Data data) {

    }

    public static void link(Like like, Data data) {

    }

    public static void link(Post post, Data data) {
        User poster = new User(post.user);
        User existing = data.users.putIfAbsent(post.user_id, poster);
        if(existing != null) {
            existing.name = post.user;
            poster = existing;
        }
        data.addPost(new fise2.hpp.ok.events.Post(post.ts, post.post_id, poster, post.post));
    }
}
