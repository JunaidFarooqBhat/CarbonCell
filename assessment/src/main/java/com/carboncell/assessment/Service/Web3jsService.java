package com.carboncell.assessment.Service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class Web3jsService {
private final Web3j web3j;
public Web3jsService(){
    this.web3j=Web3j.build(new HttpService("https://mainnet.infura.io/v3/579a51f9b4954aaa841a8495a4c1fa14"));
}
    public BigDecimal getAccountBalance(String ethAddress) throws Exception {
        // Retrieve balance in Wei and convert to Ether
        BigInteger ethBalance = web3j.ethGetBalance(ethAddress, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send().getBalance();
        return Convert.fromWei(new BigDecimal(ethBalance), Convert.Unit.ETHER);
    }
}
