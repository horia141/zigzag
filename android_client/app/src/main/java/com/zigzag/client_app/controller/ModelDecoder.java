package com.zigzag.client_app.controller;

import com.zigzag.common.api.NextGenResponse;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransport;

import java.nio.ByteBuffer;

public class ModelDecoder {
    public static class Error extends Exception {
        private static final long serialVersionUID = 1L;

        public Error(String message) {
            super(message);
        }
    }

    public ModelDecoder() {
        super();
    }

    public NextGenResponse decodeNextGenResponse(ByteBuffer nextGenResponseSerializedData) throws Error {
        try {
            TTransport transport = new TMemoryInputTransport(nextGenResponseSerializedData.array());
            TProtocol protocol = new TBinaryProtocol(transport);
            NextGenResponse nextGenResponse = new NextGenResponse();
            nextGenResponse.read(protocol);

            return nextGenResponse;
        } catch (TException e) {
            throw new Error(e.toString());
        }
    }
}
