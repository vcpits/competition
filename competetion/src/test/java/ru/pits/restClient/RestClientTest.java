package ru.pits.restClient;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RestClientTest {
    @Test
    public void testExecute() throws Exception {
        RestClient client = new RestClient();
        client.execute();
    }

}