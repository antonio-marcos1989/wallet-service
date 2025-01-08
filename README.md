# Wallet Service

**Wallet Service** é uma aplicação baseada em Spring Boot para gerenciar carteiras digitais e transações financeiras. A aplicação oferece funcionalidades como criação de carteiras, depósitos, saques, transferências e consulta de saldo histórico.

---

## **Repositório**
O código-fonte do projeto está disponível no GitHub:

[**Wallet Service - GitHub Repository**](https://github.com/antonio-marcos1989/wallet-service.git)

---

## **Índice**
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Execução](#instalação-e-execução)
- [Uso](#uso)
    - [Documentação da API](#documentação-da-api)
    - [Principais Endpoints](#principais-endpoints)
- [Explicação do Design](#explicação-do-design)
    - [Camadas Arquiteturais](#camadas-arquiteturais)
    - [Monitoramento e Resiliência](#monitoramento-e-resiliência)
- [Trade-offs e Compromissos](#trade-offs-e-compromissos)
- [Contribuições](#contribuições)
- [Contato](#contato)

---

## **Pré-requisitos**
Antes de começar, certifique-se de ter o seguinte instalado:
- **Docker** (versão 20.10 ou superior)

---

## **Instalação e Execução**

### **1. Clonar o Repositório**
Clone o projeto para o seu ambiente local:
```bash
git clone https://github.com/antonio-marcos1989/wallet-service.git
cd wallet-service

```

### **2. Construir a Imagem Docker**
No diretório raiz do projeto (onde está o arquivo Dockerfile), execute o comando abaixo para criar a imagem Docker:
```bash
docker build -t wallet-service:latest .

```

### **3. Executar o container**
Execute o container a partir da imagem criada:
```bash
docker build -t wallet-service:latest .
```
A aplicação estará disponível em:
```bash
http://localhost:8080/swagger-ui/index.html


