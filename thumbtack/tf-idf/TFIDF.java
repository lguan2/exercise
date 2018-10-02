package algorithm.thumbtack;

import java.util.*;

/**
 * Created by linguan on 10/1/18.
 */
public class TFIDF {

    public static void main(String[] args) {
        Document d1 = new Document();
        d1.id = "d1";
        d1.words = Arrays.asList("a", "a", "b", "c", "c", "c", "c", "c");

        Document d2 = new Document();
        d2.id = "d2";
        d2.words = Arrays.asList("a", "a", "a", "a", "b", "b");

        Document d3 = new Document();
        d3.id = "d3";
        d3.words = Arrays.asList("a", "a", "a", "a", "a", "b", "b", "b", "b", "c", "c", "c");

        List<Document> documents = Arrays.asList(d1, d2, d3);

        TFIDF tfidf = new TFIDF(documents);

        List<Document> res = tfidf.getTopKDocuments(2, "a b c");
        res.forEach((document) -> System.out.println(document.id));
    }

    private List<Document> documents;
    private Map<String, Map<String, Integer>> index;


    public TFIDF(List<Document> documents) {
        this.documents = documents;
        this.index = new HashMap<>();
        buildIndex();
    }

    public List<Document> getTopKDocuments(int k, String query) {
        String[] q = query.split(" ");
        PriorityQueue<Document> heap = new PriorityQueue<>((d1, d2) -> {
            return Double.compare(countScore(d1, q), countScore(d2, q));
        });
        for (Document doc : documents) {
            if(heap.size() < k){
                heap.add(doc);
            } else if (heap.size() == k && countScore(doc, q) > countScore(heap.peek(), q)) {
                heap.poll();
                heap.add(doc);
            }
        }
        List<Document> res = new ArrayList<>(heap);
        Collections.reverse(res);
        return res;
    }

    private void buildIndex() {
        for (Document document : documents) {
            List<String> words = document.words;
            for (String word : words) {
                if (!index.containsKey(word)) {
                    index.put(word, new HashMap<>());
                }
                Map<String, Integer> map = index.get(word);
                map.put(document.id, map.getOrDefault(document.id, 0) + 1);
            }
        }
    }

    private double countScore(Document document, String[] words) {
        double res = 0.0;
        for(String word : words){
            if(index.containsKey(word)){
                Map<String, Integer> map = index.get(word);
                if(map.containsKey(document.id)) {
                    res += map.get(document.id) * 1.0 / map.size();
                }
            }
        }
        System.out.println(String.format("document id: %s, score: %s", document.id, res));
        return res;
    }


}
