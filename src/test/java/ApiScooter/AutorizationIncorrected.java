package ApiScooter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorizationIncorrected {
    private String password;

    public AutorizationIncorrected(String password) {
        this.password = password;
    }
}
