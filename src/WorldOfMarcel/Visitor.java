package WorldOfMarcel;

public interface Visitor<T extends Entity> {
    void visit(Entity entity);
}
