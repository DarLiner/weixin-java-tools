package me.chanjar.weixin.common.util.http;

import me.chanjar.weixin.common.util.http.apache.ApacheMediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.jodd.JoddMediaDownloadRequestExecutor;
import me.chanjar.weixin.common.util.http.okhttp.OkMediaDownloadRequestExecutor;

import java.io.File;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 *
 * @author Daniel Qian
 */
public abstract class MediaDownloadRequestExecutor<H, P> implements RequestExecutor<File, String> {

  protected RequestHttp<H, P> requestHttp;
  protected File tmpDirFile;
  public MediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

  public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
    switch (requestHttp.getRequestType()) {
      case apacheHttp:
        return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case joddHttp:
        return new JoddMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case okHttp:
        return new OkMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      default:
        return null;
    }
  }

}
