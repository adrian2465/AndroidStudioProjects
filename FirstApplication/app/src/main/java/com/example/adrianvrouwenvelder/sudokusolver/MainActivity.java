package com.example.adrianvrouwenvelder.sudokusolver;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String ABOUT_MESSAGE_KEY ="com.apv.sudokusolver.ABOUT_MESSAGE_KEY";
    private final static int[][] cellMap = {
            {R.id.Cell11,R.id.Cell12,R.id.Cell13,R.id.Cell14,R.id.Cell15,R.id.Cell16,R.id.Cell17,R.id.Cell18,R.id.Cell19},
            {R.id.Cell21,R.id.Cell22,R.id.Cell23,R.id.Cell24,R.id.Cell25,R.id.Cell26,R.id.Cell27,R.id.Cell28,R.id.Cell29},
            {R.id.Cell31,R.id.Cell32,R.id.Cell33,R.id.Cell34,R.id.Cell35,R.id.Cell36,R.id.Cell37,R.id.Cell38,R.id.Cell39},
            {R.id.Cell41,R.id.Cell42,R.id.Cell43,R.id.Cell44,R.id.Cell45,R.id.Cell46,R.id.Cell47,R.id.Cell48,R.id.Cell49},
            {R.id.Cell51,R.id.Cell52,R.id.Cell53,R.id.Cell54,R.id.Cell55,R.id.Cell56,R.id.Cell57,R.id.Cell58,R.id.Cell59},
            {R.id.Cell61,R.id.Cell62,R.id.Cell63,R.id.Cell64,R.id.Cell65,R.id.Cell66,R.id.Cell67,R.id.Cell68,R.id.Cell69},
            {R.id.Cell71,R.id.Cell72,R.id.Cell73,R.id.Cell74,R.id.Cell75,R.id.Cell76,R.id.Cell77,R.id.Cell78,R.id.Cell79},
            {R.id.Cell81,R.id.Cell82,R.id.Cell83,R.id.Cell84,R.id.Cell85,R.id.Cell86,R.id.Cell87,R.id.Cell88,R.id.Cell89},
            {R.id.Cell91,R.id.Cell92,R.id.Cell93,R.id.Cell94,R.id.Cell95,R.id.Cell96,R.id.Cell97,R.id.Cell98,R.id.Cell99}
    };

    private SudokuSolver ss=new SudokuSolver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Solve button */
    public void solveButton(View view) {
        if (!ss.init(pullBoard())) {
            displayLackOfSolution();
            return;
        }
        if(ss.solve(0,0)) {
            ss.printGrid("Solution board");
            putBoard(ss);
        } else {
            displayLackOfSolution();
        }
    }
    private void putBoard(SudokuSolver ss) {
        for (int row=0;row<9;row++) {
            for (int col=0;col<9;col++) {
                EditText cell = (EditText) findViewById(cellMap[row][col]);
                int solution=ss.valueAt(row,col);
                if (solution<0) {
                    cell.setTextColor(Color.BLACK);
                    solution=-solution;
                } else {
                    cell.setTextColor(Color.MAGENTA);
                }
                cell.setText(solution==0?"":(""+solution));
            }
        }
    }
    private void displayLackOfSolution() {
        for (int row=0;row<9;row++) {
            for (int col=0;col<9;col++) {
                EditText cell = (EditText) findViewById(cellMap[row][col]);
                cell.setTextColor(Color.RED);
            }
        }
    }
    private int[][] pullBoard() {
        int[][] board=new int[][] {
                // EMPTY
                {0,0,0,0,0,0,0,0,0}, // Row 1
                {0,0,0,0,0,0,0,0,0}, // row 2
                {0,0,0,0,0,0,0,0,0}, // row 3
                {0,0,0,0,0,0,0,0,0}, // row 4
                {0,0,0,0,0,0,0,0,0}, // row 5
                {0,0,0,0,0,0,0,0,0}, // row 6
                {0,0,0,0,0,0,0,0,0}, // row 7
                {0,0,0,0,0,0,0,0,0}, // row 8
                {0,0,0,0,0,0,0,0,0}, // row 9
        };
        for (int row=0;row<9;row++) {
            for (int col=0;col<9;col++) {
                String cellVal = ((EditText) findViewById(cellMap[row][col])).getText().toString();
                board[row][col]=(cellVal.trim().length()>0)?Integer.parseInt(cellVal):0;;
            }
        }
        return board;
    }
    public void aboutButton(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra(ABOUT_MESSAGE_KEY, "");
        startActivity(intent);
    }
    public void clearButton(View view) {
        for (int row=0;row<9;row++) {
            for (int col=0;col<9;col++) {
                EditText cell = (EditText) findViewById(cellMap[row][col]);
                cell.setTextColor(Color.BLACK);
                cell.setText("");
            }
        }
    }
}


