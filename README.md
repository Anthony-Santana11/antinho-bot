# 🤖 Antinho Bot - Discord AI Assistant

Um bot simples para Discord integrado com IA utilizando **Java + JDA + LangChain4j + Gemini**, capaz de responder perguntas quando mencionado.

---

## 🚀 Tecnologias utilizadas

- Java 17+
- JDA (Java Discord API)
- LangChain4j
- Google Gemini API
- Maven

---

## 🧠 Funcionalidades

- Responde mensagens quando mencionado (`@antinho`)
- Integração com IA (Gemini)
- Tratamento de mensagens grandes (split automático de 2000 caracteres)
- Estrutura simples e extensível

---

## 📦 Estrutura do projeto


src/
├── listener/
│ └── AntinhoBotListener.java
├── service/
│ └── ChatService.java
└── Main.java


---

## ⚙️ Configuração

### 🔑 1. Variáveis de ambiente

Crie as variáveis:

```bash
GOOGLE_API_KEY=your_api_key_here
GOOGLE_MODEL_NAME=gemini-1.5-flash
DISCORD_TOKEN=your_discord_token_here
▶️ 2. Rodar o projeto
mvn clean install
mvn spring-boot:run

ou

mvn exec:java
💬 Como usar

No Discord, mencione o bot:

@antinho o que é um record no Java?

🛠️ Melhorias futuras
 Comandos slash (/chat)
 Memória de contexto por usuário
 Resposta em streaming (digitando)
 Deploy em VPS
 Dashboard web

⚠️ Observações
O Discord limita mensagens em 2000 caracteres, então o bot divide respostas automaticamente
Nunca suba suas API Keys para o GitHub
crie o seu application.properties

🔐 Segurança

Adicione ao .gitignore:

.env
application.properties

📄 Licença

Este projeto é open-source e pode ser utilizado para fins de estudo e desenvolvimento.

👨‍💻 Autor

Desenvolvido por Anthony Mendonça

⭐ Contribuição

Sinta-se livre para abrir issues ou enviar pull requests!
