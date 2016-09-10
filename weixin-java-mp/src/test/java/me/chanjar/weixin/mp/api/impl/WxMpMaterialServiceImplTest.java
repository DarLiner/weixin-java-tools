package me.chanjar.weixin.mp.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.fs.FileUtils;
import me.chanjar.weixin.mp.api.ApiTestModule;
import me.chanjar.weixin.mp.bean.WxMpMaterial;
import me.chanjar.weixin.mp.bean.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialCountResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMaterialVideoInfoResult;

/**
 * 素材管理相关接口的测试
 *
 * @author chanjarster
 * @author codepiano
 * @author Binary Wang
 */
@Test(groups = "materialAPI")
@Guice(modules = ApiTestModule.class)
public class WxMpMaterialServiceImplTest {
  @Inject
  protected WxMpServiceImpl wxService;

  private Map<String, Map<String, Object>> media_ids = new LinkedHashMap<>();
  // 缩略图的id，测试上传图文使用
  private String thumbMediaId = "";
  // 单图文消息media_id
  private String singleNewsMediaId = "";
  // 多图文消息media_id
  private String multiNewsMediaId = "";
  // 先查询保存测试开始前永久素材数据
  private WxMpMaterialCountResult wxMaterialCountResultBeforeTest;

  @DataProvider
  public Object[][] uploadMedia() {
    return new Object[][] {
            new Object[] { WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, "mm.jpeg" },
            new Object[] { WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, "mm.mp3" },
            new Object[] { WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, "mm.mp4" },
            new Object[] { WxConsts.MEDIA_THUMB, WxConsts.FILE_JPG, "mm.jpeg" }
    };
  }

  @Test(dataProvider = "uploadMedia")
  public void testUploadMaterial(String mediaType, String fileType, String fileName) throws WxErrorException, IOException {
    if (wxMaterialCountResultBeforeTest == null) {
      wxMaterialCountResultBeforeTest = this.wxService.getMaterialService().materialCount();
    }
    InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
    File tempFile = FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType);
    WxMpMaterial wxMaterial = new WxMpMaterial();
    wxMaterial.setFile(tempFile);
    wxMaterial.setName(fileName);
    if (WxConsts.MEDIA_VIDEO.equals(mediaType)) {
      wxMaterial.setVideoTitle("title");
      wxMaterial.setVideoIntroduction("test video description");
    }
    WxMpMaterialUploadResult res = this.wxService.getMaterialService().materialFileUpload(mediaType, wxMaterial);
    Assert.assertNotNull(res.getMediaId());
    if (WxConsts.MEDIA_IMAGE.equals(mediaType) || WxConsts.MEDIA_THUMB.equals(mediaType)) {
      Assert.assertNotNull(res.getUrl());
    }
    if (WxConsts.MEDIA_THUMB.equals(mediaType)) {
      thumbMediaId = res.getMediaId();
    }

    Map<String, Object> materialInfo = new HashMap<>();
    materialInfo.put("media_id", res.getMediaId());
    materialInfo.put("length", tempFile.length());
    materialInfo.put("filename", tempFile.getName());
    media_ids.put(res.getMediaId(), materialInfo);

    System.out.println(res);
  }

  @Test(dependsOnMethods = {"testUploadMaterial"})
  public void testAddNews() throws WxErrorException {

    // 单图文消息
    WxMpMaterialNews wxMpMaterialNewsSingle = new WxMpMaterialNews();
    WxMpMaterialNews.WxMpMaterialNewsArticle mpMaterialNewsArticleSingle = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    mpMaterialNewsArticleSingle.setAuthor("author");
    mpMaterialNewsArticleSingle.setThumbMediaId(thumbMediaId);
    mpMaterialNewsArticleSingle.setTitle("single title");
    mpMaterialNewsArticleSingle.setContent("single content");
    mpMaterialNewsArticleSingle.setContentSourceUrl("content url");
    mpMaterialNewsArticleSingle.setShowCoverPic(true);
    mpMaterialNewsArticleSingle.setDigest("single news");
    wxMpMaterialNewsSingle.addArticle(mpMaterialNewsArticleSingle);

    // 多图文消息
    WxMpMaterialNews wxMpMaterialNewsMultiple = new WxMpMaterialNews();
    WxMpMaterialNews.WxMpMaterialNewsArticle wxMpMaterialNewsArticleMultiple1 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    wxMpMaterialNewsArticleMultiple1.setAuthor("author1");
    wxMpMaterialNewsArticleMultiple1.setThumbMediaId(thumbMediaId);
    wxMpMaterialNewsArticleMultiple1.setTitle("multi title1");
    wxMpMaterialNewsArticleMultiple1.setContent("content 1");
    wxMpMaterialNewsArticleMultiple1.setContentSourceUrl("content url");
    wxMpMaterialNewsArticleMultiple1.setShowCoverPic(true);
    wxMpMaterialNewsArticleMultiple1.setDigest("");

    WxMpMaterialNews.WxMpMaterialNewsArticle wxMpMaterialNewsArticleMultiple2 = new WxMpMaterialNews.WxMpMaterialNewsArticle();
    wxMpMaterialNewsArticleMultiple2.setAuthor("author2");
    wxMpMaterialNewsArticleMultiple2.setThumbMediaId(thumbMediaId);
    wxMpMaterialNewsArticleMultiple2.setTitle("multi title2");
    wxMpMaterialNewsArticleMultiple2.setContent("content 2");
    wxMpMaterialNewsArticleMultiple2.setContentSourceUrl("content url");
    wxMpMaterialNewsArticleMultiple2.setShowCoverPic(true);
    wxMpMaterialNewsArticleMultiple2.setDigest("");

    wxMpMaterialNewsMultiple.addArticle(wxMpMaterialNewsArticleMultiple1);
    wxMpMaterialNewsMultiple.addArticle(wxMpMaterialNewsArticleMultiple2);

    WxMpMaterialUploadResult resSingle = this.wxService.getMaterialService().materialNewsUpload(wxMpMaterialNewsSingle);
    singleNewsMediaId = resSingle.getMediaId();
    WxMpMaterialUploadResult resMulti = this.wxService.getMaterialService().materialNewsUpload(wxMpMaterialNewsMultiple);
    multiNewsMediaId = resMulti.getMediaId();
  }

  @Test(dependsOnMethods = {"testAddNews"})
  public void testMaterialCount() throws WxErrorException {
    WxMpMaterialCountResult wxMaterialCountResult = this.wxService.getMaterialService().materialCount();
    // 测试上传过程中添加了一个音频，一个视频，两个图片，两个图文消息
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getVoiceCount() + 1, wxMaterialCountResult.getVoiceCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getVideoCount() + 1, wxMaterialCountResult.getVideoCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getImageCount() + 2, wxMaterialCountResult.getImageCount());
    Assert.assertEquals(wxMaterialCountResultBeforeTest.getNewsCount() + 2, wxMaterialCountResult.getNewsCount());
  }

  @Test(dependsOnMethods = {"testMaterialCount"}, dataProvider = "downloadMaterial")
  public void testDownloadMaterial(String media_id) throws WxErrorException, IOException {
    Map<String, Object> materialInfo = media_ids.get(media_id);
    Assert.assertNotNull(materialInfo);
    String filename = materialInfo.get("filename").toString();
    if (filename.endsWith(".mp3") || filename.endsWith(".jpeg")) {
      InputStream inputStream = this.wxService.getMaterialService().materialImageOrVoiceDownload(media_id);
      Assert.assertNotNull(inputStream);
      IOUtils.closeQuietly(inputStream);
    }
    if (filename.endsWith("mp4")) {
      WxMpMaterialVideoInfoResult wxMaterialVideoInfoResult = this.wxService.getMaterialService().materialVideoInfo(media_id);
      Assert.assertNotNull(wxMaterialVideoInfoResult);
      Assert.assertNotNull(wxMaterialVideoInfoResult.getDownUrl());
    }
  }

  @Test(dependsOnMethods = {"testAddNews"})
  public void testGetNewsInfo() throws WxErrorException {
    WxMpMaterialNews wxMpMaterialNewsSingle = this.wxService.getMaterialService().materialNewsInfo(singleNewsMediaId);
    WxMpMaterialNews wxMpMaterialNewsMultiple = this.wxService.getMaterialService().materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
  }

  @Test(dependsOnMethods = {"testGetNewsInfo"})
  public void testUpdateNewsInfo() throws WxErrorException {
    WxMpMaterialNews wxMpMaterialNewsSingle = this.wxService.getMaterialService().materialNewsInfo(singleNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    WxMpMaterialArticleUpdate wxMpMaterialArticleUpdateSingle = new WxMpMaterialArticleUpdate();
    WxMpMaterialNews.WxMpMaterialNewsArticle articleSingle = wxMpMaterialNewsSingle.getArticles().get(0);
    articleSingle.setContent("content single update");
    wxMpMaterialArticleUpdateSingle.setMediaId(singleNewsMediaId);
    wxMpMaterialArticleUpdateSingle.setArticles(articleSingle);
    wxMpMaterialArticleUpdateSingle.setIndex(0);
    boolean resultSingle = this.wxService.getMaterialService().materialNewsUpdate(wxMpMaterialArticleUpdateSingle);
    Assert.assertTrue(resultSingle);
    wxMpMaterialNewsSingle = this.wxService.getMaterialService().materialNewsInfo(singleNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsSingle);
    Assert.assertEquals("content single update", wxMpMaterialNewsSingle.getArticles().get(0).getContent());

    WxMpMaterialNews wxMpMaterialNewsMultiple = this.wxService.getMaterialService().materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
    WxMpMaterialArticleUpdate wxMpMaterialArticleUpdateMulti = new WxMpMaterialArticleUpdate();
    WxMpMaterialNews.WxMpMaterialNewsArticle articleMulti = wxMpMaterialNewsMultiple.getArticles().get(1);
    articleMulti.setContent("content 2 update");
    wxMpMaterialArticleUpdateMulti.setMediaId(multiNewsMediaId);
    wxMpMaterialArticleUpdateMulti.setArticles(articleMulti);
    wxMpMaterialArticleUpdateMulti.setIndex(1);
    boolean resultMulti = this.wxService.getMaterialService().materialNewsUpdate(wxMpMaterialArticleUpdateMulti);
    Assert.assertTrue(resultMulti);
    wxMpMaterialNewsMultiple = this.wxService.getMaterialService().materialNewsInfo(multiNewsMediaId);
    Assert.assertNotNull(wxMpMaterialNewsMultiple);
    Assert.assertEquals("content 2 update", wxMpMaterialNewsMultiple.getArticles().get(1).getContent());
  }

  @Test(dependsOnMethods = {"testUpdateNewsInfo"})
  public void testMaterialNewsList() throws WxErrorException {
    WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = this.wxService.getMaterialService().materialNewsBatchGet(0, 20);
    Assert.assertNotNull(wxMpMaterialNewsBatchGetResult);
    return;
  }

  @Test(dependsOnMethods = {"testMaterialNewsList"})
  public void testMaterialFileList() throws WxErrorException {
    WxMpMaterialFileBatchGetResult wxMpMaterialVoiceBatchGetResult = this.wxService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_VOICE, 0, 20);
    WxMpMaterialFileBatchGetResult wxMpMaterialVideoBatchGetResult = this.wxService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_VIDEO, 0, 20);
    WxMpMaterialFileBatchGetResult wxMpMaterialImageBatchGetResult = this.wxService.getMaterialService().materialFileBatchGet(WxConsts.MATERIAL_IMAGE, 0, 20);

    Assert.assertNotNull(wxMpMaterialVoiceBatchGetResult);
    Assert.assertNotNull(wxMpMaterialVideoBatchGetResult);
    Assert.assertNotNull(wxMpMaterialImageBatchGetResult);
    return;
  }

  @Test(dependsOnMethods = {"testMaterialFileList"}, dataProvider = "allTestMaterial")
  public void testDeleteMaterial(String mediaId) throws WxErrorException {
    boolean result = this.wxService.getMaterialService().materialDelete(mediaId);
    Assert.assertTrue(result);
  }

  @DataProvider
  public Object[][] downloadMaterial() {
    Object[][] params = new Object[this.media_ids.size()][];
    int index = 0;
    for (String mediaId : this.media_ids.keySet()) {
      params[index] = new Object[]{mediaId};
      index++;
    }
    return params;
  }

  @DataProvider
  public Iterator<Object[]> allTestMaterial() {
    List<Object[]> params = new ArrayList<>();
    for (String mediaId : this.media_ids.keySet()) {
      params.add(new Object[]{mediaId});
    }
    params.add(new Object[]{this.singleNewsMediaId});
    params.add(new Object[]{this.multiNewsMediaId});
    return params.iterator();
  }

  // 以下为media接口的测试
  private List<String> mediaIds = new ArrayList<>();
  @Test(dataProvider="uploadMedia")
  public void testUploadMedia(String mediaType, String fileType, String fileName) throws WxErrorException, IOException {
    try(InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName)){
      WxMediaUploadResult res = this.wxService.getMaterialService().mediaUpload(mediaType, fileType, inputStream);
      Assert.assertNotNull(res.getType());
      Assert.assertNotNull(res.getCreatedAt());
      Assert.assertTrue(res.getMediaId() != null || res.getThumbMediaId() != null);

      if (res.getMediaId() != null) {
        this.mediaIds.add(res.getMediaId());
      }

      if (res.getThumbMediaId() != null) {
        this.mediaIds.add(res.getThumbMediaId());
      }

      System.out.println(res);
    }
  }

  @Test(dependsOnMethods = { "testUploadMedia" }, dataProvider="downloadMedia")
  public void testDownloadMedia(String media_id) throws WxErrorException {
    File file = this.wxService.getMaterialService().mediaDownload(media_id);
    Assert.assertNotNull(file);
    System.out.println(file.getAbsolutePath());
  }

  @DataProvider
  public Object[][] downloadMedia() {
    Object[][] params = new Object[this.mediaIds.size()][];
    for (int i = 0; i < this.mediaIds.size(); i++) {
      params[i] = new Object[] { this.mediaIds.get(i) };
    }
    return params;
  }
}
