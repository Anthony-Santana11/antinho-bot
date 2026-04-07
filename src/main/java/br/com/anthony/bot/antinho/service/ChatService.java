package br.com.anthony.bot.antinho.service;

import br.com.anthony.bot.antinho.interfaces.AntinhoAssistant;
import br.com.anthony.bot.antinho.utils.Utils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

public class ChatService {
    private ChatModel chatModel;

    public ChatService() {
        this.chatModel = GoogleAiGeminiChatModel.builder()
                .apiKey("YOUR_GOOGLE_API_KEY")
                .modelName("gemini-2.5-flash")
                .build();
    }

    public AntinhoAssistant doRag() {
        List<Document> documents = loadDocuments(Utils.toPath("./documents"), Utils.glob("*.txt"));
        return AiServices.builder(AntinhoAssistant.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(createContentRetriever(documents))
                .systemMessageProvider(query -> new SystemMessage(AntinhoAssistant.SYSTEM_PROMPT).text())
                .build();
    }

    private static ContentRetriever createContentRetriever(List<Document> documents) {
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

}
