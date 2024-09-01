package io.github.stelitop.battledudes.configs;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import io.github.stelitop.mad4j.Mad4jConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Configuration
@Import(Mad4jConfig.class)
public class DiscordBotConfig {

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() throws FileNotFoundException {
        File file = new File(getClass().getResource("/bottoken.txt").getFile());
        Scanner scanner = new Scanner(file);

        String tokenStr = scanner.nextLine();
        scanner.close();

        return DiscordClientBuilder.create(tokenStr).build()
                .gateway()
                .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.playing("Bot is online!")))
                .login()
                .block();
    }
}
