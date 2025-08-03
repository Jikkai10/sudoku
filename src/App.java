import java.util.Arrays;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.Scanner;
import pieces.Board;
import static util.Template.BOARD_TEMPLATE;

public class App {
    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;
    public static void main(String[] args) throws Exception {
        
        System.out.println(Arrays.toString(args));
        var option = 0;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(args);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void startGame(String[] args) {
        board = new Board(args);
        System.out.println("Jogo iniciado com sucesso!");
    }

    private static void inputNumber() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        System.out.println("Digite a linha (0-8): ");
        int row = getValidNumber(0, 8);

        System.out.println("Digite a coluna (0-8): ");
        int col = getValidNumber(0, 8);

        System.out.println("Digite o valor (1-9): ");
        int value = getValidNumber(1, 9);

        if (board.setBlock(row, col, value)) {
            System.out.println("Número adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar número.");
        }
    }

    private static void removeNumber() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        System.out.println("Digite a linha (0-8): ");
        int row = getValidNumber(0, 8);

        System.out.println("Digite a coluna (0-8): ");
        int col = getValidNumber(0, 8);

        if (board.clearBlock(row, col)) {
            System.out.println("Número removido com sucesso!");
        } else {
            System.out.println("Erro ao remover número.");
        }
    }

    private static void showCurrentGame() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        List<List<pieces.Block>> blocks = board.getBlocks();

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < Board.getSize(); i++) {
            for (var col: blocks.get(i)) {
                args[argPos ++] = " " + ((isNull(col.getValue())) ? " " : col.getValue());
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        var state = board.getState();
        System.out.println("O estado atual do jogo é: " + state.getLabel());
         if(board.hasError()){
            System.out.println("O jogo contém erros");
        } else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.println("Informe 'sim' ou 'não'");
            confirm = scanner.next();
        }

        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
            System.out.println("Jogo limpo com sucesso!");
        }
        
    }

    private static void finishGame() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado. Por favor, inicie um novo jogo.");
            return;
        }
        if (board.isFinished()){
            System.out.println("Parabéns você concluiu o jogo");
            showCurrentGame();
            board = null;
        } else if (board.hasError()) {
            System.out.println("Seu jogo contém erros, verifique seu board e ajuste-o");
        } else {
            System.out.println("Você ainda precisa preencher algum espaço");
        }
    }
    
    private static int getValidNumber(int min, int max) {
        int number;
        while (true) {
            System.out.printf("Digite um número entre %d e %d: ", min, max);
            number = scanner.nextInt();
            if (number >= min && number <= max) {
                break;
            }
            System.out.println("Número inválido. Tente novamente.");
        }
        return number;
    }

    
}
