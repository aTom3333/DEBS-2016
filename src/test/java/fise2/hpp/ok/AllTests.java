package fise2.hpp.ok;

import fise2.hpp.ok.main.Query1Test;
import fise2.hpp.ok.main.Tests;
import fise2.hpp.ok.parsing.AbstractParserTest;
import fise2.hpp.ok.parsing.CommentParserTest;
import fise2.hpp.ok.parsing.PostParserTest;
import fise2.hpp.ok.producer.SortedEventProducerTest;
import fise2.hpp.ok.structs.DataTest;
import fise2.hpp.ok.utils.CircularListTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({Query1Test.class, Tests.class, AbstractParserTest.class, CommentParserTest.class, PostParserTest.class, DataTest.class, SortedEventProducerTest.class, CircularListTest.class})
public class AllTests {
}
