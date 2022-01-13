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

    void placeEnemy() {
        Enemy enemy = new Enemy();
        type = Cell.CellEnum.ENEMY;
        cellElement = enemy;
    }

    void placeShop() {
        Shop shop = new Shop(true);
        type = Cell.CellEnum.SHOP;
        cellElement = shop;
    }
}
