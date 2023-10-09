package com.hashicorp.test.vault;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultClients;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.client.RestOperations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {
    VaultEndpoint vaultEndpoint;
    private Properties properties = new Properties();

    public VaultConfig() {
        try {
            this.properties.load(new FileReader(new File("src/main/resources/application.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        String roleId = "9bc1c6dc-2fbf-ba94-656c-5d01b075a173"; // TODO
        String secretId = "d7cd2ec8-5357-78f4-d528-cac3dd98a0ba";
        if(roleId != null && secretId != null) {
            AppRoleAuthenticationOptions appRoleAuthenticationOptions = AppRoleAuthenticationOptions.builder()
                    .roleId(AppRoleAuthenticationOptions.RoleId.provided(roleId))
                    .secretId(AppRoleAuthenticationOptions.SecretId.provided(secretId))
                    .build();
            return new AppRoleAuthentication(appRoleAuthenticationOptions, restOperations());
        }
        return null;

    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        String url = properties.getProperty("hashicorp.secretPath");
        this.vaultEndpoint = VaultEndpoint.from(URI.create("http://localhost:8080/"));
        return vaultEndpoint;
    }
}
