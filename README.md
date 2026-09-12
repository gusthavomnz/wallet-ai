# Wallet AI

<p align="center">
  🌐 Select your language / Selecione seu idioma:<br>
  <a href="#english">&rarr; English</a> &nbsp;|&nbsp; <a href="#português">Português &larr;</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?logo=spring&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Python-AI%20Worker-3776AB?logo=python&logoColor=white" alt="Python">
  <img src="https://img.shields.io/badge/Ollama-Local%20AI-000000?logo=ollama&logoColor=white" alt="Ollama">
</p>

---

## English

A financial management system built on a microservices architecture (Java/Spring Boot and Python) that centralizes financial control through manual entry, invoice processing via OCR, and local AI via Ollama for smart expense categorization and an integrated chatbot.

### Architecture

| Service | Stack | Responsibility |
|---|---|---|
| **Core API** | Java / Spring Boot | Manages transactions, business rules, database persistence, and exposes the endpoints for the front-end. |
| **AI Worker** | Python | Handles the OCR pipeline for reading files and runs the local AI (Ollama) to interpret free text, invoices, and power the chatbot. |

### Key Features

- **Manual Entry** — Direct input of income and expenses.
- **Invoice Reading (OCR + AI)** — Text extraction from PDFs or images with automatic field structuring.
- **Smart Chatbot** — Natural language command interpretation for quick expense logging.

<p align="right"><a href="#wallet-ai">Back to top</a></p>

---

## Português

Sistema de gestão financeira baseado em arquitetura de microsserviços (Java/Spring Boot e Python) que centraliza o controle financeiro através de entrada manual, processamento de faturas via OCR e inteligência artificial local via Ollama para categorização inteligente de gastos e chatbot integrado.

### Arquitetura

| Serviço | Stack | Responsabilidade |
|---|---|---|
| **Core API** | Java / Spring Boot | Gerencia transações, regras de negócio, persistência no banco de dados e expõe os endpoints para o front-end. |
| **AI Worker** | Python | Processa o pipeline de OCR para leitura de arquivos e executa a IA local (Ollama) para interpretar textos livres, faturas e alimentar o chatbot. |

### Principais Funcionalidades

- **Cadastro Manual** — Inserção direta de receitas e despesas.
- **Leitura de Faturas (OCR + IA)** — Extração de texto de PDFs ou imagens e estruturação automática dos campos.
- **Chatbot Inteligente** — Interpretação de comandos em linguagem natural para lançamento rápido de gastos.

<p align="right"><a href="#wallet-ai">Voltar ao topo</a></p>