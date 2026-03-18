# 🚀 Blog App - Fullstack Project

Este é um projeto de rede social/blog desenvolvido como  **Projeto Pessoal**. O sistema conta com autenticação JWT, perfis editáveis com upload de fotos e backend em Spring Boot.

## 🛠 Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3, Spring Security (JWT), Hibernate, PostgreSQL/MySQL.
* **Frontend:** Next.js 14 (App Router), Tailwind CSS, Lucide React.
* **Infra:** Docker (opcional), File System para uploads.

---

## 📂 Estrutura de Pastas

/blog
├── backend/          # API Spring Boot
├── frontend/         # Aplicação Next.js
└── uploads/          # Pasta externa para fotos de perfil

# Documentação
O projeto conta com acesso de Api's e Teste em:
http://localhost:8080/swagger-ui/index.html
ou a porta backend configurada

🚀 Como Executar o Projeto
1. Pré-requisitos

    Java 17+ instalado.

    Node.js 18+ instalado.

    Banco de Dados (Postgres ou MySQL) rodando.

2. Configuração do Backend (Java)

    Navegue até a pasta backend/.

    Crie as pastas de upload no seu sistema:

        Linux: mkdir -p /home/seu-usuario/blog/uploads/photos

        Windows: C:/blog/uploads/photos

    No arquivo src/main/resources/application.properties, ajuste as credenciais do banco e o caminho da pasta:
    Properties

app.upload.dir=/caminho/para/sua/pasta/uploads/photos
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

Execute o projeto via IntelliJ ou terminal:
Bash

    ./mvnw spring-boot:run

3. Configuração do Frontend (Next.js)

    Navegue até a pasta frontend/.

    Instale as dependências:
    Bash

npm install

Certifique-se de que a API está rodando em http://localhost:8080.

Inicie o servidor de desenvolvimento:
Bash

    npm run dev

    Acesse http://localhost:3000.

🔐 Variáveis de Ambiente e Segurança

    JWT Secret: Configure a chave mestra no application.properties.

    CORS: O backend está configurado para aceitar requisições de http://localhost:3000.

📸 Sistema de Upload

As fotos de perfil são salvas fora do diretório do projeto para evitar perda de dados durante deploys. Certifique-se de que o usuário que executa o Java tem permissão de escrita na pasta de uploads.
✒️ Autor

    Francisco  - Aluno de ADS na FATEC Mogi Mirim.
