package com.blockid.service;

import com.blockid.blockchain.HashUtil;
import com.blockid.model.Block;
import com.blockid.repository.BlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlockchainService {

    private final BlockRepository blockRepository;

    public BlockchainService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public void addBlock(String data) {

        List<Block> chain = blockRepository.findAll();

        String previousHash = chain.isEmpty() ? "0" : chain.get(chain.size() - 1).getHash();

        String hash = HashUtil.sha256(data + previousHash);

        Block block = new Block();
        block.setData(data);
        block.setHash(hash);
        block.setPreviousHash(previousHash);
        block.setTimestamp(LocalDateTime.now());

        blockRepository.save(block);
    }

    public List<Block> getChain() {
        return blockRepository.findAll();
    }
}