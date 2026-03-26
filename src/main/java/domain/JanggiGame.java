package domain;

import domain.state.ChoTurn;
import domain.state.Finished;
import domain.state.State;

public class JanggiGame {
    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public Country play() {
        while (!(state instanceof Finished)) {
            // TODO : 각 상태별로 ????? 입력은요?????????
            this.state = state.changeTurn();
        }
        // 이긴 나라 반환
        return state.getCountry();
    }

    public Country getCountry() {
        return state.getCountry();
    }
}
