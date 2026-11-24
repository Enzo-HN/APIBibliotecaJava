package com.aula.biblioteca.controllers;

import com.aula.biblioteca.cep.ConsultaCep;
import com.aula.biblioteca.cep.Endereco;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// eu fiz um controller mas depois coloquei no usuario então ficou meio sem sentido. ficou aqui só para teste mesmoOBJETIVO
//A equipe deverá adicionar uma nova funcionalidade relevante ao sistema, aplicando padrões comportamentais e preservando a arquitetura desenvolvida nas atividades anteriores.
//
//PARTE 1 — Evolução da aplicação
//Escolha uma funcionalidade relevante para o domínio do seu sistema e implemente-a na API.
//Exemplos:
//
//    Biblioteca: implementar o fluxo de empréstimo e devolução de livros, considerando disponibilidade, prazos e multas.
//    Restaurante: implementar diferentes estratégias de cálculo de entrega, descontos ou taxas de serviço.
//    Eventos: implementar o fluxo de inscrição, confirmação, cancelamento e controle de vagas.
//    Controle Financeiro: implementar diferentes estratégias de categorização, cálculo de orçamento ou alertas de gastos.
//    Museu: implementar o fluxo de agendamento de visitas, controle de exposições ou gerenciamento de disponibilidade de peças.
//
//A funcionalidade deverá possuir regras de negócio próprias, não sendo suficiente implementar apenas mais um CRUD.
//
//PARTE 2 — Aplicação de Design Patterns comportamentais
//A equipe deverá implementar manualmente pelo menos dois padrões comportamentais GoF, escolhidos conforme a necessidade da funcionalidade desenvolvida.
//Padrões disponíveis:
//
//    Strategy;
//    Observer;
//    State;
//    Command;
//    Template Method;
//    Memento;
//    Iterator.
//
//Exemplos de aplicação:
//
//    Strategy: permitir diferentes estratégias de cálculo de multas, descontos, taxas ou preços, sem precisar modificar a classe responsável pela operação.
//    Observer: notificar diferentes componentes quando ocorrer um evento importante no sistema, como uma nova inscrição, um empréstimo ou uma alteração de pedido.
//    State: controlar o ciclo de vida de um objeto, impedindo operações incompatíveis com seu estado atual. Por exemplo, um empréstimo não pode ser devolvido se ainda não foi realizado.
//    Command: encapsular operações do sistema em objetos independentes, permitindo organizar a execução de ações e separar quem solicita uma operação de quem a executa.
//
//A implementação deverá:
//
//    Utilizar classes e interfaces próprias para representar o padrão.
//    Evitar implementações artificiais, nas quais o padrão não resolve um problema real.
//    Manter as responsabilidades das classes bem definidas.
//    Demonstrar como o padrão se integra à arquitetura existente.
//    Registrar as principais operações utilizando o AppLogger.
//
//A equipe deverá explicar por que escolheu cada padrão e qual problema sua utilização resolve.
//
//PARTE 3 — Qualidade e arquitetura
//A nova funcionalidade deverá respeitar os princípios SOLID estudados durante o bimestre.
//Além disso:
//
//    O CRUD desenvolvido anteriormente deverá continuar funcionando.
//    O Singleton Logger deverá permanecer integrado.
//    Os padrões Adapter e Proxy desenvolvidos anteriormente deverão continuar funcionando.
//    As regras de negócio deverão permanecer concentradas na camada Service ou em componentes específicos do domínio.
//    Os Controllers não deverão concentrar regras de negócio.
//    As dependências deverão ser organizadas adequadamente, evitando acoplamento desnecessário.
//
//ENTREGA
//A equipe deverá disponibilizar:
//
//    Link do repositório GitHub atualizado.
//    Código-fonte completo da aplicação.
//    README.md atualizado, contendo instruções para executar o projeto e explicações sobre os padrões utilizados.
//
//APRESENTAÇÃO
//Cada equipe deverá demonstrar a nova funcionalidade funcionando e explicar:
//
//    Qual problema a funcionalidade resolve.
//    Quais padrões foram utilizados e por quê.
//    Como os padrões foram implementados.
//    Quais princípios SOLID foram aplicados.
//    Quais melhorias foram obtidas em relação à implementação.
//    Quais dificuldades foram encontradas durante o desenvolvimento.
@RestController
@RequestMapping("/cep")
public class CepController {

    private final ConsultaCep consultaCep;

    public CepController(ConsultaCep consultaCep) {
        this.consultaCep = consultaCep;
    }

    @GetMapping("/{cep}")
    public Endereco buscarCep(@PathVariable String cep) {
        return consultaCep.buscar(cep);
    }
}