# Wallet AI
Sistema de gestão financeira baseado em arquitetura de microsserviços (Java/Spring Boot e Python) que centraliza o controle financeiro através de entrada manual, processamento de faturas via OCR e inteligência artificial local via ollama para categorização inteligente de gastos e chatbot integrado.

## Arquitetura:

Core API (Java / Spring Boot): Gerencia transações, regras de negócio, persistência no banco de dados e expõe os endpoints para o front-end.

AI Worker (Python): Processa o pipeline de OCR para leitura de arquivos e executa a IA local (Ollama) para interpretar textos livres, faturas e alimentar o chatbot.


## Principais funcionalidades:

Cadastro Manual: Inserção direta de receitas e despesas.

Leitura de Faturas (OCR + IA): Extração de texto de PDFs ou imagens e estruturação automática dos campos.

Chatbot Inteligente: Interpretação de comandos em linguagem natural para lançamento rápido de gastos.