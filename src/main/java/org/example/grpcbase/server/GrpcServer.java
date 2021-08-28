package org.example.grpcbase.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8081)
                .addService(new HelloServiceGrpcImpl()).build();

        server.start();
        server.awaitTermination();
    }
}
