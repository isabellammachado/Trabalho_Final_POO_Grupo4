
# Diagrama UML- Grupo 4
<img width="1920" height="1080" alt="3" src="https://github.com/user-attachments/assets/011e10d8-bcb0-47d7-955e-74081a5a833d" />
<img width="1920" height="1080" alt="2" src="https://github.com/user-attachments/assets/662ef58c-2059-478e-b133-8d5d1179aca8" />

# Banco de Dados - Grupo 4
<img width="1919" height="1079" alt="Banco de dados2_Grupo4" src="https://github.com/user-attachments/assets/d1068ff2-c0f1-4eed-8983-417be96f8973" />
<img width="1919" height="1079" alt="Banco de dados_Grupo4" src="https://github.com/user-attachments/assets/0233eaef-1242-4fd4-b648-a3f04acca624" />

# Arquivos TXT - Grupo 4
# Exemplo Funcionarios para importação
<img width="412" height="610" alt="Captura de tela 2025-09-23 214413" src="https://github.com/user-attachments/assets/ef6ba2e8-7280-4e6e-8e10-dd242f0d2133" />

# Exemplo Funcionarios exportados após tratamento no banco de dados
<img width="433" height="394" alt="Captura de tela 2025-09-23 214359" src="https://github.com/user-attachments/assets/0eb620a2-7b26-44ee-8880-571b71f1fc76" />





# Trabalho Final POO - Grupo 4

**COISAS QUE FALTAM SEREM FEITAS**

* [x] Organizar a estrutura do projeto
* [x] Arrumar os Daos 
    * [x] FuncionarioDao 
    * [x] FolhaPagamentoDao 
* [x] Leitura do txt 
* [x] Conexão com o BD 
* [x] Arrumar Métodos da CalculoService
* [x] Criar Classe Main para orquestração do App


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

- Perguntar: ListagemFuncionario está correta?
- Perguntar: View ou Application deve orquestrar o sistema.
- Sobre o Código do Funcionario: deixar do jeito q está, retirar ou alterar;
