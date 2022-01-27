package com.adiel.sudokuback.services;

import com.adiel.sudokuback.dto.ResponseSudoku;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
public class SudokuService {

    private static Integer AMOUNTOFNUMBERSEASYMODE = 30;
    private static Integer AMOUNTOFNUMBERSNORMALMODE = 20;
    private static Integer AMOUNTOFNUMBERSHARDMODE = 10;

    private ResponseSudoku responseSudoku = new ResponseSudoku();

    private Random random = new Random();


    public ResponseSudoku generate(String difficult) {

        String sudoku = "..." + "..." + "..." +
                        "..." + "..." + "..." +
                        "..." + "..." + "...";

        Integer iterations = difficult.equals("easy") ? AMOUNTOFNUMBERSEASYMODE : difficult.equals("normal") ? AMOUNTOFNUMBERSNORMALMODE : AMOUNTOFNUMBERSHARDMODE;

        for (int i=1; i<iterations; i++){
            int number = random.nextInt(9) + 1;

            int corX = random.nextInt(9);
            int corY = random.nextInt(9);

            do{

            }while(posicionOcupada(sudoku, corX, corY));

        }

        return null;
    }

    private boolean posicionOcupada(String sudoku, int corX, int corY) {
        return true;
    }

    //Darle solución al sudoku
    public ResponseSudoku giveSolution(String sudoku){


        for (int i=0; i<81; i=i+9){

        }

        return responseSudoku;
    }


}
