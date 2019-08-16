package protein_chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProteinChainTest {

    ProteinChain proteinChain;

    @BeforeEach
    void createInstanceOfProtein_Chain(){
        proteinChain = ProteinChain.getInstance();
    }

    @ParameterizedTest
    @MethodSource("dataForChangePossibleTest")
    void changePossibleTest(String s1, String s2, boolean expected){
        boolean result = proteinChain.changePossible(s1, s2);
        assertEquals(expected,result);
    }

    static Stream<Arguments> dataForChangePossibleTest(){
        return Stream.of(
                Arguments.arguments("AABBCC","ACBBCA",true),
                Arguments.arguments("AAABBB","BBBAAA",true),
                Arguments.arguments("ABCCAB","ABCCBB",false),
                Arguments.arguments("ABCABC","ABCAB",false)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForChangePossibleTest")
    void changePossibleTest1(String s1, String s2, boolean expected){
        boolean result = proteinChain.changePossible(s1, s2);
        assertEquals(expected,result);
    }

    @Test
    void printChainsListLoadedFromFile() {
        System.out.println(proteinChain.loadData());
    }

    @Test
    void countNumberOfChains(){
        System.out.println(proteinChain.numberOfChains(proteinChain.loadData()));
    }

    @Test
    void checkChainsTest() {
        proteinChain.checkChains();
    }

}