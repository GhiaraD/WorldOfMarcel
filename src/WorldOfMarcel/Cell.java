package WorldOfMarcel;

public class Cell {
    int ox;
    int oy;
    CellEnum type;
    CellElement cellElement;
    boolean visited;

    public Cell(int ox, int oy, CellEnum type, CellElement cellElement, boolean visited){
        this.ox = ox;
        this.oy = oy;
        this.type = type;
        this.cellElement = cellElement;
        this.visited = visited;
    }
}

enum CellEnum {
    EMPTY,
    ENEMY,
    SHOP,
    FINISH
}
