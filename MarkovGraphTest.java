package comprehensive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkovGraphTest {

    private MarkovGraph graph;

    @BeforeEach
    void setup(){

        graph = new MarkovGraph(List.of("I", "Am","Angieeeee","I", "Am","Happy","I", "Am","Happy","I", "Am","Sad"));

    }
    @Test
    void testMostProbableChain(){
        assertEquals(List.of("I", "Am","Happy","I", "Am"), graph.generateMostProbableChain("I", 5));
    }

    @Test
    void testKMostProbable(){
        assertEquals(List.of("Happy","Angieeeee","Sad"), graph.findKLargest("Am",3));
    }

    @Test
    void testWRSChains(){
        assertEquals(20,graph.generateWRS("I", 20).size());
        System.out.println( graph.generateWRS("I", 100 ));
        TextGenerator.main(new String[] { "src/comprehensive/shrek.txt", "lord", "1", "one"});
    }



    }
