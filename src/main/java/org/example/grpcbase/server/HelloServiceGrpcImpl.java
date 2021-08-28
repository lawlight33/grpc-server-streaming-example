package org.example.grpcbase.server;

import io.grpc.stub.StreamObserver;
import org.example.grpc.base.HelloRequest;
import org.example.grpc.base.HelloResponse;
import org.example.grpc.base.HelloServiceGrpc;

public class HelloServiceGrpcImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String greeting = "Hello, " + request.getFirstName() + " " + request.getLastName() + "!";
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
