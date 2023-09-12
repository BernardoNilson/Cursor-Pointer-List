# Trabalho de ALEST I
> 2º Semestre do curso de Ciência da Computação da **PUCRS**

> Algoritmos e Estruturas de Dados I com Prof. Marcio Pinho

### Execução
O único requisito é possuir Java (OpenJRE 11.0+ ou equivalente)

No diretório `/T1-Cursor`, execute o comando `java source.App`

### Objetivo
Criar um programa que “instale” dois cursores em uma lista encadeada ordenada e utilize estes cursores para otimizar o acesso à lista.

### Funções
O programa deverá ter funções para
- mover o cursor para frente ou para trás
- trocar os nodos apontados pelos cursores entre si
- remover um nodo apontado por um dos cursores
- remover um nodo a partir de seu valor
- inserir um nodo, mantendo a ordem da lista
- inserir um nodo antes de um cursor

### Especificações
A cada operação a lista deverá ser impressa e a posição de cada cursor deve ser assinalada como “C1” ou “C2”.

Ao realizar cada operação, o programa deve escolher o cursor mais próximo para iniciar a operação. Este cursor pode ser o HEAD, C1 ou C2. O resultado desta escolha deve ser informado ao usuário.

### Implementação
Para criar a lista, pode ser usado o método de inserção ordenada já disponível na classe de listas disponível no Moodle.

Para as demais funções do programa, devem ser criados novos códigos, não podendo ser chamadas os métodos já existentes na classe de lista vista em aula.

Deve existir um método para cada função do programa.

Não é permitido o uso de classes do nativas do JAVA para a criação da lista.

### Configurações
O programa foi feito através do Visual Studio Code e testado em máquinas Ubuntu 22.04 e Windows 10.

Sobre o Java:
> OpenJDK version "11.0.20.1" 2023-08-24

> OpenJDK Runtime Environment (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04)

> OpenJDK 64-Bit Server VM (build 11.0.20.1+1-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)