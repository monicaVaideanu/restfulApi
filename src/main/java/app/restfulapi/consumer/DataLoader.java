package consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final ApiConsumer apiConsumer;

    @Autowired
    public DataLoader(ApiConsumer apiConsumer) {
        this.apiConsumer = apiConsumer;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        apiConsumer.consumeApi();
    }
}
