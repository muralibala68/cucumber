package org.bala.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);
   
    public static void main(String[] argv) {
        LOGGER.info("Use cases for CompletableFuture");
        LOGGER.info("as opposed to Future, CompletableFuture can be completed manually instead of waiting forever");
        useCase1();
        useCase2();
        useCase3();
        useCase4();
        useCase5();
    }

    private static void useCase1() {
        LOGGER.info("Chaining multiple callbacks...");
        CompletableFuture.supplyAsync(Main::findReceiver)
                         .thenApply(Main::sendMsg)
                         .thenAccept(Main::tellMe);
        LOGGER.info("------------------------------");
    }

    private static void useCase3() {
        LOGGER.info("Submit callbacks to ForkJoinPool...");
        CompletableFuture.supplyAsync(Main::findReceiver)
                         .thenApplyAsync(Main::sendMsg)
                         .thenApplyAsync(Main::sendOtherMsg)
                         .thenAccept(Main::tellMe);
        LOGGER.info("------------------------------");
    }

    private static void useCase2() {
        LOGGER.info("Building asynchronous systems...");
        CompletableFuture.supplyAsync(Main::findReceiver)
                         .thenCompose(Main::sendMsg1)
                         .thenAccept(Main::tellMe);
        LOGGER.info("------------------------------");
    }

    private static void useCase4() {
        LOGGER.info("Callback depending on multiple tasks...");
        CompletableFuture<String> to = CompletableFuture.supplyAsync(Main::findReceiver);
        CompletableFuture<String> content = CompletableFuture.supplyAsync(Main::createContent);
        to.thenCombine(content, Main::sendMsg)
          .thenAccept(Main::tellMe);
        LOGGER.info("------------------------------");
    }

    private static void useCase5() {
        LOGGER.info("Callback depending on either of the tasks...");
        CompletableFuture<String> first = CompletableFuture.supplyAsync(Main::findReceiver1);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(Main::findReceiver2);
        first.acceptEither(second, Main::sendMsg);
        LOGGER.info("------------------------------");
    }


    public static String findReceiver() {
        LOGGER.info("finding receiver");
        return "bala";
    }

    public static String findReceiver1() {
        LOGGER.info("finding recipient-1");
        return "recipient-1";
    }

    public static String findReceiver2() {
        LOGGER.info("finding recipient-2");
        return "recipient-2";
    }

    public static String createContent() {
        LOGGER.info("creating content to send");
        return "<* How are you? *>";
    }

    public static String sendMsg(final String receiver, final String msg) {
        LOGGER.info("sending {} to {}", msg, receiver);
        return receiver;
    }

    public static String sendMsg(final String receiver) {
        LOGGER.info("sending message-0 to " + receiver);
        return receiver;
    }

    public static String sendOtherMsg(final String receiver) {
        LOGGER.info("sending message-00 to " + receiver);
        return receiver;
    }

    public static CompletionStage<String> sendMsg1(final String receiver) {
        LOGGER.info("sending message-1 to " + receiver);
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(receiver);
        return cf;
    }

    public static void tellMe(final String msg) {
        LOGGER.info("all messages sent to: " + msg);
    }
}
