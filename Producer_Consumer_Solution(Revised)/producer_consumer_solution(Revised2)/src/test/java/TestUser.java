import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.example.nhnacademy.producer_consumer_solution.Consumer;
import com.example.nhnacademy.producer_consumer_solution.Producer;
import com.example.nhnacademy.producer_consumer_solution.Store;

public class TestUser {

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> {
            String[] productList = { "[apple]", "[fineapple]", "[water]", "[milk]", "[meat]", "[snack]", "[beaf]","[vegetable]", "[meal]" };
            String[] producerList = {"[producer1]", "[producer2]", "[producer3]", "[producer4]", "[producer5]", "[producer6]", "[producer7]", "[producer8]", "[producer9]"};
            String[] consumerList = {"[man1]", "[man2]", "[man3]", "[man4]", "[man5]", "[man6]", "[man7]", "[man8]", "[man9]"};
            Store[] stores = new Store[productList.length];

            for (int i = 0; i < productList.length; i++) {
                stores[i] = (new Store(productList[i]));
                stores[i].open(); // 각 매장을 오픈
            }

            for(int i = 0; i < consumerList.length; i++){
                Consumer consumer = new Consumer(consumerList[i], stores);
                assertEquals("[man" + (i + 1) + "]", consumer.getConsumerName());

                Producer producer = new Producer(producerList[i], stores);
                assertEquals("[producer" + (i + 1) + "]", producer.getProducerName());
            }

        });
    }
}
