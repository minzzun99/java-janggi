package domain.state;

import domain.Country;

public interface State {
    State changeTurn();
    Country getCountry();
}
