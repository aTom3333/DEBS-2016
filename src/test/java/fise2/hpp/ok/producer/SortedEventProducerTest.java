package fise2.hpp.ok.producer;

import fise2.hpp.ok.eventsIR.Comment;
import fise2.hpp.ok.eventsIR.Post;
import fise2.hpp.ok.interfaces.Event;
import org.junit.Assert;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ArrayBlockingQueue;

public class SortedEventProducerTest {

    @Test
    public void run() {
        ArrayBlockingQueue<Event> queue = new ArrayBlockingQueue<>(40);
        SortedEventProducer producer = new SortedEventProducerBuilder(queue)
                .requestString(Event.Type.COMMENT,
                        "2019-05-15T12:05:00.000+0000|1|1|Premier commentaire|user||1\n" +
                                "2019-05-15T12:06:00.000+0000|2|1|Réponse commentaire|user|1|\n" +
                                "2019-05-15T12:08:00.000+0000|3|1|Premier commentaire sur post 2|user||2\n" +
                                "2019-05-15T12:09:00.000+0000|4|1|Deuxième commentaire|user||1\n")
                .requestString(Event.Type.POST,
                        "2019-05-15T12:00:00.000+0000|1|1|Premier post|user\n" +
                                "2019-05-15T12:07:00.000+0000|2|1|Deuxième post|user")
                .build();

        long timestamp_base = OffsetDateTime.of(2019, 5, 15, 12, 0, 0, 0, ZoneOffset.UTC).toEpochSecond() * 1000;

        Event[] expected = {
                new Post(timestamp_base, 1, 1, "Premier post", "user"),
                new Comment(timestamp_base + 5 * 60 * 1000, 1l, 1l, "Premier commentaire", "user", null, 1l),
                new Comment(timestamp_base + 6 * 60 * 1000, 2l, 1l, "Réponse commentaire", "user", 1l, null),
                new Post(timestamp_base + 7 * 60 * 1000, 2, 1, "Deuxième post", "user"),
                new Comment(timestamp_base + 8 * 60 * 1000, 3, 1, "Premier commentaire sur post 2", "user", null, 2l),
                new Comment(timestamp_base + 9 * 60 * 1000, 4, 1, "Deuxième commentaire", "user", null, 1l),
        };

        producer.run();

        Event[] events = queue.toArray(Event[]::new);

        Assert.assertArrayEquals(expected, events);
    }
}