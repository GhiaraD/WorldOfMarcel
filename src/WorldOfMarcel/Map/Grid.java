package WorldOfMarcel.Map;

import WorldOfMarcel.Characters.Character;

import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public int length;
    public int width;
    public Character currentCharacter;
    public Cell currentCell;

    private Grid(int length, int width, Character currentCharacter) {
        this.length = length;
        this.width = width;
        this.currentCharacter = currentCharacter;
        this.currentCell = null;
    }

    public static Grid generateGrid(int length, int width, Character currentCharacter) {
        // Grid map = new Grid(length, width, currentCharacter);
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
        return null;
    }

    // shops: 0,3; 1,3; 2,0.
    // enemies: 3,4
    // finish: 4,4
    public static Grid generateTestGrid(Character currentCharacter) {
        int length = 5;
        int width = 5;
        Grid map = new Grid(length, width, currentCharacter);

        for (int i = 0; i < length; ++i) {
            ArrayList<Cell> gridRow = new ArrayList<>();
            for (int j = 0; j < width; ++j) {
                Cell gridCell;
                if ((i == 0 && j == 3) || (i == 1 && j == 3) || (i == 2 && j == 0)) {
                    Shop shop = new Shop(true);
                    gridCell = new Cell(i, j, Cell.CellEnum.SHOP, shop, false);
                } else if (i == 3 && j == 4) {
                    Enemy enemy = new Enemy();
                    gridCell = new Cell(i, j, Cell.CellEnum.ENEMY, enemy, false);
                } else if (i == 4 && j == 4) {
                    gridCell = new Cell(i, j, Cell.CellEnum.FINISH, null, false);
                } else {
                    gridCell = new Cell(i, j, Cell.CellEnum.EMPTY, null, false);
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

    public void printGrid() {
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
                    if (get(i).get(j).type == Cell.CellEnum.FINISH) {
                        print.append("F  ");
                        char2 = true;
                    } else if (get(i).get(j).type != Cell.CellEnum.EMPTY) {
                        print.append(get(i).get(j).cellElement.toCharacter()).append("  ");
                        char2 = true;
                    } else if (get(i).get(j).type == Cell.CellEnum.EMPTY && !char1) {
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

    private void getCurrentCell() {
        currentCell = get(currentCharacter.oy).get(currentCharacter.ox);
    }

    public void goNorth() {
        if (currentCharacter.oy == 0) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.oy--;
        getCurrentCell();
    }

    public void goSouth() {
        if (currentCharacter.oy == length - 1) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.oy++;
        getCurrentCell();
    }

    public void goWest() {
        if (currentCharacter.ox == 0) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.ox--;
        getCurrentCell();
    }

    public void goEast() {
        if (currentCharacter.ox == width - 1) {
            System.out.println("You cannot do that move.");
            return;
        }
        currentCharacter.ox++;
        getCurrentCell();
    }
}
