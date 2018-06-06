package weakencode.com.weakencode.helper;

import java.io.Serializable;

/**
 * Created by samsung on 6/5/2018.
 */

public class LoginDetails implements Serializable {
    String email;
     String password;
      String  device_id;
       String  device_type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}


