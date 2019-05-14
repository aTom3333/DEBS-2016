package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Data {
    private static Data instance = new Data();

    private Data() {
    }

    public static Data instance() {
        return instance;
    }

    public Map<Integer, User> users = new HashMap<>();

    public SortedMap<Integer, Post> posts = new TreeMap<>();

    public Map<Integer, Comment> comments = new HashMap<>();

}
