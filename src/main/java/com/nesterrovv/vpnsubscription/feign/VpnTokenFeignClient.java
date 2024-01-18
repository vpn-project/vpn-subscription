package com.nesterrovv.vpnsubscription.feign;

import com.nesterrovv.vpnlibrary.Token;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "vpn-token/vpn/token")
public interface VpnTokenFeignClient {

    @DeleteMapping("/delete/{id}")
    void deleteToken(@PathVariable("id") Integer tokenId);

    @PostMapping("/generate")
    Token generateToken();

}
