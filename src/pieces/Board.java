/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pieces;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 *
 * @author mogli
 */
public class Board {
    private List<List<Block>> blocks;
    private State state = State.NON_STARTED;
    private static final int SIZE = 9;

    public Board(String [] args) {
        initializeBoard(args);
    }

    public List<List<Block>> getBlocks() {
        return blocks;
    }

    public static int getSize() {
        return SIZE;
    }

    public boolean setBlock(int row, int col, int value) {
        
        Block block = blocks.get(row).get(col);
        if (!block.isEmpty()) {
            return false; 
        }
        block.setValue(value);

        if(isComplete()) {
            state = State.COMPLETE;
        }else {
            state = State.INCOMPLETE;
        }
        return true;
    }

    public boolean clearBlock(int row, int col) {
        Block block = blocks.get(row).get(col);
        if (block.isFixed()) {
            return false; 
        }
        block.setValue(0);
        return true;
    }

    public State getState() {
        return state;
    }

    public void reset() {
        for (List<Block> row : blocks) {
            for (Block block : row) {
                if (!block.isFixed()) {
                    block.setValue(0);
                }
            }
        }
        state = State.NON_STARTED;
    }

    public boolean isFinished() {
        return state == State.COMPLETE && !hasError();
    }

    public boolean hasError() {
        return !verifyLines() || !verifyColumns() || !verifySubgrids();
    }
    
    private boolean verifyLines() {
        for (List<Block> row : blocks) {
            if (!verifyLine(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyColumns() {
        for (int col = 0; col < SIZE; col++) {
            List<Block> column = new ArrayList<>();
            for (List<Block> row : blocks) {
                column.add(row.get(col));
            }
            if (!verifyLine(column)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyLine(List<Block> line) {
        boolean[] seen = new boolean[SIZE + 1];
        for (Block block : line) {
            if (!block.isEmpty()) {
                if (seen[block.getValue()]) {
                    return false; 
                }
                seen[block.getValue()] = true;
            }
        }
        return true;
    }

    private boolean verifySubgrids() {
        for (int row = 0; row < SIZE; row += 3) {
            for (int col = 0; col < SIZE; col += 3) {
                if (!verifySubgrid(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifySubgrid(int startRow, int startCol) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Block block = blocks.get(startRow + i).get(startCol + j);
                if (!block.isEmpty()) {
                    if (seen[block.getValue()]) {
                        return false; 
                    }
                    seen[block.getValue()] = true;
                }
            }
        }
        return true;
    }

    private boolean isComplete() {
        for (List<Block> row : blocks) {
            for (Block block : row) {
                if (block.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initializeBoard(String[] args) {
        // Initialize the board with blocks
        blocks = new ArrayList<>();
        var position = Stream.of(args)
                .collect(toMap(
                    a -> a.split(";")[0],
                    b -> b.split(";")[1]
                ));
        for (var i = 0; i < SIZE; i++) {
            List<Block> row = new ArrayList<>();
            for (var j = 0; j < SIZE; j++) {
                var valueString = position.get("%s,%s".formatted(i, j));
                //System.out.println("Value for (%s,%s): %s".formatted(i, j, valueString));
                if (valueString == null || valueString.isEmpty()) {
                    row.add(new Block(0, false));
                } else {
                    int value = Integer.parseInt(valueString);
                    boolean isFixed = (value != 0);
                    row.add(new Block(value, isFixed));
                }
            }
            blocks.add(row);
        }
    }
    
}
