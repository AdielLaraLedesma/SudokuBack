package com.adiel.sudokuback.services;

import com.adiel.sudokuback.dto.ResponseSudoku;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

@Service
public class SudokuService {

    private static final Integer AMOUNTOFNUMBERSEASYMODE = 30;
    private static final Integer AMOUNTOFNUMBERSNORMALMODE = 20;
    private static final Integer AMOUNTOFNUMBERSHARDMODE = 10;

    private final ResponseSudoku responseSudoku = new ResponseSudoku();

    private final Random random = new Random();


    public ResponseSudoku generate(String difficult) {

        String sudoku = "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "..." +
                "..." + "..." + "...";

        int iterations = getIterations(difficult);

        int corX;
        int corY;

        for (int i = 1; i < iterations; i++) {
            int number = random.nextInt(9) + 1;

            String sudokuNew;
            do {
                do {
                    corX = random.nextInt(9) + 1;
                    corY = random.nextInt(9) + 1;
                } while (posicionOcupada(sudoku, corX, corY));
                int posicion = getPosition(corX, corY);
                String inicio = sudoku.substring(0, posicion);
                String fin;
                if (posicion == sudoku.length())
                    fin = "";
                else
                    fin = sudoku.substring(posicion + 1);

                sudokuNew = inicio + number + fin;
            } while (hasError(sudokuNew));
            sudoku = sudokuNew;

        }

        responseSudoku.setCells(sudoku);
        return responseSudoku;
    }

    private Integer getIterations(String difficult) {
        if (difficult.equals("easy"))
            return AMOUNTOFNUMBERSEASYMODE;
        else if (difficult.equals("normal"))
            return AMOUNTOFNUMBERSNORMALMODE;
        return AMOUNTOFNUMBERSHARDMODE;
    }

    /**
     * Metodo que verifica si el sudoku contiene errores
     **/
    private static boolean hasError(String sudoku) {
        String[][] sudokuArray = new String[9][9];
        int contador = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuArray[i][j] = String.valueOf(sudoku.charAt(contador));
                contador++;
            }
        }

        //Validar de forma horizontal y vertical
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    if (sudokuArray[k][i].equals(sudokuArray[k][j]) && !sudokuArray[k][i].equals(".")) {
                        return true;
                    }
                    if (sudokuArray[i][k].equals(sudokuArray[j][k]) && !sudokuArray[i][k].equals(".")) {
                        return true;
                    }
                }
            }
        }

        //Validar de tres en tres
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                String cuadrante = sudokuArray[i][j] + sudokuArray[i][j + 1] + sudokuArray[i][j + 2] +
                        sudokuArray[i + 1][j] + sudokuArray[i + 1][j + 1] + sudokuArray[i + 1][j + 2] +
                        sudokuArray[i + 2][j] + sudokuArray[i + 2][j + 1] + sudokuArray[i + 2][j + 2];
                for (int f = 0; f < 9; f++)
                    for (int h = f + 1; h < 9; h++)
                        if (cuadrante.substring(f, f + 1).equals(cuadrante.substring(h, h + 1)) && cuadrante.charAt(f) != '.')
                            return true;
            }
        }
        return false;
    }

    /**
     * Metodo que revisa si hay un numero en esa posicion
     *
     * @param sudoku String con el sudoku
     * @param corX   int con la coordenada X de la posicion a revisar. Valores posibles del 1 al 9
     * @param corY   int con la coordenada Y de la posicion a revisar. Valores posibles del 1 al 9
     * @return devuelve verdadero si la posicion esta ocupada y falso si no.
     **/
    private boolean posicionOcupada(String sudoku, int corX, int corY) {
        int numero = getPosition(corX, corY);
        return sudoku.charAt(numero - 1) != '.';
    }

    private int getPosition(int corX, int corY) {
        int numero = 0;
        while (true) {
            if (corY > 1) {
                numero += 9;
                corY--;
            } else
                break;
        }
        numero += corX;
        return numero;
    }

    public static String giveSolution(Queue<String> frontier){
        String sudoku = frontier.remove();

        if(!hasError(sudoku) && !sudoku.contains(".")){
            System.out.println(sudoku);
            return sudoku;
        }
        Queue<String> nuevosSudokus = expandSudoku(sudoku);
        for (String nuevoSudoku : nuevosSudokus)
            frontier.add(nuevoSudoku);

        return giveSolution(frontier);
    }

    private static Queue<String> expandSudoku(String sudoku) {
        int index = sudoku.indexOf(".");

        Queue<String> colaSudoku = new LinkedList<>();

        for(int i=1; i<=9; i++){
            String fin;
            fin = index == sudoku.length() ? "" : sudoku.substring(index+1);
            String nuevoSoduku = sudoku.substring(0, index) + i + fin;

            colaSudoku.add(nuevoSoduku);
        }

        return colaSudoku;
    }

    public static void main(String[] args) {
        String stringSudoku = "......7.2.7.3.8...5..7..8.63.9..26.7.......2.1......5.....97.3........65.14......";
        System.out.println(stringSudoku.length());
        Queue<String> sudoku = new LinkedList<>();
        sudoku.add(stringSudoku);
        giveSolution(sudoku);
    }

}
