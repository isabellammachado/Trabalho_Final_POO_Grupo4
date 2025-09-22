# Trabalho Final POO - Grupo 4

**COISAS QUE FALTAM SEREM FEITAS**

* [x] Organizar a estrutura do projeto
* [x] Arrumar os Daos 
    * [x] FuncionarioDao 
    * [x] FolhaPagamentoDao 
* [x] Leitura do txt 
* [x] Conexão com o BD 
* [ ] Arrumar Métodos da CalculoService
* [ ] Criar Classe Main para orquestração do App


**ORDEM DE COMANDOS GIT**

**1. Entre no diretório raiz do projeto**

Exemplo:
```
cd xx/xx/Trabalho_Final_POO_Grupo4
```

**2. Comando para puxar as alterações do github**
```
git pull
```

**3. Comando para empacotar e enviar as alterações**
```
git add .
git commit -m "Sua msg de commit"
git push
```


**Anotações para Lembrar**

- Funcionários serão inicializados com os descontos zerados, dentro do loop de leitura do txt a Main deve chamar a CalculoService para setar os atributos de Funcionario e, após isso, salvar no BD com o FuncionarioDao.

