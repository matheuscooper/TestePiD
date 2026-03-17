# Industrial Production Manager

Sistema de gestão de produção industrial com sugestão inteligente de otimização baseada em estoque de matérias-primas.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Executando a Aplicação](#executando-a-aplicação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [API Endpoints](#api-endpoints)

## 🎯 Sobre o Projeto

O AutoFlex é um sistema completo de gestão de produção industrial que permite:

- Cadastro e gerenciamento de produtos
- Controle de matérias-primas e estoque
- Sugestão inteligente de produção otimizada
- Visualização de métricas e indicadores

## 📦 Pré-requisitos

Antes de começar, você precisará ter instalado:

### Obrigatórios

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) (apenas para o banco de dados)
- [Java JDK 21](https://adoptium.net/) ou superior
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- [Node.js 20+](https://nodejs.org/) e npm

## 🔧 Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/autoflex.git
cd autoflex
```

### 2. Inicie o banco de dados Oracle

```bash
# Na raiz do projeto (onde está o docker-compose.yml)
docker-compose up -d

# Verificar se está rodando
docker-compose ps

# Ver logs do banco
docker-compose logs -f oracle
```

⏳ **Aguarde cerca de 1-2 minutos** para o banco inicializar completamente.

### 3. Configure o Backend

```bash
cd backend

# O arquivo application.properties já está configurado para:
# - Oracle em localhost:1521
# - Usuário: app
# - Senha: app
# - Banco: FREEPDB1
```

### 4. Configure o Frontend

```bash
cd frontend/autoflex-web/client

# Criar arquivo .env
echo "VITE_API=http://localhost:8080" > .env

# Instalar dependências
npm install
```

## ▶️ Executando a Aplicação

### Ordem de inicialização:

#### 1. **Banco de Dados** (se ainda não estiver rodando)

```bash
# Na raiz do projeto
docker-compose up -d
```

#### 2. **Backend** (Terminal 1)

```bash
cd backend

# Modo desenvolvimento (com hot reload)
./mvnw quarkus:dev


```

✅ **Backend disponível em:** http://localhost:8080

**Endpoints importantes:**

- API: http://localhost:8080/api/products
- Health Check: http://localhost:8080/q/health
- Dev UI: http://localhost:8080/q/dev

#### 3. **Frontend** (Terminal 2)

```bash
cd frontend/autoflex-web/client

# Iniciar servidor de desenvolvimento
npm run dev
```

✅ **Frontend disponível em:** http://localhost:3001 ou http://localhost:5173

### 🎉 Pronto!

Acesse a aplicação em: **http://localhost:3001**

## 🏗️ Estrutura do Projeto

```
autoflex/
├── backend/                    # Backend Quarkus (Java)
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/autoflex/
│   │       │       ├── entity/        # Entidades JPA
│   │       │       ├── resource/      # Controllers REST
│   │       │       ├── service/       # Lógica de negócio
│   │       │       └── filter/        # CorsFilter
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
├── frontend/                   # Frontend Vue
│   └── autoflex-web/
│       └── client/
│           ├── src/
│           │   ├── components/       # Componentes Vue
│           │   ├── pages/           # Páginas/Views
│           │   ├── services/        # API services
│           │   ├── router/          # Rotas
│           │   └── main.ts
│           ├── package.json
│           └── .env
│
└── docker-compose.yml          # Configuração do Oracle DB
```

## ✨ Funcionalidades

### Dashboard

- Visão geral de produtos cadastrados
- Total de matérias-primas disponíveis
- Valor total de produção possível
- Sugestão de produtos para fabricar

### Produtos

- ✅ Listar todos os produtos
- ✅ Criar novo produto
- ✅ Editar produto existente
- ✅ Deletar produto
- ✅ Adicionar matérias-primas ao produto
- ✅ Visualizar detalhes do produto

### Matérias-Primas

- ✅ Listar todas as matérias-primas
- ✅ Criar nova matéria-prima
- ✅ Editar matéria-prima
- ✅ Deletar matéria-prima
- ✅ Controle de estoque

### Sugestão de Produção

- 📊 Algoritmo inteligente de otimização
- 💰 Maximização do valor de produção
- 📦 Análise de disponibilidade de estoque
- 🎯 Sugestão de quantidade ideal por produto

## 🔌 API Endpoints

### Produtos

```
GET    /api/products                        # Listar produtos
GET    /api/products/{id}                   # Buscar produto por ID
POST   /api/products                        # Criar produto
PUT    /api/products/{id}                   # Atualizar produto
DELETE /api/products/{id}                   # Deletar produto
POST   /api/products/{id}/raw-materials     # Adicionar matéria-prima
```

### Matérias-Primas

```
GET    /api/raw-materials         # Listar matérias-primas
GET    /api/raw-materials/{id}    # Buscar por ID
POST   /api/raw-materials         # Criar
PUT    /api/raw-materials/{id}    # Atualizar
DELETE /api/raw-materials/{id}    # Deletar
```

### Produção

```
GET    /api/production/suggestion # Obter sugestão de produção
```

## 🛠️ Comandos Úteis

### Backend

```bash
# Compilar
./mvnw clean compile

# Executar testes
./mvnw test

# Build de produção
./mvnw clean package

# Limpar
./mvnw clean
```

### Frontend

```bash
# Instalar dependências
npm install

# Desenvolvimento
npm run dev

# Build de produção
npm run build

# Preview da build
npm run preview
```

### Banco de Dados

```bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Ver logs
docker-compose logs -f oracle

# Reiniciar
docker-compose restart oracle
```

## 📝 Variáveis de Ambiente

### Backend (`application.properties`)

```properties
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
quarkus.datasource.username=app
quarkus.datasource.password=app
quarkus.http.port=8080
```

### Frontend (`.env`)

```env
VITE_API=http://localhost:8080
```

## 👨‍💻 Autor

Desenvolvido por **Matheus**

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
