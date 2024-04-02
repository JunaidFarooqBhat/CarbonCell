package com.carboncell.assessment.Controller;

import com.carboncell.assessment.Service.Web3jsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("Api")
@Tag(name = "Access Ethereum Wallet",description = "This Controller contains the api which fetches the Etherum balance")
public class Web3JsEthereumController {
    @Autowired
    private final Web3jsService web3jsService;
    @Operation(
            summary = "The api fetches the Ethereum wallet balance when provided the ethereum wallet address"
            ,responses  ={
            @ApiResponse(
                    description = "Success",
                    responseCode = "200"
            ),
            @ApiResponse(responseCode = "403", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Invalid address")
    }
    )
    @GetMapping("Ethereum/Balance")
    public BigDecimal getEthAccountBalance(@RequestParam(required = true) String ethAddress) {
        try {
            return web3jsService.getAccountBalance(ethAddress);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error while fetching balance",e.getCause());
        }
    }


}
