# Customer Microservice Application

Microservice para criação, consulta, deleção e atualização de fornecedores.

## Tecnologias / Recursos Utilizados

1. Springboot(3.1.5)
2. Swagger Ui
3. Mockito
4. Junit5
5. MariaDB

## Desenvolvimento

Para configurar o ambiente de desenvolvimento, é necessário seguir os seguintes passos abaixo: 

###  Criação do Banco de Dados MariaDB

Para criar o banco MariaDB, utilize o Docker Compose disponível na raiz do projeto; ele irá criar o banco com os parâmetros corretos de configuração.

``` 
docker-compose -f docker-compose.yml -p customer up -d
```

### Execução da Aplicação

1. Realize a compilação do arquivo .jar: 

```
mvn clean install 
```

2. Execute a aplicação no command line: 

```
java -jar target/customer-1.0.0.jar 
```

3. Os testes de Endpoint podem ser realizados através de: 

* Postman / Insomina (ou outro software pertinente)
* Navegador (em caso de requisições no tipo **GET**)
* Interface Swagger (Veja proximo topico para utilização)
* Diversos outros métodos no qual seja possível processar requsições HTTP

### Swagger UI

A Appplicação possui interface com o Swagger UI, através da Open API 3.0, para executá-lo você deve: 

1. Execute a Aplicação através do command line: 

```
mvn clean install 
java -jar target/customer-1.0.0.jar 
```

2. Abra a URL

Abra a URL no seu navegador de preferência: http://localhost:8080/swagger-ui/index.html

###  Execução de Testes

O Projeto conta com testes unitários feitos utilizando JUNIT5 e Mockito. O Report é feito através do Jacoco, para executa-los 
e gerar o report: 

```
mvn clean install test jacoco:report
```

Ele irá gerar um report .html localizado em: 

- target
  - site
    - jacoco
      - index.html

Abra em um navegador da sua preferência, e será possível verificar a execução dos testes e sua cobertura
