package game_components;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;


    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i=0; i<totalBombs; i++) {
            placeBomb();
        }
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    private void placeBomb() {
        while (true) {
            Coordinate coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.getBox(coord))
                continue;
            bombMap.setBox(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coordinate coord) {
        for (Coordinate around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.getBox(around))
                bombMap.setBox(around, bombMap.getBox(around).getNextNumber());
    }

    int getTotalBombs() {
        return totalBombs;
    }

    Box getBox(Coordinate coord) {
        return bombMap.getBox(coord);
    }

}