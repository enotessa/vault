package com.hashicorp.test.vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Component
public class Vault {
    VaultConfig vaultConfig;
    private Properties properties = new Properties();
    File file = new File("src/main/resources/application.properties");

    @Autowired
    public Vault(ApplicationContext applicationContext) {
        this.vaultConfig = applicationContext.getBean(VaultConfig.class);
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPass() {
        VaultEndpoint vaultEndpoint = vaultConfig.vaultEndpoint();
        VaultTemplate vaultTemplate = new VaultTemplate(vaultEndpoint, vaultConfig.clientAuthentication());
        String storagePath = properties.getProperty("hashicorp.storagePath");
        String secretPath = properties.getProperty("hashicorp.secretPath");
        VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue(storagePath,
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_1);
        VaultResponse response = keyValueOperations.get(secretPath);
        HashMap<String, String> secretMap = (HashMap<String, String>) response.getRequiredData().get("data");

        return secretMap.get("db_password");

    }
}
