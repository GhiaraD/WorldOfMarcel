package WorldOfMarcel;

import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length;
    int width;
    Character currentCharacter;
    Cell currentCell;

    private Grid(int length, int width, Character currentCharacter) {
        this.length = length;
        this.width = width;
        this.currentCharacter = currentCharacter;
        this.currentCell = null;
    }

    static Grid generateGrid(int length, int width, Character currentCharacter) {
        Grid map = new Grid(length, width, currentCharacter);
//
//        for (int i = 0; i < length; ++i) {
//            ArrayList<Cell> gridRow = new ArrayList<>();
//            //
//            for (int j = 0; j < width; ++j) {
//                Cell gridCell = new Cell();
//                gridRow.add(gridCell);
//                // 2 shops and 4 enemies minimum
//            }
//            map.add(gridRow);
//        }
        return map;
    }

    // shops: 0,3; 1,3; 2,0.
    // enemies: 3,4
    // finish: 4,4
    static Grid generateTestGrid(Character currentCharacter) {
        int length = 5;
        int width = 5;
        Grid map = new Grid(length, width, currentCharacter);

        for (int i = 0; i < length; ++i) {
            ArrayList<Cell> gridRow = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Cell gridCell;
                if ((i == 0 && j == 3) || (i == 1 && j == 3) || (i == 2 && j == 0)) {
                    Shop shop = new Shop(true);
                    gridCell = new Cell(i, j, CellEnum.SHOP, shop, false);
                } else if (i == 3 && j == 4) {
                    Enemy enemy = new Enemy();
                    gridCell = new Cell(i, j, CellEnum.ENEMY, enemy, false);
                } else if (i == 4 && j == 4) {
                    gridCell = new Cell(i, j, CellEnum.FINISH, null, false);
                } else {
                    gridCell = new Cell(i, j, CellEnum.EMPTY, null, false);
                }
                gridRow.add(gridCell);
                // 2 shops and 4 enemies minimum
            }
            map.add(gridRow);
        }
        map.currentCell = map.get(0).get(0);
        map.currentCell.visited = true;
        currentCharacter.ox = 0;
        currentCharacter.oy = 0;
        return map;
    }

    void printGrid() {
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                boolean char1 = false;
                boolean char2 = false;
                StringBuilder print = new StringBuilder();
                if (!get(i).get(j).visited) {
                    print.append("?  ");
                } else {
                    if (i == currentCell.ox && j == currentCell.oy) {
                        print.append('P');
                        char1 = true;
                    }
                    if (get(i).get(j).type == CellEnum.FINISH) {
                        print.append("F  ");
                        char2 = true;
                    } else if (get(i).get(j).type != CellEnum.EMPTY) {
                        print.append(get(i).get(j).cellElement.toCharacter()).append("  ");
                        char2 = true;
                    } else if (get(i).get(j).type == CellEnum.EMPTY && !char1) {
                        print.append("N  ");
                    } else {
                        print.append("  ");
                    }
                    if (char1 && char2)
                        print.deleteCharAt(print.length() - 1);
                }
                System.out.print(print);
            }
            System.out.println();
        }
    }

    void getCurrentCell() {
        currentCell = get(currentCharacter.oy).get(currentCharacter.ox);
    }

    void goNorth() {
        if (currentCharacter.oy == 0) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.oy--;
        getCurrentCell();
    }

    void goSouth() {
        if (currentCharacter.oy == length - 1) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.oy++;
        getCurrentCell();
    }

    void goWest() {
        if (currentCharacter.ox == 0) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.ox--;
        getCurrentCell();
    }

    void goEast() {
        if (currentCharacter.ox == width - 1) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.ox++;
        getCurrentCell();
    }
}
