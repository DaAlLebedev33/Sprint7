package ApiScooter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourierInsufficientData {
    private String password;
    private String firstName;

    public CreateCourierInsufficientData(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }
}