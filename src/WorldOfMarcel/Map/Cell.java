package WorldOfMarcel.Map;

public class Cell {
    public int ox;
    public int oy;
    public CellEnum type;
    public CellElement cellElement;
    public boolean visited;

    public Cell(int ox, int oy, CellEnum type, CellElement cellElement, boolean visited) {
        this.ox = ox;
        this.oy = oy;
        this.type = type;
        this.cellElement = cellElement;
        this.visited = visited;
    }

    public enum CellEnum {
        EMPTY,
        ENEMY,
        SHOP,
        FINISH
    }
}
