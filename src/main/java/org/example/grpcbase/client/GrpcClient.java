package org.example.grpcbase.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.grpc.base.HelloRequest;
import org.example.grpc.base.HelloResponse;
import org.example.grpc.base.HelloServiceGrpc;

import java.util.concurrent.CountDownLatch;

public class GrpcClient {

    private final static Logger log = LogManager.getLogger(GrpcClient.class);

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();
        HelloServiceGrpc.HelloServiceStub stub = HelloServiceGrpc.newStub(channel);

        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName("Mark")
                .setLastName("Chesnavsky")
                .build();

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<HelloResponse> requestObserver = new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                log.info("received new response: " + helloResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("received error", throwable);
                throw new RuntimeException(throwable);
            }

            @Override
            public void onCompleted() {
                log.info("completed");
                countDownLatch.countDown();
            }
        };

        stub.hello(request, requestObserver);
        countDownLatch.await();

        channel.shutdown();
    }
}
