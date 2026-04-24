package com.blockid.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<String> chain = new ArrayList<>();

    public void addBlock(String hash) {
        chain.add(hash);
    }

    public List<String> getChain() {
        return chain;
    }
}