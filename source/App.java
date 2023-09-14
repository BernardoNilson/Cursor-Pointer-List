package source;
import java.util.Random;
import java.util.Scanner;

/**
 * Esta é a aplicação principal do Trabalho de ALEST I com o Prof. Marcio Pinho. Leia o README.md
 * 
 * @author Bernardo Nilson
 * @version 14.09.2023
 */

public class App {
    // Scanner da classe inteira
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // Declarações
        Random random = new Random();
        LinkedListOfInteger list = new LinkedListOfInteger();
        String choice;
        int jump;
        Integer select;
        
        // Geração da lista com números aleatórios
        for (int i = 0; i < 10; i++){
            Integer element = Integer.valueOf(random.nextInt(100));
            list.addIncreasingOrder(element);
        }

        // Zera as posições dos cursores
        list.startCursorPosition();

        // Mensagem de início
        System.out.println(welcome());

        // Aplicação e funções principais
        do {
            list.resetMoving();
            // Exibe a lista
            System.out.println("\n    Posição do C1 - " + list.getPositionOne() + "    |   Posição do C2 - " + list.getPositionTwo());
            System.out.println("----------------------------------------------------");
            System.out.println("    " + list);

            // Exibe o menu
            choice = showMenu();
    
            // Executa a opção escolhida
            switch (choice) {
                
            case "A":
                    showMessage("AVANÇAR O CURSOR UM!");
                    jump = askPositions();
                    list.moveCursorForward(1, jump);
                    break;
                
                case "B":
                    showMessage("AVANÇAR O CURSOR DOIS!");
                    jump = askPositions();
                    list.moveCursorForward(2, jump);
                    break;
                
                case "C":
                    showMessage("RETROCEDER O CURSOR UM!");
                    jump = askPositions();
                    list.moveCursorBackward(1, jump);
                    break;
                
                case "D":
                    showMessage("RETROCEDER O CURSOR DOIS!");
                    jump = askPositions();
                    list.moveCursorBackward(2, jump);
                    break;
                
                case "F":
                    showMessage("TROCA ENTRE OS CURSORES!");
                    list.switchNodePositions();
                    break;
                
                case "G":
                    showMessage("REMOVER VIA CURSOR UM!");
                    resultMessage(list.remove(1));
                    break;
                
                case "H":
                    showMessage("REMOVER VIA CURSOR DOIS!");
                    resultMessage(list.remove(2));
                    break;
                
                case "I":
                    showMessage("REMOVER VIA ELEMENTO!");
                    System.out.print("Qual elemento você quer remover? ");
                    select = Integer.valueOf(scan.nextInt());
                    resultMessage(list.remove(select));
                    break;
                
                case "J":
                    showMessage("INSERIR VIA CURSOR UM!");
                    System.out.print("Qual elemento você quer inserir? ");
                    select = Integer.valueOf(scan.nextInt());
                    list.addByCursor(1, select);
                    break;
                
                case "K":
                    showMessage("INSERIR VIA CURSOR DOIS!");
                    System.out.print("Qual elemento você quer inserir? ");
                    select = Integer.valueOf(scan.nextInt());
                    list.addByCursor(2, select);
                    break;
                
                case "L":
                    showMessage("INSERIR VIA ELEMENTO!");
                    System.out.print("Qual elemento você quer inserir? ");
                    select = Integer.valueOf(scan.nextInt());
                    list.addIncreasingOrder(select);
                    break;
                
                case "Z":
                    System.out.println(" - Você está saindo do programa. Até mais! :)");
                    System.out.println("----------------------------------------------------");
                    System.out.println(list);
                    System.out.println("----------------------------------------------------");
                    break;
                    
                default:
                    System.out.println("\nVocê pode ter se confundido. Por favor, tente outra opção!");
                    break;
            }
            System.out.println("Operação executada necessitou " + list.getMoving() + " movimentos na lista (a = a.next)");
        } while (!"Z".equals(choice));
        scan.close();
    }

    /**
     * Método exibe um menu ao usuário e retorna a escolha
     * @return String - escolha do usuário, independente se está certa ou não
     */
    private static String showMenu(){
        System.out.println("----------------------------------------------------");
        System.out.println("Por favor, selecione uma das opções:");
        System.out.println(" - AVANÇAR");
        System.out.println("     ( A ) - CURSOR UM");
        System.out.println("     ( B ) - CURSOR DOIS");
        System.out.println(" - RETROCEDER");
        System.out.println("     ( C ) - CURSOR UM");
        System.out.println("     ( D ) - CURSOR DOIS");
        System.out.println(" - TROCAR");
        System.out.println("     ( F ) - ENTRE OS CURSORES");
        System.out.println(" - REMOVER");
        System.out.println("     ( G ) - VIA CURSOR UM");
        System.out.println("     ( H ) - VIA CURSOR DOIS");
        System.out.println("     ( I ) - VIA ELEMENTO");
        System.out.println(" - INSERIR");
        System.out.println("     ( J ) - VIA CURSOR UM");
        System.out.println("     ( K ) - VIA CURSOR DOIS");
        System.out.println("     ( L ) - VIA ELEMENTO");
        System.out.println(" - SAIR");
        System.out.println("     ( Z ) - SAIR");
        System.out.println("----------------------------------------------------");
        System.out.print("Sua resposta: ");
        return scan.next().toUpperCase();
    }

    /**
     * Método exibe um menu ao usuário
     * @param message - mensagem a ser enviada
     */
    private static void showMessage(String message){
        System.out.println(" Você selecionou " + message + "!");
    }

    /**
     * Método pergunta quantas posições o usuário deseja andar
     * @return int - quantas posições
     */
    private static int askPositions(){
        System.out.print("Quantas posições? ");
        return scan.nextInt();
    }

    /**
     * Método exibe uma mensagem de sucesso ou erro
     * @param mode - true para sucesso ou false para fracasso
     */
    private static void resultMessage(boolean mode){
        if (mode) System.out.println("\nOperação concluída com sucesso!");
        else System.out.println("\nOperação não teve êxito, revise as informações e tente novamente");
    }


    /**
     * Método exibe uma mensagem de boas vindas
     */
    private static String welcome(){
        return "\n\n...................................................\n" +
        "......-#*-....................#@+:.................\n" +
        "......:# -*-..................*@@@+:...............\n" +
        ".......#.  -*=................+@@@@@+:.............\n" +
        ".......*-    :*=..............-@@@@@@%+:...........\n" +
        ".......:#       :++...........:#@@@@@@@@%+.........\n" +
        "........*-        :++=.........+@@@@@@@@@@%*+......\n" +
        "........=+      :*.............-@@@@@@@@#..........\n" +
        "........:#.+=:*=  ==...........:#@@+:*@@@@=........\n" +
        ".........+#-..:*--*=............+#-..:*@@%=........\n" +
        "...................................................";
    }
}
