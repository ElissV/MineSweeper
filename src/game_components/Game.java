package game_components;

public class Game {

    private GameState gameState;
    private Bomb bomb;
    private Flag flag;

    private final int COLUMNS = 11;
    private final int ROWS = 11;
    private final int BOMBS = 10;

    public Game() {
        Ranges.setSize(new Coordinate(COLUMNS, ROWS));
        bomb = new Bomb(BOMBS);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        gameState = GameState.PLAY;
    }

    public Box getBox(Coordinate coord) {
        if (flag.getBox(coord) == Box.OPEN)
            return bomb.getBox(coord);
        else
            return flag.getBox(coord);
    }

    private void checkWinner() {
        if (gameState == GameState.PLAY)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                gameState = GameState.WINNER;
    }

    private void openBox(Coordinate coord) {
        switch (flag.getBox(coord)) {
            case OPEN:
                setBoxesAroundNumberOpen(coord);
                return;
            case FLAGGED:
                return;
            case CLOSED:
                switch(bomb.getBox(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setBoxOpen(coord);
                }
        }
    }

    private void setBoxesAroundNumberOpen(Coordinate coord) {
        if (bomb.getBox(coord) != Box.BOMB)
            if (flag.getCountOfFlaggedBoxesAround(coord) == bomb.getBox(coord).getNumber())
                for (Coordinate around : Ranges.getCoordsAround(coord))
                    if (flag.getBox(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coordinate bombed) {
        gameState = GameState.BOMBED;
        flag.setBombedOnCoord(bombed);
        for (Coordinate coord : Ranges.getAllCoordinates())
            if (bomb.getBox(coord) == Box.BOMB)
                flag.setBombBoxOpen(coord);
            else
                flag.setNoBombBox(coord);
    }

    private void openBoxesAround(Coordinate coord) {
        flag.setBoxOpen(coord);
        for (Coordinate around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coordinate coord) {
        if (gameOver()) return;
        flag.toggleFlaggedBox(coord);
    }

    public void pressLeftButton(Coordinate coord) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if (gameState == GameState.PLAY)
            return false;
        start();
        return true;
    }

    public GameState getState() {
        return gameState;
    }
}