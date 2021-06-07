package Model;

public class SMSReceiver {

    private String _address;

    public SMSReceiver(String _address, String _msg, String _time) {
        this._address = _address;
        this._msg = _msg;
        this._time = _time;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_msg() {
        return _msg;
    }

    public void set_msg(String _msg) {
        this._msg = _msg;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    private String _msg;
    private String _time;


}
