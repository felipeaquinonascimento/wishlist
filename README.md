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

## ☸️ Configuração do Kubernetes

1. Iniciar Minikube:
```bash
minikube start
```

2. Iniciar Minikube:
```bash
# Configure o Docker no Minikube
eval $(minikube docker-env)

# Construa a imagem
./gradlew bootBuildImage --imageName=wishlist-backend:latest
```

3. Criar namespace:
```bash
kubectl create namespace wishlist
```

4. Aplicar configurações do Kubernetes:
```bash
kubectl apply -f k8s/ -n wishlist
```

5. Listar recursos:
```bash
# Pods
kubectl get pods -n wishlist

# Serviços
kubectl get services -n wishlist
```

6. Ver logs de um pod:
```bash
kubectl logs <nome-do-pod> -n wishlist
```

7. Ver logs de um pod:
```bash
# Encaminhar porta
kubectl port-forward <nome-do-pod> 8080:8080 -n wishlist

# Ou usar o serviço do Minikube
minikube service wishlist-service -n wishlist
```

## 🔧 Comandos Úteis
```bash
# Listar todos os recursos
kubectl get all -n wishlist

# Descrição detalhada de um recurso
kubectl describe pod <nome-do-pod> -n wishlist

# Dashboard do Kubernetes
minikube dashboard
```
## 💻 Desinstalar/Limpar
```bash
# Deletar todos os recursos
kubectl delete namespace wishlist

# Parar Minikube
minikube stop

# Deletar cluster Minikube
minikube delete
```

## Decisões Arquiteturais

### Frameworks e Tecnologias
- **Spring Boot**: Escolhido pela produtividade e ecossistema robusto
- **MongoDB**: Flexibilidade para documentos de wishlist
- **Gradle**: Gerenciamento de dependências mais moderno que Maven

### Padrões Utilizados
- Clean Architecture
- Repository Pattern
- DTO Pattern para desacoplamento

### Considerações de Implementação
- Limite de 20 produtos por wishlist
- Tratamento de exceções centralizado
- Validações de integridade de dados


## 📄 Licença

Distribuído sob licença MIT. Veja o arquivo `LICENSE` para mais informações.

---

✨ **Pronto para usar!** A API está configurada para rodar imediatamente com Docker, garantindo consistência entre ambientes de desenvolvimento.