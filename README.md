# API Biblioteca (Java + Spring Boot)

API REST de um sistema de biblioteca. Etapa atual: CRUD + regra básica de empréstimo.

## Como rodar
```bash
./mvnw spring-boot:run
```
API em `http://localhost:8080`. Console do H2 em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:bibliotecadb`, user `sa`, senha vazia).

## Entidades
- **Autor**: nome, nacionalidade
- **Livro**: titulo, isbn (único), anoPublicacao, disponivel, autor
- **Usuario**: nome, cpf (único), email
- **Emprestimo**: livro, usuario, dataEmprestimo, dataPrevistaDevolucao (14 dias), dataDevolucao

## Endpoints
| Recurso | Métodos |
|---|---|
| `/api/autor` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| `/api/livro` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| `/api/usuario` | GET, GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| `/api/emprestimo` | GET, GET `/em-aberto`, GET `/{id}`, POST, PUT `/{id}/devolver`, DELETE `/{id}` |

## Exemplo de uso
```bash
curl -X POST localhost:8080/api/autor -H "Content-Type: application/json" \
  -d '{"nome":"Machado de Assis","nacionalidade":"Brasileira"}'

curl -X POST localhost:8080/api/livro -H "Content-Type: application/json" \
  -d '{"titulo":"Dom Casmurro","isbn":"9788535910663","anoPublicacao":1899,"autor":{"id":1}}'

curl -X POST localhost:8080/api/usuario -H "Content-Type: application/json" \
  -d '{"nome":"Maria Silva","cpf":"12345678900","email":"maria@email.com"}'

curl -X POST localhost:8080/api/emprestimo -H "Content-Type: application/json" \
  -d '{"livro":{"id":1},"usuario":{"id":1}}'

curl -X PUT localhost:8080/api/emprestimo/1/devolver
```

## Regras de negócio
- Só é possível emprestar um livro com `disponivel = true`; ao emprestar ele passa a `false`.
- Ao devolver, o livro volta a ficar disponível.
- Não dá para devolver duas vezes o mesmo empréstimo.
- Erros retornam JSON `{"erro": "..."}`: 404 (não encontrado) e 409 (regra violada / dado duplicado / registro em uso).
