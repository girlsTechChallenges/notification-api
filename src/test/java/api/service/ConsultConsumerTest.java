//package api.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.slf4j.Logger;
//
//class ConsultConsumerTest {
//
//    private ConsultConsumer consumer;
//    private Logger mockLogger;
//
//    @BeforeEach
//    void setUp() {
//        consumer = new ConsultConsumer() {};
//        mockLogger = Mockito.mock(Logger.class);
//
//        try {
//            var field = ConsultConsumer.class.getDeclaredField("logger");
//            field.setAccessible(true);
//            field.set(consumer, mockLogger);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void shouldConsumeMessageWithoutException() {
//        String mensagem = "Mensagem de teste Kafka";
//
//        consumer.consume(mensagem);
//
//        Mockito.verify(mockLogger).info("Mensagem recebida do Kafka: {}", mensagem);
//    }
//}
//
