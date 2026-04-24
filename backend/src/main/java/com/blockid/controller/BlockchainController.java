package com.blockid.controller;

import com.blockid.model.Block;
import com.blockid.service.BlockchainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping
    public List<Block> getBlockchain() {
        return blockchainService.getChain();
    }
}