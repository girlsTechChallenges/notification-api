# API de Notificação de Consultas Hospitalares 🔔🏥

Esta API é responsável por gerenciar notificações de consultas de pacientes em um hospital. Através da integração com **Kafka** e **Mailtrap**, a API envia emails automatizados aos pacientes informando sobre o agendamento, cancelamento ou realização de consultas.

A aplicação segue a **arquitetura MVC (Model-View-Controller)** para organização do código e separação de responsabilidades.

## Execução

1. Clonar o repositório 

```bash
git clone https://github.com/girlsTechChallenges/notification-api.git
```

2. Configurar Kafka e Mailtrap com as credenciais necessárias

3. Executar a aplicação

## Configuração do Kafka e Zookeeper 🎈

Antes de executar a aplicação, é necessário subir o Kafka e o Zookeeper.  

Se você estiver usando **Docker Compose**, execute:

```bash
docker-compose up -d kafka zookeeper
```

Após isso, basta rodar a aplicação: 

```bash
mvn clean spring-boot:run
```

---

## Tecnologias Utilizadas 💻

- **Java 21**
- **Spring Boot**: Framework principal para construção da API.
- **Spring Kafka**: Integração com Kafka para comunicação assíncrona.
- **Spring Mail (JavaMailSender)**: Envio de emails através do Mailtrap.
- **Lombok**: Redução de boilerplate (getters, setters, construtores, etc.).
- **Jackson**: Serialização e desserialização de objetos JSON.
- **Kafka**: Broker para envio e consumo de mensagens.
- **Mailtrap**: Sandbox para envio de emails em ambiente de desenvolvimento.
- **Validation (Jakarta Validation)**: Validação de DTOs.
- **SLF4J/Logback**: Logs estruturados.

---

## Arquitetura 🏛️

A API utiliza a **arquitetura MVC**:

- **Model (Domain)**: Contém os modelos de domínio, como `Consulation` e `Patient`.
- **Controller (Entrypoint)**: Expõe endpoints REST, recebe requisições HTTP e envia para os serviços.
- **Service**: Contém a lógica de negócio, envio de mensagens Kafka e envio de emails.
- **Mapper**: Converte DTOs em objetos de domínio e vice-versa.
- **Consumer Kafka**: Consome mensagens do tópico Kafka para processamento posterior.

---

## Estrutura de Diretórios 🏛️

```
api/
├── domain/
│   └── model/          # Modelos de domínio (Consulation, Patient)
├── entrypoint/
│   └── controller/     # Controllers REST (ConsulationController)
├── entrypoint/dto/     # DTOs de requisição e resposta
├── service/            # Serviços de negócio e envio de emails
├── mapper/             # Mapeamento DTO <-> Domain
├── enums/              # Status de consulta e email
└── exception/          # Exceções customizadas

```

## Endpoints 🏁

### Enviar consulta 

```
POST /consulations
```

**Request Body**:

```
{
"id": "1",
"nameProfessional": "Dra. Maria Silva",
"pacient": {
"name": "Jorginho",
"email": "jorginho123@gmail.com"
},
"localTime": "21:00:00",
"date": "2025-10-05",
"reason": "Consulta de rotina",
"statusConsulation": "SCHEDULED"
}
```

**Response Body**:

```
{
"message": "Consulta enviada com sucesso para o Kafka!",
"id": "1",
"statusConsulation": "SCHEDULED"
}
```
 
**Fluxo do Endpoint:**

- O **ConsulationController** recebe a requisição.
- O **DTO** é mapeado para o modelo Consulation.
- O **ConsulationService** envia a mensagem para o **Kafka**.
- O **EmailService** envia o email para o paciente via **Mailtrap**.
- Retorna a resposta confirmando que a consulta foi processada.

## Email Notification ✉️

- Emails são enviados usando **JavaMailSender**.
- O conteúdo do email muda conforme o status da consulta:
    - `SCHEDULED` → "Agendamento da Consulta"
    - `CARRIED_OUT` → "Realização da Consulta"
    - `CANCELLED` → "Cancelamento da Consulta"
- O corpo do email inclui:
    - Nome do paciente
    - Nome do profissional
    - Data, horário e motivo da consulta
    - Assinatura do sistema hospitalar

## Configuração ⚙️

### Kafka
app.kafka.topics.consulations=consulations-topic
app.kafka.groupid=consulations-group

### Email
spring.mail.host=smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=<SEU_USERNAME>
spring.mail.password=<SEU_PASSWORD>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true





