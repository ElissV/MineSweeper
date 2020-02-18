package game_components;

class Flag {

    private Matrix  flagMap;
    private int closedBoxesCount;


    void start() {
        flagMap = new Matrix(Box.CLOSED);
        closedBoxesCount = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box getBox(Coordinate coord) {
        return flagMap.getBox(coord);
    }

    void setBoxOpen(Coordinate coord) {
        flagMap.setBox(coord, Box.OPEN);
        closedBoxesCount--;
    }

    void toggleFlaggedBox(Coordinate coord) {
        switch (flagMap.getBox(coord)) {
            case FLAGGED:
                setBoxClosed(coord);
                break;
            case CLOSED:
                setBoxFlagged(coord);
        }
    }

    private void setBoxClosed(Coordinate coord) {
        flagMap.setBox(coord, Box.CLOSED);
    }

    private void setBoxFlagged(Coordinate coord) {
        flagMap.setBox(coord, Box.FLAGGED);
    }

    int getCountOfClosedBoxes() {
        return closedBoxesCount;
    }

    void setBombedOnCoord(Coordinate coord) {
        flagMap.setBox(coord, Box.BOMBED);
    }

    void setBombBoxOpen(Coordinate coord) {
        if (flagMap.getBox(coord) == Box.CLOSED)
            flagMap.setBox(coord, Box.OPEN);
    }

    void setNoBombBox(Coordinate coord) {
        if (flagMap.getBox(coord) == Box.FLAGGED)
            flagMap.setBox(coord, Box.NOBOMB);
    }

    int getCountOfFlaggedBoxesAround(Coordinate coord) {
        int count = 0;
        for (Coordinate around : Ranges.getCoordsAround(coord))
            if (flagMap.getBox(around) == Box.FLAGGED)
                count++;
        return count;
    }
}
