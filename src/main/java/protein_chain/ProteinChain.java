package protein_chain;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ProteinChain {

    //klasa musi być Singletonem, ponieważ potrzebujemy tylko jedną jej instancję w jednym momencie
    private static ProteinChain instance;
    private ProteinChain() {
    }

    public static ProteinChain getInstance() {
        if (instance == null) {
            synchronized (ProteinChain.class) {
                if (instance == null) {
                    instance = new ProteinChain();
                }
            }
        }
        return instance;
    }

    boolean changePossible (String s1, String s2) {
        List<Character> list1 = new ArrayList<>();
        //utworzenie ze Stringa listy Characterów i posortowanie ich zostało przydzielone dwóm oddzielnym wątkom tak,
        //aby te zadania wykonywały się w tym samym czasie
        AtomicReference<List<Character>> sortedList1 = new AtomicReference<>();
        AtomicReference<List<Character>> sortedList2 = new AtomicReference<>();
        Runnable runnable1 = () -> {
            for (int i = 0; i <= s1.length() - 1; i++) {
                list1.add(s1.charAt(i));
            }
            sortedList1.set(list1.stream()
                    .sorted()
                    .collect(Collectors.toList()));
        };
        Runnable runnable2 = () -> {
            List<Character> list2 = new ArrayList<>();
            for (int i = 0; i <= s2.length() - 1; i++) {
                list2.add(s2.charAt(i));
            }
            sortedList2.set(list2.stream()
                    .sorted()
                    .collect(Collectors.toList()));
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();

        if (sortedList1.equals(sortedList2))
            return true;
        else
            return false;
    }

    public List<String> loadData () {
        List<String> chainsList;
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            URI uri = classLoader.getResource("dataForProteinChain.txt").toURI();
            Path path = Paths.get(uri);
            chainsList = Files.readAllLines(path);
        } catch (Exception e) {
            chainsList = new ArrayList<>();
        }
        return chainsList;
    }

    public int numberOfChains(List<String> list) {
        return list.size();
    }

    public void checkChains(){
        List<String> chainsList = this.loadData();
        int numberOfChains = this.numberOfChains(chainsList);
        for (int i = 0; i <= numberOfChains-1; i = i+2) {
            System.out.println(chainsList.get(i));
            System.out.println(chainsList.get(i+1));
            System.out.println(changePossible(chainsList.get(i),chainsList.get(i+1)));
        }
    }
}
