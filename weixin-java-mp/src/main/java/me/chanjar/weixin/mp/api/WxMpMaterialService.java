package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.*;
import me.chanjar.weixin.mp.bean.result.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * Created by Binary Wang on 2016/7/21.
 * 素材管理的相关接口，包括媒体管理的接口，
 * 即以https://api.weixin.qq.com/cgi-bin/material
 * 和 https://api.weixin.qq.com/cgi-bin/media开头的接口
 * </pre>
 */
public interface WxMpMaterialService {

  /**
   * 新增临时素材
   *
   * @param mediaType
   * @param file
   * @throws WxErrorException
   * @see #mediaUpload(String, String, InputStream)
   */
  WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException;

  /**
   * <pre>
   * 获取临时素材
   * 本接口即为原“下载多媒体文件”接口。
   * 根据微信文档，视频文件下载不了，会返回null
   * 详情请见: <a href="http://mp.weixin.qq.com/wiki/9/677a85e3f3849af35de54bb5516c2521.html">获取临时素材</a>
   * </pre>
   *
   * @param media_id
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   */
  File mediaDownload(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 上传图文消息内的图片获取URL
   * 详情请见：http://mp.weixin.qq.com/wiki/15/40b6865b893947b764e2de8e4a1fb55f.html#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E5.86.85.E7.9A.84.E5.9B.BE.E7.89.87.E8.8E.B7.E5.8F.96URL.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8E.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.9D.87.E5.8F.AF.E7.94.A8.E3.80.91
   * </pre>
   *
   * @param file
   * @return WxMediaImgUploadResult 返回图片url
   * @throws WxErrorException
   */
  WxMediaImgUploadResult mediaImgUpload(File file) throws WxErrorException;

  /**
   * <pre>
   * 新增临时素材
   * 本接口即为原“上传多媒体文件”接口。
   *
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 1M，支持JPG格式
   *   语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
   *   视频（video）：10MB，支持MP4格式
   *   缩略图（thumb）：64KB，支持JPG格式
   *
   * 详情请见: <a href="http://mp.weixin.qq.com/wiki/15/2d353966323806a202cd2deaafe8e557.html">新增临时素材</a>
   * </pre>
   *
   * @param mediaType   媒体类型, 请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param fileType    文件类型，请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param inputStream 输入流
   * @throws WxErrorException
   */
  WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException;

  /**
   * <pre>
   * 上传非图文永久素材
   *
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式
   *   语音（voice）：语音大小不超过5M，长度不超过60秒，支持mp3/wma/wav/amr格式
   *   视频（video）：在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
   *   缩略图（thumb）：文档未说明
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
   * </pre>
   *
   * @param mediaType 媒体类型, 请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param material  上传的素材, 请看{@link me.chanjar.weixin.mp.bean.WxMpMaterial}
   */
  WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) throws WxErrorException;

  /**
   * <pre>
   * 上传永久图文素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
   * </pre>
   *
   * @param news 上传的图文消息, 请看{@link me.chanjar.weixin.mp.bean.WxMpMaterialNews}
   */
  WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) throws WxErrorException;

  /**
   * <pre>
   * 下载声音或者图片永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   *
   * @param media_id 永久素材的id
   */
  InputStream materialImageOrVoiceDownload(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 获取视频永久素材的信息和下载地址
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   *
   * @param media_id 永久素材的id
   */
  WxMpMaterialVideoInfoResult materialVideoInfo(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 获取图文永久素材的信息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   *
   * @param media_id 永久素材的id
   */
  WxMpMaterialNews materialNewsInfo(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 更新图文永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/19a59cba020d506e767360ca1be29450.html
   * </pre>
   *
   * @param wxMpMaterialArticleUpdate 用来更新图文素材的bean, 请看{@link me.chanjar.weixin.mp.bean.WxMpMaterialArticleUpdate}
   */
  boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException;

  /**
   * <pre>
   * 删除永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/5/e66f61c303db51a6c0f90f46b15af5f5.html
   * </pre>
   *
   * @param media_id 永久素材的id
   */
  boolean materialDelete(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 获取各类素材总数
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html
   * </pre>
   */
  WxMpMaterialCountResult materialCount() throws WxErrorException;

  /**
   * <pre>
   * 分页获取图文素材列表
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
   * </pre>
   *
   * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count  返回素材的数量，取值在1到20之间
   */
  WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) throws WxErrorException;

  /**
   * <pre>
   * 分页获取其他媒体素材列表
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
   * </pre>
   *
   * @param type   媒体类型, 请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count  返回素材的数量，取值在1到20之间
   */
  WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count) throws WxErrorException;

}
