package br.com.anthony.bot.antinho;

import br.com.anthony.bot.antinho.service.AntinhoBotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AntinhoApplication {


    private static String token;

    @Value("${DISCORD_BOT_TOKEN}")
    public void setToken(String token) {
        AntinhoApplication.token = token;
    }
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(AntinhoApplication.class, args);
        JDA builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("fale comigo mencionando @antinho"))
                .addEventListeners(new AntinhoBotListener(new br.com.anthony.bot.antinho.service.ChatService().doRag()))
                .build()
                .awaitReady();
    }
    }
