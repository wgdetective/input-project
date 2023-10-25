package com.wgdetective.security.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NodeApiTest {

    @Test
    void test() {
        assertNotNull(new NodeApi().getAllNodes());
    }

}
