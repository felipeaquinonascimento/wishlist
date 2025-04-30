# Wishlist API - E-commerce

## 📌 Visão Geral

API REST desenvolvida para gerenciar listas de desejos (wishlist) em sistemas de e-commerce. Permite que clientes salvem produtos favoritos para consulta posterior, melhorando a experiência de compra.

## ✨ Principais Funcionalidades

- **Adição/Remoção** de produtos na wishlist
- **Consulta completa** dos itens salvos
- **Verificação rápida** se produto está na lista
- **Limite seguro** de 20 produtos por cliente

## 🛠 Stack Tecnológica

- **Backend**: Java 17 + Spring Boot 3.4.5
- **Banco de Dados**: MongoDB (NoSQL)
- **Build**: Gradle 8.3+
- **Containerização**: Docker + Docker Compose

## 🔍 Endpoints Disponíveis

```http
POST   /api/wishlist/{clientId}/products/{productId}
DELETE /api/wishlist/{clientId}/products/{productId} 
GET    /api/wishlist/{clientId}/products
GET    /api/wishlist/{clientId}/products/{productId}
```

## 🚀 Como Executar

### Pré-requisitos
- Docker 20.10+
- Java JDK 17 instalado

### Passo a Passo

1. Clone o repositório:
```bash
git clone [URL_DO_REPOSITORIO]
```

2. Acesse a pasta do projeto:
```bash
cd wishlist-api
```

3. Construa e inicie os containers:
```bash
docker-compose up --build -d
```

4. Acesse a API em:
```
http://localhost:8080/api/wishlist
```

## ⚙️ Configurações

As principais configurações estão no arquivo `application.yml`:

```yaml
server:
  port: 8080
spring:
  data:
    mongodb:
      uri: mongodb://db:27017/wishlist
```

## 📄 Licença

Distribuído sob licença MIT. Veja o arquivo `LICENSE` para mais informações.

---

✨ **Pronto para usar!** A API está configurada para rodar imediatamente com Docker, garantindo consistência entre ambientes de desenvolvimento.