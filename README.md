# Trabalho Final POO - Grupo 4

**COISAS QUE FALTAM SEREM FEITAS**

* Organizar a estrutura do projeto (Gabriel) - FEITO
* Arrumar os Daos (Gabriel) - FEITO
    * FuncionarioDao - FEITO
    * FolhaPagamentoDao - FEITO
* Leitura do txt
* Conexão com o BD
* Testar e sempre utilizar o git


**ORDEM DE COMANDOS GIT**

**1. Para puxar as alterações do github**
```
git pull
```

**2. Para empacotar e enviar as alterações**
```
git add .
git commit -m "Sua msg de commit"
git push
```


**Anotações para Lembrar**

- Funcionários serão inicializados com os descontos zerados, dentro do loop de leitura do txt a Main deve chamar a CalculoService para setar os atributos de Funcionario e, após isso, salvar no BD com o FuncionarioDao.
- Pergunta: tabelas serão com nome no singular ou plural (funcionarios e folha_pagamento)? 
