# API Biblioteca

API REST desenvolvida em Java com Spring Boot para gerenciar autores, livros, usuários e empréstimos.

## Como executar

1. Tenha o **Java 21** instalado.
2. Abra o projeto em uma IDE ou pelo terminal.
3. Execute a classe `BibliotecaApplication`:
4. Acesse a API em `http://localhost:8080` e faça os testes pelo Postman.
5. Acesse o banco de dados H2 em `http://localhost:8080/h2-console`

## Configuração do Adapter de CEP

O sistema permite consultar endereços usando **ViaCEP** ou **BrasilAPI**. Para escolher, altere esta linha em `src/main/resources/application.properties`:

```properties
cep.provedor=brasilapi
```

Para usar o ViaCEP, troque por:

```properties
cep.provedor=viacep
```

**Reinicie a aplicação** após mudar o provedor. A consulta pode ser testada em:

```http
GET http://localhost:8080/cep/85301000
```

## Padrões de projeto utilizados

- **Singleton (Logger):** o `SpringAppLogger` implementa `ILogger` e é gerenciado pelo Spring como um componente singleton. Ele registra informações e erros da API. O projeto também possui a classe `AppLogger`, com implementação clássica de Singleton.
- **Adapter:** `ViaCepAdapter` e `BrasilApiAdapter` implementam a interface `ConsultaCep`, permitindo consultar provedores diferentes e retornar um objeto `Endereco`.
- **Proxy:** `ConsultaCepProxy` valida o CEP e guarda consultas já realizadas em um cache simples na memória, evitando repetir chamadas ao provedor. O cache é reiniciado junto com a aplicação.
- **Strategy:** `MultaPadrao` e `MultaLimitada` implementam `CalculadoraMulta`, permitindo escolher a forma de calcular multas dos empréstimos.
- **State:** `EstadoAtivo`, `EstadoAtrasado` e `EstadoDevolvido` implementam `EstadoEmprestimo` e controlam as regras de devolução conforme a situação do empréstimo.

## Empréstimos e devoluções

Um empréstimo só pode ser criado se o livro estiver disponível. O prazo de devolução é de **14 dias**.

Para criar um empréstimo, use:

```http
POST http://localhost:8080/api/emprestimo
```

```json
{
  "livro": { "id": 1 },
  "usuario": { "id": 1 },
  "tipoMulta": "PADRAO"
}
```

Os IDs de livro e usuário precisam existir. Em `tipoMulta`, escolha:

- `PADRAO`: R$ 2,00 por dia de atraso.
- `LIMITADA`: R$ 2,00 por dia, com limite de R$ 20,00.

Para consultar e devolver empréstimos:

```http
GET http://localhost:8080/api/emprestimo
GET http://localhost:8080/api/emprestimo/em-aberto
PUT http://localhost:8080/api/emprestimo/1/devolver
```

Na devolução, o sistema calcula a multa quando há atraso e libera o livro novamente. O State impede que o mesmo empréstimo seja devolvido duas vezes.

## Organização e SOLID

O projeto separa **Controller**, **Service**, **Repository** e **Entity**. As regras de negócio ficam no Service ou nas classes específicas dos padrões. As interfaces ajudam a reduzir o acoplamento, e as classes de Strategy e State mantêm responsabilidades separadas, facilitando futuras alterações.

As operações importantes são registradas pelo Logger. O CRUD, o Adapter e o Proxy continuam fazendo parte da aplicação.
