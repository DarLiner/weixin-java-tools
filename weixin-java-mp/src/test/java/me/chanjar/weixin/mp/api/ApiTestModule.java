package me.chanjar.weixin.mp.api;

import java.io.IOException;
import java.io.InputStream;

import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import me.chanjar.weixin.common.util.xml.XStreamInitializer;

public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try (InputStream is1 = ClassLoader
        .getSystemResourceAsStream("test-config.xml")) {
      WxXmlMpInMemoryConfigStorage config = fromXml(
          WxXmlMpInMemoryConfigStorage.class, is1);
      WxMpServiceImpl wxService = new WxMpServiceImpl();
      wxService.setWxMpConfigStorage(config);

      binder.bind(WxMpServiceImpl.class).toInstance(wxService);
      binder.bind(WxMpConfigStorage.class).toInstance(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromXml(Class<T> clazz, InputStream is) {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.alias("xml", clazz);
    xstream.processAnnotations(clazz);
    return (T) xstream.fromXML(is);
  }

  @XStreamAlias("xml")
  public static class WxXmlMpInMemoryConfigStorage
      extends WxMpInMemoryConfigStorage {

    private String openId;
    private String kfAccount;
    private String qrconnectRedirectUrl;

    public String getOpenId() {
      return this.openId;
    }

    public void setOpenId(String openId) {
      this.openId = openId;
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

    public String getKfAccount() {
      return this.kfAccount;
    }

    public void setKfAccount(String kfAccount) {
      this.kfAccount = kfAccount;
    }

    public String getQrconnectRedirectUrl() {
      return this.qrconnectRedirectUrl;
    }

    public void setQrconnectRedirectUrl(String qrconnectRedirectUrl) {
      this.qrconnectRedirectUrl = qrconnectRedirectUrl;
    }

  }

}
