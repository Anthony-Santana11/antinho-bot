package br.com.anthony.bot.antinho.service;

import br.com.anthony.bot.antinho.interfaces.AntinhoAssistant;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AntinhoBotListener extends ListenerAdapter {

    private ChatService chatService;
    private AntinhoAssistant antinhoAssistant;

    public AntinhoBotListener(AntinhoAssistant antinhoAssistant) {
        this.antinhoAssistant = antinhoAssistant;
        this.chatService = new ChatService();
        this.chatService.doRag();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return; // Ignora mensagens de outros bots
        }
        if (!event.getMessage().getContentDisplay().contains("@antinho")) {
            return; // Ignora mensagens que não mencionam o
        }

        System.out.println("DEBUG: Mensagem recebida: " + event.getAuthor().getName() + event.getAuthor().getAsMention());
        System.out.println("DEBUG: Conteúdo da mensagem: " + event.getMessage().getContentDisplay());

        String author = event.getAuthor().getAsMention();
        String question = event.getMessage().getContentDisplay().replace("@antinho", "");
        String respostaIA = antinhoAssistant.answer(question);
        String prefixo = "Fala comigo meu chefe eu sou o antinho o bot camarada "
                + author + " : ";

        // Divide a resposta em partes respeitando o limite de 2000 caracteres do Discord
        int limiteDiscord = 2000;
        java.util.List<String> mensagens = dividirMensagem(respostaIA, limiteDiscord - prefixo.length(), limiteDiscord);

        for (int i = 0; i < mensagens.size(); i++) {
            if (i == 0) {
                event.getChannel()
                        .sendMessage(prefixo + mensagens.get(i))
                        .queue();
            } else {
                event.getChannel()
                        .sendMessage(mensagens.get(i))
                        .queue();
            }
        }
    }

    /**
     * Divide uma mensagem em partes respeitando o limite de caracteres do Discord
     * @param texto Texto a ser dividido
     * @param limiteTexto Limite de caracteres para a primeira mensagem (descontando o prefixo)
     * @param limiteCompleto Limite de caracteres para mensagens subsequentes (2000)
     * @return Lista com as partes da mensagem
     */
    private java.util.List<String> dividirMensagem(String texto, int limiteTexto, int limiteCompleto) {
        java.util.List<String> resultado = new java.util.ArrayList<>();

        if (texto.length() <= limiteTexto) {
            resultado.add(texto);
            return resultado;
        }

        int limiteAtual = limiteTexto;
        int inicio = 0;

        while (inicio < texto.length()) {
            int fim = Math.min(inicio + limiteAtual, texto.length());


            if (fim < texto.length() && texto.charAt(fim) != ' ') {
                int ultimoEspaco = texto.lastIndexOf(' ', fim);
                if (ultimoEspaco > inicio) {
                    fim = ultimoEspaco;
                }
            }

            resultado.add(texto.substring(inicio, fim).trim());
            inicio = fim;
            limiteAtual = limiteCompleto; // Após a primeira, usa o limite completo
        }

        return resultado;
    }
}
