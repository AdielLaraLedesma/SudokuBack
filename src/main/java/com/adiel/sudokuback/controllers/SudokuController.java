package com.adiel.sudokuback.controllers;

import com.adiel.sudokuback.dto.ResponseSudoku;
import com.adiel.sudokuback.services.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SudokuController {


    @Autowired
    private SudokuService sudokuService;


    @PostMapping("/generate")
    public ResponseSudoku generate(@RequestParam(defaultValue = "easy") String difficult){
        return sudokuService.generate(difficult);
    }

}
