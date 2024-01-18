package com.nesterrovv.vpnsubscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EntityScan({"com.nesterrovv.vpnlibrary", "com.nesterrovv.vpnsubscription.*"})
@EnableJpaRepositories("com.nesterrovv.vpnsubscription.*")
@ComponentScan(basePackages = { "com.nesterrovv.vpnsubscription.*" })
//@EntityScan("my.package.base.*")
@SuppressWarnings("HideUtilityClassConstructor")
public class VpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpnApplication.class, args);
    }

}
