package comprehensive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VertexNodeTest {

    private VertexNode node;

    @BeforeEach
    void setup(){
        node = new VertexNode("Hi");
        node.addEdge("stinky");
        node.addEdge("stinky");
        node.addEdge("stinky");

        node.addEdge("brubber");
        node.addEdge("brubber");

        node.addEdge("shewwwyyy!!!!");

    }

    @Test
    void testMostProbable(){
        assertEquals("stinky",node.mostProbableNextWord());
    }

    @Test
    void testKMostProbable(){
        assertEquals(List.of("stinky","brubber"), node.kMostProbable(2));
    }

    @Test
    void testKMostProbableAll(){
        assertEquals(List.of("stinky","brubber","shewwwyyy!!!!"), node.kMostProbable(3));
    }

}