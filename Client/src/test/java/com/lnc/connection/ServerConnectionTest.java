package com.lnc.connection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ServerConnectionTest {

    @Test
    public void testRequestServer() {
        String request = "Invalid Request";

        String response = ServerConnection.requestServer(request);

        assertNull(response);
    }
}
