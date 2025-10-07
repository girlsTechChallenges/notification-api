# API de Notificação de Consultas Hospitalares 🔔🏥

Esta API é responsável por gerenciar notificações de consultas de pacientes em um hospital. Através da integração com **Kafka** e **Mailtrap**, a API envia emails automatizados aos pacientes informando sobre o agendamento, cancelamento ou realização de consultas.

A aplicação segue a **arquitetura MVC (Model-View-Controller)** para organização do código e separação de responsabilidades.

1) ```Propósito```: processar as mensagens de consultas e enviar e-mails de consultas para os pacientes, com os status: ```AGENDADA```, ```REALIZADA```, ```CANCELADA```
3) ```Arquitetura```: 
4) ```Perfis e Configuração```: 

## Execução

1. Clonar o repositório 

```bash
git clone https://github.com/girlsTechChallenges/notification-api.git
```

2. Configurar Kafka e Mailtrap com as credenciais necessárias

3. Executar a aplicação

## Como rodar o projeto? 🎈

Antes de executar a aplicação, é necessário subir o Kafka e o Zookeeper.  

Se você estiver usando **Docker Compose**, execute:

```bash
docker-compose up -d kafka zookeeper
```

Após isso, basta rodar a aplicação: 

```bash
mvn clean spring-boot:run
```

## Onde verificar o envio de e-mails? 🎈

1) Acesse o site do ```MailTrap```: https://mailtrap.io/

2) Clique em "login"

3) Em "E-mail", preencha: ```techchallengefiaphospital@gmail.com```

4) E na "senha/password", utilize: ```TechChallenge2025@$```

5) Ao acessar a página: https://mailtrap.io/home, você verá o Sandbox > ```tech-challenge-admin```

6) Ao clicar em ```tech-challenge-admin```, você será encaminhado para: https://mailtrap.io/inboxes/4085302/messages onde poderá visualizar os e-mails que foram enviados pelo kafka


## Executar relatório de testes no Sonar Quality

1. Executar os testes gerais da aplicação
```bash
mvn test
```

2. Executar o report do jacoco
```bash
mvn jacoco:report
```

A cobertura será gerada na pasta: ```target/site/jacoco/index.html, ao clicar no arquivo, você poderá visualizar a cobertura de testes da aplicação```.

3. Executar o relatório no SonarQuality
```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=girlsTechChallenges_notification-api -Dsonar.organization=girlstechchallenges -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=<SEU_TOKEN> -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml 
```

A credencial: ```-Dsonar.login=``` deve ser gerada acessando o seu perfil no SonarQuality > My Account > Security > Inserir um TOKEN NAME > Generate Token. 

- O relatório estará disponível em: ```https://sonarcloud.io/project/overview?id=girlsTechChallenges_notification-api```



---

## 🚀 Tecnologias Utilizadas 💻

*   **Java 21** → Linguagem principal utilizada no desenvolvimento da aplicação.


*   **Spring Boot 3.3.5** → Framework principal para construção da API e configuração simplificada do projeto.


*   **Spring Web** → Responsável por expor endpoints REST e lidar com requisições HTTP.


*   **Spring Kafka** → Integração com o Apache Kafka para consumo e publicação de mensagens de forma assíncrona.


*   **Reactor Kafka** → Implementação reativa baseada em _Project Reactor_ para trabalhar com _streams_ de mensagens Kafka.


*   **Spring Mail (JavaMailSender)** → Envio de emails através do serviço sandbox **Mailtrap**, permitindo simulações seguras em ambiente de desenvolvimento.


*   **Jakarta Validation (Spring Validation)** → Validação de dados recebidos nos DTOs antes do processamento da requisição.


*   **Jackson Datatype JSR310** → Suporte adicional para serialização e desserialização de tipos de data/hora (LocalDate, LocalDateTime, etc.).


*   **Lombok** → Elimina _boilerplate code_ (getters, setters, construtores, equals, hashCode, etc.), aumentando a produtividade.


*   **Kafka** → _Message broker_ utilizado para comunicação assíncrona entre microserviços.


*   **Mailtrap** → Sandbox para teste e visualização de emails enviados pela aplicação, sem envio real.


*   **SLF4J + Logback / SLF4J-Test** → Implementação de logs estruturados e biblioteca de teste para verificar saídas de log em cenários de teste.


*   **Mockito** → Framework para criação de _mocks_ e _stubs_ em testes unitários.


*   **Spring Boot Starter Test** → Fornece infraestrutura completa para testes de unidade e integração com o Spring Boot.


*   **Spring Kafka Test** → Utilizado para testar a comunicação e consumo de mensagens Kafka em ambiente controlado.


*   **JaCoCo** → Ferramenta de análise de cobertura de testes, integrada ao Maven, com exclusões configuradas para DTOs.


*   **SonarQube / SonarQuality** → Utilizado para análise estática de código, verificando qualidade, cobertura e boas práticas.


---

## 🏛️ Arquitetura

A API segue o padrão **MVC (Model–View–Controller)** adaptado ao contexto de **serviços REST** com **processamento assíncrono via Kafka**, garantindo organização, desacoplamento e facilidade de manutenção do código.

### **Model (Domain)**

*   Representa as **entidades centrais do negócio**, como Consult e Patient.


*   Define os atributos e comportamentos essenciais da aplicação, refletindo diretamente as regras de domínio.


### **Service**

*   Camada responsável pela **lógica de negócio** da aplicação.


*   Gerencia o **envio e consumo de mensagens Kafka**, o **disparo de e-mails** via JavaMailSender e o **processamento agendado** das consultas.


*   Implementa o princípio de responsabilidade única, mantendo o código desacoplado das camadas de transporte (DTOs e controladores).


### **Mapper**

*   Responsável por **converter objetos entre diferentes camadas**, especialmente entre:


  *   DTOs (camada de entrada/saída da API)


  *   Modelos de domínio (camada interna da aplicação)


*   Facilita a serialização e desserialização, evitando dependências diretas entre as camadas.


### **Consumer Kafka**

*   Atua como **ponto de integração assíncrona** com outros microsserviços.


*   Consome mensagens do tópico Kafka assim que a aplicação é iniciada.


*   Encaminha os dados recebidos para o ConsultService, que processa e agenda o envio de e-mails.


*   Implementado com **Spring Kafka** e **Reactor Kafka**, garantindo performance e resiliência no consumo de mensagens.


### **Scheduler**

*   Componente responsável por **monitorar e processar periodicamente** as mensagens armazenadas em fila interna (emailQueue).


*   Utiliza o @Scheduled do Spring para realizar o **envio automático de e-mails** em intervalos configuráveis.

---

## Estrutura de Diretórios 🏛️

A aplicação segue uma arquitetura organizada por camadas funcionais, separando claramente responsabilidades e domínios de negócio, o que facilita a manutenção, testes e evolução do sistema.

```
api/
├── common/ # Contém componentes genéricos e reutilizáveis que podem ser utilizados em diferentes partes da aplicação.
    └── annotation/  # Anotações personalizadas criadas para adicionar comportamentos específicos (ex.: validações customizadas, interceptores).
    └── validator/  # Classes responsáveis por validar regras de negócio associadas às anotações (ex.: validação de CPF, horário, formato de e-mail, etc.).
├── config/  # Agrupa as classes de configuração do Spring Boot e de bibliotecas externas. Inclui -> (Configuração de Kafka (producers, consumers, listeners), Configuração de e-mail (Mailtrap), Beans personalizados e propriedades globais da aplicação)).
├── domain/ # Camada de negócio da aplicação, representando o núcleo lógico e conceitual.
│   └── model/  # Modelos de domínio (Consult, Patient) -> Contém as entidades e modelos de domínio, como Consult e Patient. Essas classes representam os objetos principais do sistema e geralmente mapeiam tabelas ou estruturas do domínio.
├── entrypoint/ # Camada responsável por receber e expor dados — a “porta de entrada” da aplicação.
│   └── dto/    
        └── request/ # Objetos usados para receber dados enviados em requisições HTTP (ex.: criar ou atualizar uma consulta).
        └── response/ # Objetos usados para retornar dados nas respostas HTTP de forma estruturada e segura.
├── enums/ # Contém enumerações que representam valores fixos e controlados, como status de consulta
├── exception/            
└── service /             

```

### Request recebido (pelo producer) para o envio do e-mail 🏁

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
"statusConsult": "SCHEDULED"
}
```

**Response Body**:

```
{
"message": "Consulta enviada com sucesso para o Kafka!",
"id": "1",
"statusConsult": "SCHEDULED"
}
```
 
## Fluxo da aplicação:

---
A aplicação é um **serviço autônomo**, responsável por **consumir mensagens do Kafka** e **enviar notificações por e-mail** aos pacientes, de forma **assíncrona e automatizada**.

1.  **Inicialização da aplicação:**

  *   Ao iniciar, o Spring Boot carrega todos os beans e configura o **Kafka Listener** para escutar o tópico configurado (app.kafka.topics.consults).


  *   Não há endpoints REST — a aplicação entra em modo _listener_ e fica ativa aguardando novas mensagens na fila Kafka.

---
2.  **Consumo da fila Kafka:**

  *   O serviço ConsultService utiliza a anotação @KafkaListener para **escutar continuamente o tópico Kafka**.


  *   Sempre que uma nova mensagem chega, ela é **desserializada em um objeto Consult** e adicionada a uma **fila interna (emailQueue)** para processamento posterior.


  *   O _acknowledgment manual_ garante que a mensagem só é marcada como processada após ser corretamente adicionada à fila.

---

3.  **Agendamento do processamento:**

  *   A classe EmailScheduler, marcada com @Scheduled, é executada **a cada segundo** (fixedDelay = 1000).


  *   Esse agendador esvazia a fila interna (emailQueue) e processa os objetos Consult pendentes, delegando o envio para o serviço de e-mail.


  *   Caso ocorra algum erro de envio, o item é reenfileirado para nova tentativa, garantindo **resiliência** no processo.

---
4.  **Envio do e-mail:**

  *   O serviço EmailService constrói o conteúdo da mensagem com base no **status da consulta** (SCHEDULED, CARRIED\_OUT ou CANCELLED) e envia o e-mail através do **Mailtrap** usando o JavaMailSender.


  *   O corpo do e-mail é montado dinamicamente com informações como nome do paciente, profissional, data, hora e motivo.

---
5.  **Logs e monitoramento:**

  *   Todas as etapas — consumo do Kafka, processamento da fila e envio de e-mails — são **registradas via SLF4J/Logback**, permitindo auditoria e acompanhamento em tempo real do fluxo de mensagens.

```
flowchart TD

A --> [Início da aplicação] --> B[Kafka Listener ativo]


B -->|Mensagem recebida| C[ConsultService]

C --> D[Adiciona Consult na fila interna (emailQueue)]

D --> E[EmailScheduler executa a cada 1s]

E -->|Consulta disponível na fila| F[EmailService envia e-mail via Mailtrap]

F --> G[Confirma envio e registra log]

F -->|Falha no envio| H[Reenfileira a consulta para nova tentativa]
```

### 📨 Exemplo de Funcionamento

1) O Kafka recebe uma mensagem JSON com os dados da consulta.


2) O ConsultService converte o JSON em objeto Consult e adiciona na fila.


3) O EmailScheduler detecta a nova consulta e chama o EmailService.


4) O EmailService formata e envia o e-mail ao paciente via Mailtrap.


5) O envio é registrado no log e, se ocorrer falha, a mensagem é reenfileirada.


## Email Notification ✉️

- Emails são enviados usando **MailTrap**.
- O conteúdo do email muda conforme o status da consulta:
    - `SCHEDULED` → "Agendamento da Consulta"
    - `CARRIED_OUT` → "Realização da Consulta"
    - `CANCELLED` → "Cancelamento da Consulta"
- O corpo do email inclui:
    - Nome do paciente
    - Nome do profissional
    - Data, horário e motivo da consulta
    - Assinatura do sistema hospitalar


## ⚙️ Configurações da Aplicação (application.properties)

O arquivo application.properties define as propriedades essenciais de infraestrutura da aplicação, como a integração com **Apache Kafka** e o **Mailtrap (serviço SMTP de teste de e-mails)**.

### 🔸 **Configurações do Kafka**

```
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=group-consult
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.listener.missing-topics-fatal=false
spring.kafka.listener.concurrency=1
app.kafka.topics.consults=easyconsult-consult
app.kafka.groupid=group-consult
```

#### Explicação:

*   **spring.kafka.bootstrap-servers** → Endereço do servidor Kafka que a aplicação deve utilizar para consumir mensagens.


*   **spring.kafka.consumer.group-id** → Identificador do grupo de consumidores responsável por processar as mensagens do tópico.


*   **spring.kafka.consumer.auto-offset-reset** → Define o comportamento caso não haja _offset_ anterior; earliest indica que o consumo deve começar do início do tópico.


*   **Deserializers** → Convertendo as mensagens Kafka (chave e valor) de bytes para String.


*   **enable-auto-commit** → Habilita o commit automático dos offsets (no caso, as mensagens já processadas).


*   **missing-topics-fatal=false** → Impede que a aplicação falhe ao iniciar caso o tópico ainda não exista.


*   **listener.concurrency=1** → Define que haverá apenas um _listener thread_ consumindo mensagens do tópico.


*   **app.kafka.topics.consults** → Nome do tópico que a aplicação está escutando (easyconsult-consult).


*   **app.kafka.groupid** → Agrupa consumidores para o mesmo tópico, garantindo balanceamento de carga.


> 💡 **Comportamento esperado:** Assim que a aplicação é iniciada, o _listener Kafka_ (@KafkaListener) passa a escutar o tópico configurado.Caso existam mensagens pendentes, elas são imediatamente processadas e enviadas para a fila interna (emailQueue), para posterior disparo de e-mails.

### 🔸 **Configurações do Mailtrap (Envio de E-mails de Teste)**

```
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=587
spring.mail.username=35b7064a168033
spring.mail.password=3657f3d5308daf
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
spring.mail.properties.mail.smtp.ssl.trust=*

```

#### Explicação:

*   **spring.mail.host / port** → Endereço e porta do servidor SMTP do Mailtrap.


*   **spring.mail.username / password** → Credenciais geradas pelo Mailtrap para autenticação.


*   **mail.smtp.auth / starttls.enable** → Habilitam autenticação e criptografia TLS para segurança no envio.


*   **timeouts** → Definem tempos máximos de conexão, leitura e escrita, evitando travamentos.


*   ssl.trust=\ → Aceita certificados SSL de qualquer host (útil em ambientes de teste).

> 💡 **Comportamento esperado:** Cada mensagem Kafka recebida representa uma consulta a ser processada.Após o consumo, o EmailService usa as credenciais do Mailtrap para simular o envio real de e-mails aos pacientes.

### 🔸 **Configuração do Servidor**

```
server.port=8089
```

*   Define a porta onde a aplicação será executada localmente.


