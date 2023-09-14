package source;
/**
 * Esta é a classe principal do Trabalho de ALEST I com o Prof. Marcio Pinho
 * 
 * @author Bernardo Nilson - PUCRS
 */

public class LinkedListOfInteger {

    // Node é uma classe interna de LinkedListOfInteger
    private class Node {

        // Atributos de Node
        protected Integer element;
        protected Node next;

        /**
         * Método construtor do Node
         * @param elemento a ser inserido no Node
         */
        public Node(Integer element) {
            this.element = element;
            next = null;
        }      
    }

    // Atributos de LinkedListOfInteger
    // Referência para o primeiro elemento e para o último elemento
    private Node head;
    private Node tail;
    // Node para facilitar a movimentação pela lista
    private Node cursorTwo;
    private Node cursorOne;
    // Posições dos cursores para facilitar a escolha da melhor posição ao fazer uma varredura
    private int positionOne;
    private int positionTwo;
    // Quantidade de elementos que a lista possui
    private int count;
    // Controle de quantas vezes avançamos na lista
    private int moving;

    /**
     * Método construtor da classe LinkedListOfInteger
     * @param elemento a ser inserido
     */
    public LinkedListOfInteger() {
        head = null;
        tail = null;
        cursorOne = null;
        cursorTwo = null;
        positionOne = -1;
        positionTwo = -1;
        count = 0;
        moving = 0;
    }

    /**
     * @return int - quantidade de elementos
     */
    public int getCount() {
        return count;
    }

    /**
     * @return Node
     */
    public Node getCursorOne() {
        return cursorOne;
    }

    /**
     * @return Node
     */
    public Node getCursorTwo() {
        return cursorTwo;
    }

    /**
     * @return int
     */
    public int getPositionOne() {
        return positionOne;
    }

    /**
     * @return int
     */
    public int getPositionTwo() {
        return positionTwo;
    }

    /**
     * @return int
     */
    public int getMoving() {
        return moving;
    }
    public void resetMoving() {
        moving = 0;
    }
    
    /**
     * Método altera a posição dos cursores para HEAD
     */
    public void startCursorPosition() {
        cursorOne = head;
        cursorTwo = head;
        positionOne = 0;
        positionTwo = 0;
    }
    
    /**
     * @return verdadeiro, caso a lista esteja vazia
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Método esvazia a lista
     */
    public void clear() {
        head = null;
        cursorOne = null;
        cursorTwo = null;
        tail = null;
        positionOne = -1;
        positionTwo = -1;
        count = 0;
    }    

    /**
     * Método insere um elemento em uma determinada posição da lista
     * @param index do elemento a ser inserido
     * @param element a ser inserido
     * @throws IndexOutOfBoundsException se index < 0 OR index > quantidade de elementos na lista
     */
    private void add(int index, Integer element) {
        // Verifica se index é válido
        if (index < 0 || index > getCount()) throw new IndexOutOfBoundsException();
        
        // Node auxiliar
        Node aux = new Node(element);
        
        // Inserção no início da lista
        if (index == 0) {
            if (isEmpty()){
                tail = aux;
                cursorOne = aux;
                cursorTwo = aux;
            } else {
                aux.next = head;
            }
            head = aux;
        }
        // Inserção no final da lista
        else if (index == count) {
            tail.next = aux;
            tail = aux;
        }
        // Inserção no meio da lista
        else {
            // Vasculha a lista em busca do elemento anterior à posição desejada
            Node previous = bestCursor(index - 1);
            int previousPosition = bestCursorPosition(index - 1);
              
            for(int i = 0; i < index - 1 - previousPosition; i++){ // quantas vezes o cursor precisa andar
                previous = previous.next;
                moving++;
            }
            aux.next = previous.next;
            previous.next = aux;
        }
        count++;
        updateCursorsPosition(index, 1);
    }

    /**
     * Método adiciona um elemento mantendo a ordem crescente da lista
     * @param elemento a ser adicionado na lista
     */
    public void addIncreasingOrder(Integer element){
        // Declarações
        Node aux = isEmpty() ? head : bestCursor(element);
        // posição auxiliar
        int position = 0;
        if (aux == getCursorOne() && getPositionOne() > 0) position = getPositionOne();
        else if (aux == getCursorTwo() && getPositionOne() > 0) position = getPositionTwo();

        // Procura em qual índice o elemento precisa ser inserido
        while (position <= count && aux != null) {
            if (aux.element > element) break;
            position++;
            aux = aux.next;
            moving++;
        }
        add(position, element);
    }


    /**
     * Método adiciona um elemento na sua posição anterior ao cursor selecionado
     * @param elemento a ser adicionado na lista
     * @param choice - 1 para CURSOR 1 ou 2 para CURSOR 2
     * @throws IllegalArgumentException se choice != 1 OR choice != 2
     */
    public void addByCursor(int choice, int element){
        // Tratamento de exceção
        if (choice != 1 && choice != 2) throw new IllegalArgumentException("Choice: 1 para CURSOR 1 ou 2 para CURSOR 2");

        // Declarações
        int position = (choice == 1) ? positionOne : positionTwo;
        
        // Temos um método pronto de adição
        add(position, element);

        // Move o cursor para a posição recém adicionada
        moveCursorBackward(choice, 1);
    }
    
    /**
     * Método de exibição da lista
     * @return String
     */
    public String toString() {

        Node aux = head;
        String result = "[ ";

        while (aux != null) {
            result += aux.element.toString();
            if (cursorOne == aux) result += "-C1";
            if (cursorTwo == aux) result += "-C2";
            result += " ";
            aux = aux.next;
        }
        result += "]";
        return result;
    }    

    /**
     * Método move um cursor para a próxima posição da lista, se não houver, permanece no final da lista
     * Caso queira ultrapassar o limite da lista, o cursor permanece na última posição
     * @param jump - Quantas casas o cursor vai se mover
     * @param choice - 1 para CURSOR 1 ou 2 para CURSOR 2
     * @throws IllegalArgumentException se choice != 1 OR choice != 2
     * @throws IndexOutOfBoundsException se jump for negativo
     */
    public void moveCursorForward(int choice, int jump){
        // Tratamento da execeção
        if (jump < 0) throw new IndexOutOfBoundsException("Você inseriu uma posição ilegal!");
        if (choice != 1 && choice != 2) throw new IllegalArgumentException("Choice: 1 para CURSOR 1 ou 2 para CURSOR 2");

        // Declarações
        int position = (choice == 1) ? positionOne : positionTwo;
        
        // Mover o cursor
        // Recebe uma posição, sendo o máximo o último elemento da lista
        int finalPosition = (position + jump < getCount()) ? position + jump : getCount() - 1;
        Node aux = bestCursor(finalPosition);
        int auxPosition = bestCursorPosition(finalPosition);
        
        // quantas casas vai precisar andar
        for(int i = auxPosition; i < finalPosition; i++){
            aux = aux.next;
            moving++;
            auxPosition++;
        }

        // Atribuições
        if (choice == 1){
            cursorOne = aux;
            positionOne = auxPosition;
        } else {
            cursorTwo = aux;
            positionTwo = auxPosition;
        }
    }

    /**
     * Método move um cursor para a posição anterior da lista, se não houver, permanece no ínicio da lista
     * Caso queira ultrapassar o limite da lista, o cursor permanece na primeira posição
     * @param jump - Quantas casas o cursor vai se mover
     * @param choice - 1 para CURSOR 1 ou 2 para CURSOR 2
     * @throws IllegalArgumentException se choice != 1 OR choice != 2
     * @throws IndexOutOfBoundsException se jump for negativo
     */
    public void moveCursorBackward(int choice, int jump){
        // Tratamento da execeção
        if (jump < 0) throw new IndexOutOfBoundsException("Você inseriu uma posição ilegal!");
        if (choice != 1 && choice != 2) throw new IllegalArgumentException("Choice: 1 para CURSOR 1 ou 2 para CURSOR 2");

        // Declarações
        int position = (choice == 1) ? positionOne : positionTwo;

        // Mover o cursor 
        // Recebe uma posição, sendo o máximo o primeiro elemento da lista
        int finalPosition = (position - jump >= 0) ? position - jump : 0;
        Node aux = bestCursor(finalPosition);
        int auxPosition = bestCursorPosition(finalPosition); 

        //                -> quantas casas vai precisar andar
        for (int i = auxPosition; i < finalPosition; i++){
            aux = aux.next;
            moving++;
            auxPosition++;
        }
        
        // Atribuições
        if (choice == 1){
            cursorOne = aux;
            positionOne = auxPosition;
        } else {
            cursorTwo = aux;
            positionTwo = auxPosition;
        }
    }

    /**
     * Método troca entre si os Nodes apontados pelos cursores
     * @throws NullPointerException se (cursorOne == null) OR (cursorTwo == null)
     */
    public void switchNodePositions(){
        // Tratamento de exceção
        if ((cursorOne == null) || (cursorTwo == null)) throw new NullPointerException("Não há dois cursores para trocar. Tente novamente.");

        // Troca os Nodes de posição
        if (cursorOne != cursorTwo){
            // Posição do anterior ao CURSOR 1
            int previousOnePosition = positionOne - 1;
            if (previousOnePosition < 0) previousOnePosition = 0;
            
            // Define o melhor ponteiro para varredura, considerando a posição do anterior
            Node previousOne = null;
            Node currentOne = bestCursor(previousOnePosition);

            // Loop para chegar até o CURSOR 1, anterior acompanha
            while (currentOne.next != null && currentOne != cursorOne) {
                previousOne = currentOne;
                currentOne = currentOne.next;
                moving++;
            }
            
            // Posição do anterior ao CURSOR 2
            int previousTwoPosition = positionTwo - 1;
            if (previousTwoPosition < 0) previousTwoPosition = 0;
            // Define o melhor ponteiro para varredura, considerando a posição do anterior
            Node previousTwo = null;
            Node currentTwo = bestCursor(previousTwoPosition);

            // Loop para chegar até o CURSOR 2, anterior acompanha
            while (currentTwo.next != null && currentTwo != cursorTwo) {
                previousTwo = currentTwo;
                currentTwo = currentTwo.next;
                moving++;
            }

            // Faz as alterações, caso um dos cursores seja o HEAD
            if (previousOne == null) head = currentTwo;
            else previousOne.next = currentTwo;

            if (previousTwo == null) head = currentOne;
            else previousTwo.next = currentOne;

            // Faz as alterações, caso um dos cursores seja o TAIL
            if (currentOne.next == null) tail = currentTwo;
            if (currentTwo.next == null) tail = currentOne;

            // Faz as alterações entre os Nodes
            Node temp = currentOne.next;
            currentOne.next = currentTwo.next;
            currentTwo.next = temp;

            // Atualiza as posições dos cursores
            int aux = positionOne;
            positionOne = positionTwo;
            positionTwo = aux;
        }
    }

    /**
     * Método remove o elemento indicado por um dos cursores
     * @param choice - 1 para CURSOR 1 ou 2 para CURSOR 2
     * @throws IllegalArgumentException se choice != 1 OR choice != 2
     * @return true, caso seja removido com sucesso
     */
    public boolean remove(int choice) {
        // Tratamento de exceção
        if (choice != 1 && choice != 2) throw new IllegalArgumentException("Choice: 1 para CURSOR 1 ou 2 para CURSOR 2");

        // Testa se a lista está vazia
        if (count == 0) return false;
        else if (count == 1){ // Testa se a lista tinha somente um elemento
            clear();
            return true;
        }

        Node cursor, cursorSec;
        int position, positionSec;
        if (choice == 1){
            cursor = cursorOne;
            position = positionOne;
            cursorSec = cursorTwo;
            positionSec = positionTwo;
        } else {
            cursor = cursorTwo;
            position = positionTwo;
            cursorSec = cursorOne;
            positionSec = positionOne;
        }

        // Remoção do primeiro da lista
        if (cursor == head) {
            head = head.next;
            if (cursor == cursorSec){
                cursorSec = head;
                positionSec = 0;
            }
            cursor = head;
            position = 0;
        } else {
            // Declarações
            Node previous = bestCursor(position - 1);
            Node aux = previous.next;

            // Posicionados os Nodes auxiliares nas suas posições
            while (aux != cursor){
                previous = aux;
                aux = aux.next;
                moving++;
            }
            // Remoção no final da lista
            if (aux == tail){
                tail = previous;
                tail.next = null;
                if (cursor == cursorSec){
                    cursorSec = tail;
                }
                cursor = tail;
            } else {
                previous.next = aux.next;
                cursor = previous;
                if (cursor == cursorSec){
                    cursorSec = previous;
                }
            }
        }

        if (choice == 1){
            cursorOne = cursor;
            positionOne = position;
            cursorTwo = cursorSec;
            positionTwo = positionSec;
        } else {
            cursorTwo = cursor;
            positionTwo = position;
            cursorOne = cursorSec;
            positionOne = positionSec;
        }
        count--;
        updateCursorsPosition(position, 2);
        return true;
    }

    /**
     * Método remove a primeira ocorrência do elemento na lista
     * @param elemento a ser removido
     * @return true, caso o elemento seja removido
     */
    public boolean remove(Integer element) {
        if (isEmpty()) return false;
        else if (count == 1){ // Testa se a lista tinha somente um elemento
            clear();
            return true;
        }
        
        // Remoção do primeiro da lista
        if (head.element.equals(element)) {
            head = head.next;
            count--;
            return true;
        }
        
        // Remove o Node associado ao elemento
        Node ant = bestCursor(element);
        Node aux = ant.next;

        // Vasculha a lista na tentativa de encontrar o elemento
        while (aux != null) {
            if (aux.element.equals(element)) {
                if (aux == tail) {
                    tail = ant;
                    tail.next = null;
                }
                else {
                    ant.next = aux.next;
                }
                count--;
                updateCursorsPosition(element);
                return true;
            }
            aux = aux.next;
            ant = ant.next;
            moving++;
            moving++;
        }

        // Se o elemento não foi encontrado, retorna falso
        return false;
    }

    /**
     * Método faz a busca do ponteiro mais próximo da posição informada
     * @param finalPosition - posição-objetivo
     * @return Node - cursor para iniciar a busca
     * @throws IndexOutOfBoundsException se finalPosition < 0 OR finalPosition >= quantidade de elementos da lista
     */
    private Node bestCursor(int finalPosition){
        // Tratamento de exceção
        if (finalPosition < 0 || finalPosition >= getCount()) throw new IndexOutOfBoundsException("Você inseriu uma posição ilegal.");

        // Declarações
        int bestPosition = bestCursorPosition(finalPosition); // Auxiliar
        Node bestCursor = head;
        String print = "    Opção de ponteiro escolhida: HEAD";

        // Escolha do melhor cursor
        if (getPositionOne() == bestPosition){
            bestCursor = getCursorOne();
            print = "   Opção de ponteiro escolhida: CURSOR UM";
        }
        if (getPositionTwo() == bestPosition){
            bestCursor = getCursorTwo();
            print = "   Opção de ponteiro escolhida: CURSOR DOIS";
        }

        // Exibição e retorno
        System.out.println("\nProcurando o melhor cursor para posição... " + finalPosition);
        System.out.println(print);
        return bestCursor;
    }

    /**
     * Método faz a busca do ponteiro mais próximo do elemento informado
     * @param element
     * @return Node - cursor para iniciar a busca
     * @throws IndexOutOfBoundsException se finalPosition < 0 OR finalPosition >= quantidade de elementos da lista
     */
    private Node bestCursor(Integer element){
        // Declarações
        Node bestCursor = head;
        String print = "    Opção de ponteiro escolhida: HEAD";

        // Escolha do melhor cursor
        if (cursorOne.element < element && cursorOne.element >= bestCursor.element){
            bestCursor = getCursorOne();
            print = "   Opção de ponteiro escolhida: CURSOR UM";
        }
        if (cursorTwo.element < element && cursorTwo.element >= bestCursor.element){
            bestCursor = getCursorTwo();
            print = "   Opção de ponteiro escolhida: CURSOR DOIS";
        }

        // Exibição e retorno
        System.out.println("\nProcurando o melhor cursor para o elemento... " + element);
        System.out.println(print);
        return bestCursor;
    }
    
    /**
     * Método faz a busca da posição do ponteiro mais próximo do posição informada
     * @param finalPosition - posição-objetivo
     * @return Node - cursor para iniciar a busca
     * @throws IndexOutOfBoundsException se finalPosition < 0 OR finalPosition >= quantidade de elementos da lista
     */
    private int bestCursorPosition(int finalPosition){
        // Tratamento de exceção
        if ((finalPosition < 0) || (finalPosition >= getCount())) throw new IndexOutOfBoundsException("Você inseriu uma posição ilegal.");

        int bestPosition = 0; // Auxiliar

        // Escolha da melhor posição
        if ((finalPosition - getPositionOne() - 1 < finalPosition - bestPosition) && (finalPosition - getPositionOne() >= 0)){
            bestPosition = getPositionOne();
        }
        if ((finalPosition - getPositionTwo() - 1 < finalPosition - bestPosition) && (finalPosition - getPositionTwo() >= 0)){
            bestPosition = getPositionTwo();
        }
        
        // Retorno
        return bestPosition;
    }

    /**
     * Método atualiza os cursores em caso de adição ou remoção da lista
     * @param mode - 1 para ADIÇÃO ou 2 para REMOÇÃO
     * @param index - qual posição está acontecendo a operação
     */
    private void updateCursorsPosition(int index, int mode){
        // Atualiza os cursores
        if (mode == 1){ // Caso de adição de elementos
            if (positionOne >= index || getCount() == 1) positionOne++;
            if (positionTwo >= index || getCount() == 1) positionTwo++;
        } else if (mode == 2){ // Caso de remoção de elementos
            if (positionOne >= index && positionOne > 0) positionOne--;
            if (positionTwo >= index && positionTwo > 0) positionTwo--;
        } 
    }    

    /**
     * Método atualiza os cursores em caso de adição ou remoção da lista
     * @param element - qual elemento acontecendo a operação
     */
    private void updateCursorsPosition(Integer element){
        // Atualiza os cursores
        if (cursorOne.element >= element && positionOne > 0) positionOne--;
        if (cursorTwo.element >= element && positionOne > 0) positionTwo--;
    }   
}
