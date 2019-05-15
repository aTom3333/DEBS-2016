package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Comment;
import fise2.hpp.ok.eventsIR.Friendship;
import fise2.hpp.ok.eventsIR.Like;
import fise2.hpp.ok.eventsIR.Post;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.structs.User;

public class Linker {
    public static void link(Comment comment, Data data) {
        User commentator = new User(comment.user);
        if (data.users.putIfAbsent(comment.user_id, commentator) != null) {
            data.users.get(comment.user_id).name = comment.user;
        }
        data.comments.putIfAbsent(
                comment.comment_id,
                new fise2.hpp.ok.events.Comment(
                        comment.ts,
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
        if (data.users.putIfAbsent(post.user_id, poster) != null) {
            data.users.get(post.user_id).name = post.user;
        }
        data.posts.putIfAbsent(post.post_id, new fise2.hpp.ok.events.Post(post.ts, poster, post.post));
    }
}
