package game_components;

class Matrix {

    private Box[][] matrix;

    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coordinate coord : Ranges.getAllCoordinates())
            matrix[coord.x][coord.y] = defaultBox;
    }

    Box getBox(Coordinate coord) {
        if (Ranges.inRange(coord))
            return matrix[coord.x][coord.y];
        return null;
    }

    void setBox(Coordinate coord, Box box) {
        if (Ranges.inRange(coord))
            matrix[coord.x][coord.y] = box;
    }

}