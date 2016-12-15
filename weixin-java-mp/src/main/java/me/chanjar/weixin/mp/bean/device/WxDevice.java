package me.chanjar.weixin.mp.bean.device;

/**
 * Created by keungtung on 10/12/2016.
 */
public class WxDevice {
  private String id;
  private String mac;
  private String connect_protocol;
  private String auth_key;
  private String close_strategy;
  private String conn_strategy;
  private String crypt_method;
  private String auth_ver;
  private String manu_mac_pos;
  private String ser_mac_pos;
  private String ble_simple_protocol;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getConnect_protocol() {
    return connect_protocol;
  }

  public void setConnect_protocol(String connect_protocol) {
    this.connect_protocol = connect_protocol;
  }

  public String getAuth_key() {
    return auth_key;
  }

  public void setAuth_key(String auth_key) {
    this.auth_key = auth_key;
  }

  public String getClose_strategy() {
    return close_strategy;
  }

  public void setClose_strategy(String close_strategy) {
    this.close_strategy = close_strategy;
  }

  public String getConn_strategy() {
    return conn_strategy;
  }

  public void setConn_strategy(String conn_strategy) {
    this.conn_strategy = conn_strategy;
  }

  public String getCrypt_method() {
    return crypt_method;
  }

  public void setCrypt_method(String crypt_method) {
    this.crypt_method = crypt_method;
  }

  public String getAuth_ver() {
    return auth_ver;
  }

  public void setAuth_ver(String auth_ver) {
    this.auth_ver = auth_ver;
  }

  public String getManu_mac_pos() {
    return manu_mac_pos;
  }

  public void setManu_mac_pos(String manu_mac_pos) {
    this.manu_mac_pos = manu_mac_pos;
  }

  public String getSer_mac_pos() {
    return ser_mac_pos;
  }

  public void setSer_mac_pos(String ser_mac_pos) {
    this.ser_mac_pos = ser_mac_pos;
  }

  public String getBle_simple_protocol() {
    return ble_simple_protocol;
  }

  public void setBle_simple_protocol(String ble_simple_protocol) {
    this.ble_simple_protocol = ble_simple_protocol;
  }
}
