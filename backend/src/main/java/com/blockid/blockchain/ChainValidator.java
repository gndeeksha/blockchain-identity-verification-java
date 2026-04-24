package com.blockid.blockchain;

import com.blockid.model.Block;

import java.util.List;

public class ChainValidator {

    public static boolean isValid(List<Block> chain) {

        for (int i = 1; i < chain.size(); i++) {

            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            //  Check previous hash link
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }

            // Recalculate hash
            String recalculatedHash =
                    HashUtil.sha256(current.getData() + current.getPreviousHash());

            if (!current.getHash().equals(recalculatedHash)) {
                return false;
            }
        }

        return true;
    }
}